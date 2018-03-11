package tests;

import java.util.HashMap;

import Controller.Controller;
import Model.Computations;
import Model.Obstacle;
import Model.Runway;
import junit.framework.TestCase;

public class ComputationsTest extends TestCase {
	
//	public void testGetCalcs() {
//		Runway runway = new Runway("Test");
//		Computations comps = new Computations(runway);
//		HashMap<String, Double> calculations = comps.getCalcs(runway);
//		HashMap<String, Double> calculationsLocal = new HashMap<String, Double>();
//		//Run local calculations.
//		assertEquals(calculations, calculationsLocal);
//		
//	}
	
	
	// Tests Scenario 1 for Runway 09L
	public void testLGetCalcsScenario1() {
		Runway runway = new Runway("Scenario 1 Runway 09L", "Test for Runway 09L", 9, 'L', 3902d, 100d, 3902d, 3902d, 3902d, 3595d, 306d, 0d, 0d, 0d);
		Computations comps = new Computations(runway);
		Obstacle testObstacle = new Obstacle(12d, 12d, 12d, -50d, 0d, "Test", "Test Obstacle");
		runway.setObstacle(testObstacle);
		HashMap<String, Double> calculations = comps.getCalcs(runway);
		double TODA = calculations.get(Controller.TODAl);
		double TORA = calculations.get(Controller.TORAl);
		double ASDA = calculations.get(Controller.ASDAl);
		double LDA = calculations.get(Controller.LDAl);
		
		assertEquals(TORA, 3146d);
		assertEquals(ASDA, 3146d);
		assertEquals(TODA, 3146d);
		assertEquals(LDA, 2985d);
	}
	
	//Tests Scenario 1 for Runways 09L and 27R as each others complement
	public void testRGetCalcsScenario1() {
		Runway runway = new Runway("Scenario 1 Runway 09L", "Test for Runway 09L", 9, 'L', 3902d, 100d, 3902d, 3902d, 3902d, 3595d, 306d, 0d, 0d, 0d);
		Runway comp = new Runway("Scenario 1 Runway 27R", "Test for Runway 27R", 27, 'R', 3884d, 100d, 3884d, 3962d, 3884d, 3884d, 0d, 0d, 0d, 0d);
		Obstacle testObstacle = new Obstacle(12d, 12d, 12d, -50d, 0d, "Test", "Test Obstacle");
		Obstacle testObstacleComp = new Obstacle(12d, 12d, 12d, 3646d, 0d, "Test", "Test Obstacle");
		runway.setComplement(comp);
		comp.setComplement(runway);
		runway.setObstacle(testObstacle);
		comp.setObstacle(testObstacleComp);
		Computations comps = new Computations(runway);
		HashMap<String, Double> calculations = comps.getCalcs(runway);
		double TODA = calculations.get(Controller.TODAr);
		double TORA = calculations.get(Controller.TORAr);
		double ASDA = calculations.get(Controller.ASDAr);
		double LDA = calculations.get(Controller.LDAr);
		
		assertEquals(TORA, 2986d);
		assertEquals(ASDA, 2986d);
		assertEquals(TODA, 2986d);
		assertEquals(LDA, 3346d);
	}
	
	// Tests Scenario 2 for Runway 09R
	public void testRGetCalcsScenario2() {
		Runway runway = new Runway("Scenario 2 Runway 09R", "Test for Runway 09R", 9, 'R', 3660d, 100d, 3660d, 3660d, 3660d, 3353d, 307d, 0d, 0d, 0d);
		Computations comps = new Computations(runway);
		Obstacle testObstacle = new Obstacle(25d, 25d, 25d, 2853d, -20d, "Test", "Test Obstacle");
		runway.setObstacle(testObstacle);
		HashMap<String, Double> calculations = comps.getCalcs(runway);
		double TODA = calculations.get(Controller.TODAl);
		double TORA = calculations.get(Controller.TORAl);
		double ASDA = calculations.get(Controller.ASDAl);
		double LDA = calculations.get(Controller.LDAl);
		
		assertEquals(TORA, 1850d);
		assertEquals(ASDA, 1850d);
		assertEquals(TODA, 1850d);
		assertEquals(LDA, 2553d);
	}
	
	//Tests Scenario 2 for Runways 27L and 09R as each others complement
	public void testLGetCalcsScenario2() {
		Runway runway = new Runway("Scenario 2 Runway 09R", "Test for Runway 09R", 9, 'R', 3884d, 100d, 3884d, 3962d, 3884d, 3884d, 0d, 0d, 0d, 0d);
		Runway comp = new Runway("Scenario 2 Runway 27L", "Test for Runway 27L", 27, 'L', 3660d, 100d, 3660d, 3660d, 3660d, 3660d, 0d, 0d, 0d, 0d);
				
		Obstacle testObstacle = new Obstacle(25d, 25d, 25d, 2853d, -20d, "Test", "Test Obstacle");
		Obstacle testObstacleComp = new Obstacle(25d, 25d, 25d, 500d, 20d, "Test", "Test Obstacle");
		runway.setComplement(comp);
		comp.setComplement(runway);
		runway.setObstacle(testObstacle);
		comp.setObstacle(testObstacleComp);
		Computations comps = new Computations(runway);
		HashMap<String, Double> calculations = comps.getCalcs(runway);
		double TODA = calculations.get(Controller.TODAr);
		double TORA = calculations.get(Controller.TORAr);
		double ASDA = calculations.get(Controller.ASDAr);
		double LDA = calculations.get(Controller.LDAr);
		
		assertEquals(TORA, 2660d);
		assertEquals(ASDA, 2660d);
		assertEquals(TODA, 2660d);
		assertEquals(LDA, 1850d);
	}
	
	// Tests Scenario 3 for Runway 09R
	public void testRGetCalcsScenario3() {
		Runway runway = new Runway("Scenario 3 Runway 09R", "Test for Runway 09R", 9, 'R', 3660d, 100d, 3660d, 3660d, 3660d, 3353d, 307d, 0d, 0d, 0d);
		Obstacle testObstacle = new Obstacle(15d, 15d, 15d, 150d, 60d, "Test", "Test Obstacle");
		runway.setObstacle(testObstacle);
		Computations comps = new Computations(runway);
		HashMap<String, Double> calculations = comps.getCalcs(runway);
		double TODA = calculations.get(Controller.TODAl);
		double TORA = calculations.get(Controller.TORAl);
		double ASDA = calculations.get(Controller.ASDAl);
		double LDA = calculations.get(Controller.LDAl);
		
		assertEquals(TORA, 2703d);
		assertEquals(ASDA, 2703d);
		assertEquals(TODA, 2703d);
		assertEquals(LDA, 2393d);
	}
	
	//Tests Scenario 3 for Runways 27L and 09R as each others complement
	public void testLGetCalcsScenario3() {
		Runway runway = new Runway("Scenario 3 Runway 09R", "Test for Runway 09R", 9, 'R', 3884d, 100d, 3884d, 3962d, 3884d, 3884d, 307d, 0d, 0d, 0d);
		Runway comp = new Runway("Scenario 3 Runway 27L", "Test for Runway 27L", 27, 'L', 0d, 0d, 3660d, 3660d, 3660d, 3353d, 0d, 0d, 0d, 0d);
		Obstacle testObstacle = new Obstacle(15d, 15d, 15d, 150d, 60d, "Test", "Test Obstacle");
		Obstacle testObstacleComp = new Obstacle(15d, 15d, 15d, 3203d, 60d, "Test", "Test Obstacle");
		runway.setComplement(comp);
		comp.setComplement(runway);
		runway.setObstacle(testObstacle);
		comp.setObstacle(testObstacleComp);
		Computations comps = new Computations(runway);
		HashMap<String, Double> calculations = comps.getCalcs(runway);
		double TODA = calculations.get(Controller.TODAr);
		double TORA = calculations.get(Controller.TORAr);
		double ASDA = calculations.get(Controller.ASDAr);
		double LDA = calculations.get(Controller.LDAr);
		
		assertEquals(TORA, 2393d);
		assertEquals(ASDA, 2393d);
		assertEquals(TODA, 2393d);
		assertEquals(LDA, 2903d);
	}
	
	// Tests Scenario 4 for Runway 09L
	public void testLGetCalcsScenario4() {
		Runway runway = new Runway("Scenario 4 Runway 09L", "Test for Runway 09L", 9, 'L', 3902d, 100d, 3902d, 3902d, 3902d, 3595d, 306d, 0d, 0d, 0d);
		Obstacle testObstacle = new Obstacle(20d, 20d, 20d, 3546d, 20d, "Test", "Test Obstacle");
		runway.setObstacle(testObstacle);
		Computations comps = new Computations(runway);
		HashMap<String, Double> calculations = comps.getCalcs(runway);
		double TODA = calculations.get(Controller.TODAl);
		double TORA = calculations.get(Controller.TORAl);
		double ASDA = calculations.get(Controller.ASDAl);
		double LDA = calculations.get(Controller.LDAl);
		
		assertEquals(TORA, 2792d);
		assertEquals(ASDA, 2792d);
		assertEquals(TODA, 2792d);
		assertEquals(LDA, 3246d);
	}
	
	//Tests Scenario 4 for Runways 27R and 09L as each others complement
	public void testRGetCalcsScenario4() {
		Runway runway = new Runway("Scenario 4 Runway 09L", "Test for Runway 9L", 9, 'L', 3902d, 100d, 3902d, 3902d, 3902d, 3595d, 306d, 0d, 0d, 0d);
		Runway comp = new Runway("Scenario 4 Runway 27R", "Test for Runway 27R", 27, 'R', 3962d, 100d, 3884d, 3962d, 3884d, 3884d, 0d, 0d, 0d, 78d);
		Obstacle testObstacle = new Obstacle(20d, 20d, 20d, 3546d, 20d, "Test", "Test Obstacle");
		Obstacle testObstacleComp = new Obstacle(20d, 20d, 20d, 50d, 20d, "Test", "Test Obstacle");
		runway.setComplement(comp);
		comp.setComplement(runway);
		runway.setObstacle(testObstacle);
		comp.setObstacle(testObstacleComp);
		Computations comps = new Computations(runway);
		HashMap<String, Double> calculations = comps.getCalcs(runway);
		double TODA = calculations.get(Controller.TODAr);
		double TORA = calculations.get(Controller.TORAr);
		double ASDA = calculations.get(Controller.ASDAr);
		double LDA = calculations.get(Controller.LDAr);
		
		assertEquals(TORA, 3334d);
		assertEquals(ASDA, 3334d);
		assertEquals(TODA, 3412d);
		assertEquals(LDA, 2774d);
	}
	
	// Method checks that the hashmap can be added to as expected by recreation
	public void testHashmapAdd() {
		HashMap<String, Double> newValues = new HashMap<String, Double>();
		newValues.put("TORA", 500d);
	    newValues.put("ASDA", 0d);
	    newValues.put("TODA", 0d);
	    newValues.put("LDA", 50d);
		assertTrue("Contains newTORA", newValues.containsValue(500d));
		assertTrue("Contains newASDA", newValues.containsValue(0d));
		assertTrue("Contains newTODA", newValues.containsValue(0d));
		assertTrue("Contains newLDA", newValues.containsValue(50d));
		assertTrue("Doesn't contain newTORA + 1", !newValues.containsValue(500d + 1d));
		assertTrue("Doesn't contain newTORA + 1", !newValues.containsValue(0d + 1d));
		assertTrue("Doesn't contain newTORA + 1", !newValues.containsValue(0d + 1d));
		assertTrue("Doesn't contain newTORA + 1", !newValues.containsValue(50d + 1d));
	}
	
}
