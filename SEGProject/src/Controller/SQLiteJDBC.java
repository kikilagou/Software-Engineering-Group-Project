package Controller;

import Model.Airport;
import Model.Obstacle;
import Model.Runway;

import java.sql.*;

public class SQLiteJDBC {

	private Connection c;
	private Statement statement;
	private String SQLCommand;

	/**
	 * create all the tables if they do not already exist in the db.
	 */

	public SQLiteJDBC(){

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:SEG.db"); // Open database in memory
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Opened database successfully");

		try {
			statement = c.createStatement();
			SQLCommand = "CREATE TABLE IF NOT EXISTS Airport " +
					"(ID          VARCHAR(32)       PRIMARY KEY," +
					" name   	  VARCHAR(64) 	  NOT NULL);";
			statement.executeUpdate(SQLCommand);
			statement.close();

			statement = c.createStatement();
			SQLCommand = "CREATE TABLE IF NOT EXISTS Runway " +
					"(RunwayID      VARCHAR(32) NOT NULL	PRIMARY KEY," +
					" AirportID     VARCHAR(32) REFERENCES Airport, " +
					" heading	    INT   		NOT NULL , " +
					" DirectionChar CHAR    	NOT NULL, " +
					" remarks       VARCHAR(32), " +
					" TORA		    INT    	NOT NULL , " +
					" TODA      	INT  	NOT NULL, " +
					" ASDA     		INT    	NOT NULL , " +
					" LDA      		INT    	NOT NULL, " +
					" dispThresh    INT    	NOT NULL , " +
					" stopwayLen    INT    	NOT NULL, " +
					" clearwayWidth INT    	NOT NULL , " +
					" clearwayLen   INT    	NOT NULL, " +
					" stripEnd      INT   	NOT NULL , " +
					" runwayWidth   INT   	NOT NULL , " +
					" runwayLen     INT    	NOT NULL);";
			statement.executeUpdate(SQLCommand);
			statement.close();

			statement = c.createStatement();
			SQLCommand = "CREATE TABLE IF NOT EXISTS Obstacle " +
					"(ID          VARCHAR(32)    NOT NULL   PRIMARY KEY," +
					" Height      INT            NOT NULL, " +
					" Width    	  INT            NOT NULL, " +
					" Depth       INT            NOT NULL, " +
					" Description VARCHAR(256)   NOT NULL, " +
					" DistanceToThreshold INT    NOT NULL, " +
					" CenterlineDisplacement INT NOT NULL);";
			statement.executeUpdate(SQLCommand);
			statement.close();

			statement = c.createStatement();
			SQLCommand = "CREATE TABLE IF NOT EXISTS PositionedObstacle " +
					"(RunwayID    		VARCHAR(32)   NOT NULL 		REFERENCES Runway," +
					" ObstacleID  		VARCHAR(32)   NOT NULL 		REFERENCES Obstacle," +
					" ObstaclePosition 	VARCHAR (512) NOT NULL "+");";
			statement.executeUpdate(SQLCommand);
			statement.close();

		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}


	/**
	 *
	 *  inserts an obstacle into the database from raw data
	 *
	 * @param id obstacle id
	 * @param height obstacles hieght
	 * @param width obstacle width
	 * @param depth obstacle depth
	 * @param description obstacle description
	 * @param distanceToThreshold obstacle distance to threshold
	 * @param centerlineDisplacement obstacle centerline displacement
     */
	public void insertObstacle(String id, int height, int width, int depth, String description,
							   int distanceToThreshold, int centerlineDisplacement){

		try {
			statement = c.createStatement();
			SQLCommand = "INSERT INTO Obstacle (ID, Height, Width, Depth, Description, DistanceToThreshold, " +
					"CenterlineDisplacement )" +
					"VALUES (\'" + id + "\', \'" + height + "\', \'" + width + "\', \'" + depth + "\', \'" +
					description + "\', \'" + distanceToThreshold + "\', \'" + centerlineDisplacement + "\');";
			statement.executeUpdate(SQLCommand);
			statement.close();

			statement.close();
		} catch (SQLException e) {
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * inserts an obstacle into the database from raw data
	 * @param obstacle Obstacle object to add to the DB
     */
	public void insertObstacle(Obstacle obstacle){

		try {
			statement = c.createStatement();
			SQLCommand = "INSERT INTO Obstacle (ID, Height, Width, Depth, Description, DistanceToThreshold, " +
					"CenterlineDisplacement )" +
					"VALUES (\'" + obstacle.getId() + "\', \'" + obstacle.getHeight() + "\', \'" + obstacle.getWidth() + "\', \'" + obstacle.getDepth() + "\', \'" +
					obstacle.getDescription() + "\', \'" + obstacle.getDistanceFromThresh() + "\', \'" + obstacle.getCenterlineDist() + "\');";
			statement.executeUpdate(SQLCommand);
			statement.close();

			statement.close();
		} catch (SQLException e) {
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * insert an obstacle that is positioned on a runway into the db
	 * @param runwayName the name of the runway
	 * @param obstacleID the name of the obstacle
	 * @param obstaclePosition insert the positioned obstacle
     */
	public void insertPositiondObstacle(String runwayName, String obstacleID, String obstaclePosition) {
		try {
			statement = c.createStatement();
			SQLCommand = "INSERT INTO PositionedObstacle(RunwayID,  ObstacleID, ObstaclePosition)" +
					" VALUES (\'" + runwayName + "\', \'" + obstacleID + "\', \'" +
					obstaclePosition + "\');";
			statement.executeUpdate(SQLCommand);
			statement.close();
		} catch (SQLException e) {
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 *
	 * inserts a runway into the database from raw data
	 *
	 * @param runwayID the Id of the runway
	 * @param airportName the name of the airport
	 * @param heading the heading of the runway
	 * @param directionChar the direction of the runway
	 * @param tora the tora of the runway
	 * @param toda the toda of the runway
	 * @param asda the asda of the runway
	 * @param lda the lda of the runway
	 * @param displacementThreshold the diplacement of the runway
	 * @param stopwayLen the stopway length of the runway
	 * @param clearwaywidth the clearway width of the runway
	 * @param clearwaylength the clearway length of the runway
     * @param stripend the length of the runway strip end
     * @param runwayWidth the width of the runway
     * @param runwayLen the length of the runway
     */
	public void insertRunway(String runwayID, String airportName, int heading, char directionChar, String remarks, float tora, float toda,

							 float asda, float lda, float displacementThreshold, float stopwayLen, float clearwaywidth,
							 float clearwaylength, float stripend, float runwayWidth, float runwayLen){

		try {
			statement = c.createStatement();
			SQLCommand = "INSERT INTO Runway(RunwayID, AirportID, heading, DirectionChar, remarks, TORA,  TODA, ASDA, LDA, " +
					"dispThresh, stopwayLen, clearwayWidth, clearwayLen, stripEnd, runwayWidth, runwayLen)" +
					" VALUES (\'" + runwayID + "\', ( SELECT ID FROM Airport WHERE name=\'" +airportName + "\'), \'" +
					heading + "\', \'" + directionChar + "\', \'" + remarks + "\', \'" + tora + "\', \'" + toda + "\', \'" + asda + "\', \'"
					+ lda + "\', \'" + displacementThreshold + "\', \'" + stopwayLen + "\', \'" + clearwaylength +
					"\', \'" + clearwaywidth + "\', \'" + stripend + "\', \'" + runwayWidth + "\', \'" + runwayLen + "\');";
			statement.executeUpdate(SQLCommand);
			statement.close();
		} catch (SQLException e) {
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * insert runway from a runway obstacle
	 * @param runway runway obstacle
	 * @param airportName name of the airport
     */
	public void insertRunway(Runway runway, String airportName){

		try {
			statement = c.createStatement();
			SQLCommand = "INSERT OR IGNORE INTO Runway(RunwayID, AirportID, heading, DirectionChar, remarks, TORA,  TODA, ASDA, LDA, " +
					"dispThresh, stopwayLen, clearwayWidth, clearwayLen, stripEnd, runwayWidth, runwayLen)" +
					" VALUES (\'" + runway.getId() + "\', ( SELECT ID FROM Airport WHERE name=\'" + airportName + "\'), \'" +
					runway.getHeading() + "\', \'" + runway.getDirectionChar() + "\', \'" + runway.getRemarks() + "\', \'" + runway.getTora() + "\', \'" + runway.getToda() + "\', \'" + runway.getAsda() + "\', \'"
					+ runway.getLda() + "\', \'" + runway.getThresholdDisplacement() + "\', \'" + runway.getStopwayLength() + "\', \'" + runway.getClearwayLength() +
					"\', \'" + runway.getClearwayWidth() + "\', \'" + runway.getStripEnd() + "\', \'" + runway.getRunwayWidth() + "\', \'" + runway.getRunwayLength() + "\');";
			statement.executeUpdate(SQLCommand);
			statement.close();
		} catch (SQLException e) {
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * inserting an airport from the obstacle
	 * @param a an airport obstacle
	 * @param id the airport id
     */
	public void insertAirport(Airport a, String id){

		try {
			statement = c.createStatement();
			SQLCommand = "INSERT INTO airport(ID, name)" +
					" VALUES (\'" +  id + "\', \'" + a.getAirportName() + "\');";
			statement.executeUpdate(SQLCommand);
			statement.close();
		} catch (SQLException e) {
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * insert an airport from raw data
	 * @param ID the id of the airport
	 * @param name the name of the airport
     */
	public void insertAirport(String ID, String name){
		try {
			statement = c.createStatement();
			SQLCommand = "INSERT INTO airport(ID, name)" +
					" VALUES (\'" + ID + "\', \'" + name + "\');";
			statement.executeUpdate(SQLCommand);
			statement.close();
		} catch (SQLException e) {
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void removeRunway(String runwayId)
	{
		try
		{
			statement = c.createStatement();
			SQLCommand = "DELETE FROM Runway WHERE RunwayID = \'" + runwayId + "\';";
			statement.executeUpdate(SQLCommand);
			statement.close();
		} catch (SQLException e)
		{
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * gets and obstacle with a specifice id
	 * @param nameID the obstacle id
	 * @return an obstacle object
     */
	public Obstacle getObstable(String nameID){

		Obstacle obstacle = null;

		try {
			statement = c.createStatement();
			SQLCommand = "SELECT * FROM Obstacle WHERE ID=\'"+ nameID + "\';";
			ResultSet rs = statement.executeQuery(SQLCommand);


//			if (rs.getInt("rowcount") > 1){
//				// todo throw an exception of to many obstacles by this name
//			} else if (rs.getInt("rowcount") == 0){
//				// // TODO: return that there is no obstacle of this type
//			} else {
			if(rs.next())
			{
				obstacle = new Obstacle(rs.getInt("Height"),
						rs.getInt("Width"),
						rs.getInt("Depth"),
						rs.getInt("DistanceToThreshold"),
						rs.getInt("CenterlineDisplacement"),
						rs.getString("ID"),
						rs.getString("Description"));
			}
			if(rs.next()){
			obstacle = new Obstacle(rs.getInt("Height"),
					rs.getInt("Width"),
					rs.getInt("Depth"),
					rs.getInt("DistanceToThreshold"),
					rs.getInt("CenterlineDisplacement"),
					rs.getString("ID"),
					rs.getString("Description"));

			rs.close();
			}

//			}
		}catch (SQLException e) {
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}

		return obstacle;

	}


	/**
	 * gets a runway with a specific id
	 * @param ID the id of the runway
	 * @return a runway obstacle
     */
	public Runway getRunway(String ID){

		Runway runway = null;

		try {
			statement = c.createStatement();
			SQLCommand = "SELECT * FROM Runway WHERE RunwayID=\'"+ ID + "\';";
			ResultSet rs = statement.executeQuery(SQLCommand);

			if (rs.next()){
				runway = new Runway(ID,
						rs.getString("remarks"),
						rs.getInt("heading"),
						rs.getString("DirectionChar").charAt(0),
						rs.getInt("runwayLen"),
						rs.getInt("runwayWidth"),
						rs.getInt("TORA"),
						rs.getInt("TODA"),
						rs.getInt("ASDA"),
						rs.getInt("LDA"),
						rs.getInt("dispThresh"),
						rs.getInt("stopwayLen"),
						rs.getInt("clearwayWidth"),
						rs.getInt("clearwayLen"));


				SQLCommand = "SELECT * FROM PositionedObstacle WHERE RunwayID=\""+ ID + "\";";
				ResultSet rs2 = c.createStatement().executeQuery(SQLCommand);

				while (rs2.next()) {
					runway.setObstacle(new Obstacle(rs2.getInt("Height"),
							rs2.getInt("Width"),
							rs2.getInt("Depth"),
							rs2.getInt("DistanceToThreshold"),
							rs2.getInt("CenterlineDisplacement"),
							Integer.toString(rs2.getInt("ID")),
							rs2.getString("Description")));

				}
				rs2.close();
			}
			rs.close();

		}catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}

		return runway;

	}

	/**
	 * gets an airport from its name
	 * @param name the name of the airport
	 * @return an airport object
     */
	public Airport getAirport(String name){

		Airport airport = null;

		try {
			statement = c.createStatement();
			SQLCommand = "SELECT * FROM Airport WHERE name=\'"+ name + "\';";
			ResultSet rs = statement.executeQuery(SQLCommand);


			if (rs.next()) {
				airport = new Airport(name);
			} else {
				return null;
			}

			String airportID = rs.getString("ID");

			SQLCommand = "SELECT * FROM Runway WHERE AirportID=\""+ airportID + "\";";
			ResultSet rs2 = statement.executeQuery(SQLCommand);

			while ( rs2.next() ) {

				Runway runway = new Runway("id",
						rs.getString("remarks"),
						rs.getInt("heading"),
						rs.getString("DirectionChar").charAt(0),
						rs.getInt("runwayLen"),
						rs.getInt("runwayWidth"),
						rs.getInt("TORA"),
						rs.getInt("TODA"),
						rs.getInt("ASDA"),
						rs.getInt("LDA"),
						rs.getInt("dispThresh"),
						rs.getInt("stopwayLen"),
						rs.getInt("clearwayWidth"),
						rs.getInt("clearwayLen"));



				SQLCommand = "SELECT * FROM PositionedObstacle WHERE RunwayID=\""+ rs2.getString("RunwayID") + "\";";
				ResultSet rs3 = statement.executeQuery(SQLCommand);

				if(rs3.next())
				{
					runway.setObstacle(new Obstacle(rs3.getInt("Height"),
							rs3.getInt("Width"),
							rs3.getInt("Depth"),
							rs3.getInt("DistanceToThreshold"),
							rs3.getInt("CenterlineDisplacement"),
							Integer.toString(rs3.getInt("ID")),
							rs3.getString("Description")));
				}
				
				airport.addRunway(runway);
				rs3.close();
			}

			rs.close();
			rs2.close();


		}catch (SQLException e) {
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}

		return airport;
	}

	/**
	 * deletes an obstacle fro being positioned
	 * @param runwayID the id of the runway on which the obstacle is positioned
	 * @param obstacleID the id of the positioned obstacle
     */
	public void deletePositionObstacle(String runwayID, int obstacleID){
		try {
			statement = c.createStatement();
			String sql = "DELETE from PositionedObstacle where RunwayID=\"" + runwayID + "\" AND ObstacleID=\""
					+ obstacleID + "\";";
			statement.executeUpdate(sql);
			c.commit();
		} catch (SQLException e) {
			System.out.println("unable to connect to the database");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * removes all data from the database
	 */
	public void removeAll(){
		String sql = "Delete From Airport";
		String sql2 = "Delete From Obstacle";
		String sql3 = "Delete From PositionedObstacle";
		String sql4 = "Delete From Runway";


		try {
			c.createStatement().executeUpdate(sql);
			c.createStatement().executeUpdate(sql2);
			
			c.createStatement().executeUpdate(sql3);
			c.createStatement().executeUpdate(sql4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * shuts down the database
	 */
	public void shutDownDBConnetion(){
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("unable to close the connection to the Database.");
			e.printStackTrace();
		}
	}

}