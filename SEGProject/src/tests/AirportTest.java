package tests;

import java.util.List;

import Model.Airport;
import Model.Runway;
import junit.framework.TestCase;

public class AirportTest extends TestCase {

	// Method tests that a runway can be added to an airport
	public void testRunwayAdd() {
		Airport airport = new Airport("Test");
		Runway original = new Runway("Test", "Test Runway", 9, 'L', 3660d, 100d, 3660d, 3660d, 3660d, 3353d, 300d, 0, 0, 0);
		airport.addRunway(original);
		String runwayID = original.getId();
		Runway received = airport.getRunway(runwayID);
		assertEquals("Runway added correctly", original.getAsda(), received.getAsda());
	}
	
	// Method tests that a runway can be removed from an airport
	public void testRunwayRemove() {
		Airport airport = new Airport("Test");
		Runway original = new Runway("Test", "Test Runway", 9, 'L', 3660d, 100d, 3660d, 3600d, 3600d, 3353d, 300d, 0, 0, 0);
		airport.addRunway(original);
		String runwayID = original.getId();
		airport.removeRunway(runwayID);
		List<Runway> listOfRunways = airport.getRunways();
		assertTrue("Runway Deleted", !listOfRunways.contains(original));
	}
}
