package tests;

import Controller.SQLiteJDBC;
import Model.Airport;
import Model.Obstacle;
import Model.Runway;
import junit.framework.TestCase;

public class IntegrationTest extends TestCase {
	public void testTool() {
		String airportNameID = "SEGport";
		Airport testAirport = new Airport(airportNameID);
		Runway testRunway = new Runway("Test Runway ID", "This is a test runway", 20, 'R', 5000, 150, 5000, 5000, 4500, 4500, 450, 100, 50, 60);
		Obstacle testObstacle = new Obstacle(5,5,5,25,50, "Test Obstacle 1", "Test Obstacle 1");
		testRunway.setObstacle(testObstacle);
		testAirport.addRunway(testRunway);
		assertEquals(testAirport.getRunway("Test Runway ID").getObstacle().getHeight(), testObstacle.getHeight());
		
		SQLiteJDBC db = new SQLiteJDBC();
		db.removeAll();
		db.insertAirport(testAirport, airportNameID);
		db.insertObstacle(testObstacle);
		db.insertRunway(testRunway, airportNameID);
		db.insertPositiondObstacle("Test Runway ID", testObstacle.getId(), String.valueOf(testObstacle.getDistanceFromThresh()));
		
		assertEquals(db.getRunway("Test Runway ID").getRunwayLength(), testRunway.getRunwayLength());
		assertEquals(db.getRunway("Test Runway ID").getObstacle().getHeight(), testObstacle.getHeight());
		//assertEquals(db.getAirport(airportNameID).getRunway("Test Runway ID").getRunwayLength(), testRunway.getRunwayLength());
		assertEquals(db.getAirport(airportNameID).getAirportName(), airportNameID);
		
	}
}
