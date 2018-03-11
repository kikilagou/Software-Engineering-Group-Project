package tests;

import Controller.SQLiteJDBC;
import Model.Airport;
import Model.Obstacle;
import Model.Runway;
import junit.framework.TestCase;

public class SQLiteJDBCTest extends TestCase {

	
	// Method tests that an obstacle can be created, added to the database and accessed
	public void testInsertandAccessObstacle() {
		SQLiteJDBC db = new SQLiteJDBC();
		db.removeAll();
		Obstacle originalTestObstacle1 = new Obstacle(5,5,5,25,50, "Test Obstacle 1", "Test Obstacle 1");
		db.insertObstacle(originalTestObstacle1);
		Obstacle retrievedTestObstacle1 = db.getObstable("Test Obstacle 1");
		assertEquals(originalTestObstacle1.getCenterlineDist(), retrievedTestObstacle1.getCenterlineDist());
		assertEquals(originalTestObstacle1.getDepth(), retrievedTestObstacle1.getDepth());
		assertEquals(originalTestObstacle1.getDescription(), retrievedTestObstacle1.getDescription());
		assertEquals(originalTestObstacle1.getDistanceFromThresh(), retrievedTestObstacle1.getDistanceFromThresh());
		assertEquals(originalTestObstacle1.getHeight(), retrievedTestObstacle1.getHeight());
		assertEquals(originalTestObstacle1.getId(), retrievedTestObstacle1.getId());
		assertEquals(originalTestObstacle1.getWidth(), retrievedTestObstacle1.getWidth());
	}
	
	
	// Method tests that a runway can be created, added to the database and accessed
	public void testInsertandAccessRunway() {
		SQLiteJDBC db = new SQLiteJDBC();
		db.removeAll();
		Runway originalTestRunway1 = new Runway("Test Runway 1", "Test Runway 1", 0, 'L', 3902d, 100d, 3902d, 3902d, 3902d, 3595d, 306d, 0, 0, 0);
		db.insertRunway(originalTestRunway1, "Test Airport 1");
		Runway retrievedTestRunway1 = db.getRunway(originalTestRunway1.getId());
		assertEquals(originalTestRunway1.getAsda(), retrievedTestRunway1.getAsda());
		assertEquals(originalTestRunway1.getClearwayLength(), retrievedTestRunway1.getClearwayLength());
		assertEquals(originalTestRunway1.getClearwayWidth(), retrievedTestRunway1.getClearwayWidth());
		assertEquals(originalTestRunway1.getDirectionChar(), retrievedTestRunway1.getDirectionChar());
		assertEquals(originalTestRunway1.getHeading(), retrievedTestRunway1.getHeading());
		assertEquals(originalTestRunway1.getId(), retrievedTestRunway1.getId());
		assertEquals(originalTestRunway1.getLda(), retrievedTestRunway1.getLda());
		assertEquals(originalTestRunway1.getRemarks(), retrievedTestRunway1.getRemarks());
		assertEquals(originalTestRunway1.getRunwayLength(), retrievedTestRunway1.getRunwayLength());
		assertEquals(originalTestRunway1.getRunwayWidth(), retrievedTestRunway1.getRunwayWidth());
		assertEquals(originalTestRunway1.getStopwayLength(), retrievedTestRunway1.getStopwayLength());
		assertEquals(originalTestRunway1.getStripEnd(), retrievedTestRunway1.getStripEnd());
		assertEquals(originalTestRunway1.getThresholdDisplacement(), retrievedTestRunway1.getThresholdDisplacement());
		assertEquals(originalTestRunway1.getToda(), retrievedTestRunway1.getToda());
		assertEquals(originalTestRunway1.getTora(), retrievedTestRunway1.getTora());
	}

	// Method tests that an airport can be created, added to the database and accessed
	public void testInsertandAccessAirport() {
		SQLiteJDBC db = new SQLiteJDBC();
		db.removeAll();
		Airport original = new Airport("Test Airport 2");
		db.insertAirport(original, "Test Airport 2");
		Airport retrieved = db.getAirport("Test Airport 2");
		assertEquals(original.getAirportName(), retrieved.getAirportName());
	}
	
	// Method tests that different methods can be used to insert obstacles, runways and airports
	// Also checks that a more robust database can be created
	public void testBuildDatabase() {
		SQLiteJDBC db = new SQLiteJDBC();
		db.removeAll();
		Airport originalTestAirport3 = new Airport("Test Airport 3");
		
		Runway originalTestRunway2 = new Runway("Test Runway 2", "Test Runway 2", 9, 'R', 3660d, 1000d, 3660d, 3660d, 3660d, 3353d, 307d, 0, 0, 0);
		
		
		
		Obstacle originalTestObstacle2 = new Obstacle(15d, 15d, 15d, 50d, 20d, "Test Obstacle 2", "Test Obstacle 2");
		originalTestRunway2.setObstacle(originalTestObstacle2);
		originalTestAirport3.addRunway(originalTestRunway2);
		
		
		db.insertAirport(originalTestAirport3, "Test Airport 3");
		db.insertAirport("Test Airport 4", "Test Airport 4");
		
		
		db.insertRunway(originalTestRunway2, originalTestRunway2.getId());
		//This requires floats?
		db.insertRunway("Test Runway 4", "Test Runway 4", 9, 'L', "Test Runway 4", (float) 3902d, (float)3902d, (float)3902d, (float)3595d, (float)306d, (float)0d, (float)0d, (float)0d, (float)0d, (float)1000d, (float)3902d);
		
		
		db.insertObstacle(originalTestObstacle2);
		//This requires ints?
		db.insertObstacle("Test Obstacle 3", 20, 20, 20, "Test Obstacle 3", 50, 40);
		db.insertPositiondObstacle("Test Runway 10", "Test Obstacle 4", "Test Obstacle 4");
		
		
		Airport retrievedTestAirport3 = db.getAirport(originalTestAirport3.getAirportName());
		Airport retrievedTestAirport4 = db.getAirport("Test Airport 4");
		Runway retrievedTestRunway2 = db.getRunway(originalTestRunway2.getId());
		Obstacle retrievedTestObstacle2 = db.getObstable(originalTestObstacle2.getId());
		Obstacle retrievedTestObstacle3 = db.getObstable("Test Obstacle 3");
		assertEquals(originalTestAirport3.getAirportName(), retrievedTestAirport3.getAirportName());
		
		assertEquals("Test Airport 4", retrievedTestAirport4.getAirportName());

		
		assertEquals(originalTestRunway2.getAsda(), retrievedTestRunway2.getAsda());
		assertEquals(originalTestRunway2.getClearwayLength(), retrievedTestRunway2.getClearwayLength());
		assertEquals(originalTestRunway2.getClearwayWidth(), retrievedTestRunway2.getClearwayWidth());
		assertEquals(originalTestRunway2.getDirectionChar(), retrievedTestRunway2.getDirectionChar());
		assertEquals(originalTestRunway2.getHeading(), retrievedTestRunway2.getHeading());
		assertEquals(originalTestRunway2.getId(), retrievedTestRunway2.getId());
		assertEquals(originalTestRunway2.getLda(), retrievedTestRunway2.getLda());
		assertEquals(originalTestRunway2.getRemarks(), retrievedTestRunway2.getRemarks());
		assertEquals(originalTestRunway2.getRunwayLength(), retrievedTestRunway2.getRunwayLength());
		assertEquals(originalTestRunway2.getRunwayWidth(), retrievedTestRunway2.getRunwayWidth());
		assertEquals(originalTestRunway2.getStopwayLength(), retrievedTestRunway2.getStopwayLength());
		assertEquals(originalTestRunway2.getStripEnd(), retrievedTestRunway2.getStripEnd());
		assertEquals(originalTestRunway2.getThresholdDisplacement(), retrievedTestRunway2.getThresholdDisplacement());
		assertEquals(originalTestRunway2.getToda(), retrievedTestRunway2.getToda());
		assertEquals(originalTestRunway2.getTora(), retrievedTestRunway2.getTora());
		//assertEquals("Runway Inserted and Retrieved", originalRunway, retrievedRunway);
		
		assertEquals(originalTestObstacle2.getCenterlineDist(), retrievedTestObstacle2.getCenterlineDist());
		assertEquals(originalTestObstacle2.getDepth(), retrievedTestObstacle2.getDepth());
		assertEquals(originalTestObstacle2.getDescription(), retrievedTestObstacle2.getDescription());
		assertEquals(originalTestObstacle2.getDistanceFromThresh(), retrievedTestObstacle2.getDistanceFromThresh());
		assertEquals(originalTestObstacle2.getHeight(), retrievedTestObstacle2.getHeight());
		assertEquals(originalTestObstacle2.getId(), retrievedTestObstacle2.getId());
		assertEquals(originalTestObstacle2.getWidth(), retrievedTestObstacle2.getWidth());
	
		assertEquals("Test Obstacle 3", retrievedTestObstacle3.getId());
	}
	
}
