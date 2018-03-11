package tests;

import Model.Obstacle;
import Model.Runway;
import junit.framework.TestCase;

public class RunwayTest extends TestCase {
	
	// Method checks that an obstacle can be set on a runway
	public void testObstacleSet() {
		Obstacle obstacle = new Obstacle(12d, 12d, 12d, 50d, 0d, "TestObstacle", "This is a test obstacle.");
		Runway runway = new Runway("TestRunway", "This is a test runway.", 0, 'R', 0d, 0d, 3660d, 3660d, 3660d, 3353d, 306d, 0d, 0d, 0d);
		runway.setObstacle(obstacle);
		assertEquals("Obstacle sets correctly.", runway.getObstacle(), obstacle);
	}
	
	//Method should check that an obstacle can be deleted but I can't find a delete method in Runway
//	public void testObstacleDelete() {
//		Obstacle obstacle = new Obstacle(12d, 0d, 0d, 50d, 0d, "TestObstacle", "This is a test obstacle.");
//		Runway runway = new Runway("TestRunway", "This is a test runway.", 0, 'R', 0d, 0d, 3660d, 3660d, 3660d, 3353d, 306d, 0d, 0d, 0d);
//		runway.setObstacle(obstacle);
////		runway.
//		assertEquals("Obstacle sets correctly.", runway.getObstacle(), obstacle);
//	}

}
