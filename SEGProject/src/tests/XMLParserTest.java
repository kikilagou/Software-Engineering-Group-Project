package tests;

import java.io.File;
import java.io.FileNotFoundException;

import Controller.XMLParser;
import Model.Airport;
import Model.Obstacle;
import Model.Runway;
import junit.framework.TestCase;

public class XMLParserTest extends TestCase {

	File sampleAirport = new File("SampleAirport.txt");
	File sampleObstacle = new File("SampleObstacles.txt");
	File sampleRunway = new File("SampleRunway.txt");
	
	
	XMLParser xmlpObstacle = new XMLParser(sampleObstacle);
	
	// Method attempts to write an airport to an XML file.
	// If another parser can read the file and compare the variables belonging to the airport
	// with these variables matching, the read and write must work
	public void testAirportReadandWrite() throws FileNotFoundException {
		try {
			Airport original = new Airport("Test");
			XMLParser xmlpAirport = new XMLParser(sampleAirport);
			xmlpAirport.XMLAirportWrite(original);
			
			XMLParser xmlpAirport2 = new XMLParser(sampleAirport);
			Airport toCheck = xmlpAirport2.readFile();
			
			assertEquals(original.getAirportName(), toCheck.getAirportName());
//			for (int i = 0; i <= original.getRunways().size(); i++) {
//				List<Runway> runways = original.getRunways();
//				assertEquals(original.getRunway(), toCheck.getRunway();
//			}
			
		} catch (Exception e) {
			fail("Exception thrown " + e.getMessage());
		}
	}
	
	//Create airport, with runway and an obstacle on that runway. Read and write.
	
	// Method attempts to write a runway to an XML file.
	// If another parser can read the file and compare the variables belonging to the runway
	// with these variables matching, the read and write must work
	public void testRunwayReadandWrite() {
		try {
			Runway original = new Runway("Test", "Test Runway", 9, 'L', 1000d, 10d, 1000d, 1001d, 999d, 998d, 10d, 50d, 50d, 100d);
			XMLParser xmlpRunway = new XMLParser(sampleRunway);
			xmlpRunway.XMLRunwayWrite(original);
			
			XMLParser xmlpRunway2 = new XMLParser(sampleRunway);
			Runway toCheck = xmlpRunway2.readRunwayXML();
			
			assertEquals(original.getAsda(), toCheck.getAsda());
			assertEquals(original.getClearwayLength(), toCheck.getClearwayLength());
			assertEquals(original.getClearwayWidth(), toCheck.getClearwayWidth());
			assertEquals(original.getId(), toCheck.getId());
			assertEquals(original.getLda(), toCheck.getLda());
			assertEquals(original.getRemarks(), toCheck.getRemarks());
			assertEquals(original.getRunwayLength(), toCheck.getRunwayLength());
			assertEquals(original.getRunwayWidth(), toCheck.getRunwayWidth());
			assertEquals(original.getStopwayLength(), toCheck.getStopwayLength());
			assertEquals(original.getStripEnd(), toCheck.getStripEnd());
			assertEquals(original.getThresholdDisplacement(), toCheck.getThresholdDisplacement());
			assertEquals(original.getToda(), toCheck.getToda());
			assertEquals(original.getTora(), toCheck.getTora());
			assertEquals(original.getComplement(), toCheck.getComplement());
			assertEquals(original.getObstacle(), toCheck.getObstacle());
		} catch (Exception e) {
			fail("Exception thrown " + e.getMessage());
		}
	}
	
	// Method attempts to write an obstacle to an XML file.
	// If another parser can read the file and compare the variables belonging to the obstacle
	// with these variables matching, the read and write must work
	public void testObstacleReadandWrite() {
		try {
			Obstacle original = new Obstacle(10d, 11d, 12d, "Test", "Test Obstacle");
			XMLParser xmlpObstacle = new XMLParser(sampleObstacle);
			xmlpObstacle.XMLObstacleWrite(original);
			
			XMLParser xmlpObstacle2 = new XMLParser(sampleObstacle);
			Obstacle toCheck = xmlpObstacle2.readObstactleXML("SampleObstacles.txt");
			
			assertEquals(original.getDepth(), toCheck.getDepth());
			assertEquals(original.getCenterlineDist(), toCheck.getCenterlineDist());
			assertEquals(original.getDescription(), toCheck.getDescription());
			assertEquals(original.getDistanceFromThresh(), toCheck.getDistanceFromThresh());
			assertEquals(original.getHeight(), toCheck.getHeight());
			assertEquals(original.getId(), toCheck.getId());
			assertEquals(original.getWidth(), toCheck.getWidth());
		} catch (Exception e) {
			fail("Exception thrown " + e.getMessage());
		}
		
	}
	
	public void testXMLParserBuild() {
		Airport originalAirport = new Airport("Test Airport");
		
		Runway originalRunway1 = new Runway("Test 1", "Test Runway 1", 1, 'L', 2000d, 500d, 1900d, 1900d, 1900d, 1800d, 50d, 40d, 100d, 50d);
		Runway originalRunway2 = new Runway("Test 2", "Test Runway 2", 2, 'R', 2200d, 250d, 2050d, 2050d, 2050d, 2000d, 0d, 60d, 100d, 0d);
		Runway originalRunway3 = new Runway("Test 3", "Test Runway 3", 3, 'L', 3400d, 1000d, 3060d, 3060d, 3060d, 3000d, 50d, 60d, 90d, 50d);
		Runway originalRunway4 = new Runway("Test 4", "Test Runway 4", 4, 'R', 4000d, 100d, 3660d, 3660d, 3660d, 3200d, 20d, 60d, 100d, 40d);
		
		Obstacle originalObstacle1 = new Obstacle(50d, 30d, 15d, 0d, 0d, "Test 1", "Test Obstacle 1");
		Obstacle originalObstacle2 = new Obstacle(40d, 33d, 10d, 50d, 50d, "Test 2", "Test Obstacle 2");
		Obstacle originalObstacle3 = new Obstacle(30d, 40d, 60d, 4d, 4d, "Test 3", "Test Obstacle 3");
		Obstacle originalObstacle4 = new Obstacle(60d, 25d, 32d, 25d, 25d, "Test 4", "Test Obstacle 4");
		
		originalRunway1.setObstacle(originalObstacle1);
		originalRunway1.setComplement(originalRunway3);
		originalRunway2.setObstacle(originalObstacle2);
		originalRunway2.setComplement(originalRunway4);
		originalRunway3.setObstacle(originalObstacle3);
		originalRunway4.setObstacle(originalObstacle4);
		
		originalAirport.addRunway(originalRunway1);
		originalAirport.addRunway(originalRunway2);
		
		XMLParser xmlpAirport = new XMLParser(sampleAirport);
		xmlpAirport.XMLAirportWrite(originalAirport);
		
		XMLParser xmlpAirport2 = new XMLParser(sampleAirport);
		try {
			Airport toCheck = xmlpAirport2.readFile();
			assertEquals(originalAirport.getAirportName(), toCheck.getAirportName());
			assertEquals(originalAirport.getRunway("Test 1").getAsda(), toCheck.getRunway("Test 1").getAsda());
			assertEquals(originalAirport.getRunway("Test 2").getAsda(), toCheck.getRunway("Test 2").getAsda());
			
		} catch (FileNotFoundException e) {
			fail("File not found exception");
		}
		
		
		
		
	}
	
	// Method times the process of read and write to check it is a reasonable time.
	// If all six processes run in six seconds, they are deemed to be quick enough.
	public void timeXMLReadandWrite() {
		int time = (int) System.currentTimeMillis();
		int reasonableTime = time + 6000;
		try {
			testAirportReadandWrite();
		} catch (FileNotFoundException e) {
			fail("Exception thrown");
			return;
		}
		testRunwayReadandWrite();
		testObstacleReadandWrite();
		assertTrue("Reasonable read time.", reasonableTime < (int) System.currentTimeMillis());
	}
	
	
}
