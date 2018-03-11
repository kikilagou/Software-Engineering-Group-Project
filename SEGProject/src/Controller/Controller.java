package Controller;

import Model.Airport;
import Model.Obstacle;
import Model.Computations;
import Model.HelpReader;
import Model.Runway;
import View.Helper;
import View.Menu;
import View.UpdateInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by asherfischbaum on 28/02/2017.
 */
public class Controller {
	private Airport airport;

	private String airportName = "Airport";
	private SQLiteJDBC database;

	private HashMap<Runway, List<UpdateInterface>> runwaysAndUpdates;

	// Constants
	public static final String LDAl = "LDA", TORAl = "TORA", TODAl = "TODA", ASDAl = "ASDA", hx50 = "hx50",
			LDAr = "LDAr", TORAr = "TORAr", TODAr = "TODAr", ASDAr = "ASDAr", OBS_POS = "OBSPOS", orgTORAl = "orgToraL",
			orgTODAl = "orgTodaL", orgASDAl = "orgAsdaL", orgLDAl = "orgLdaL", orgTORAr = "orgToraR",
			orgTODAr = "orgTodaR", orgASDAr = "orgAsdaR", orgLDAr = "orgLdaR";

	private static Controller contr;
	static {
		contr = new Controller();
	}

	public static Controller getContr() {
		return contr;
	}

	public Controller() {
		airport = null;
		runwaysAndUpdates = new HashMap<Runway, List<UpdateInterface>>();
	}

	public static void main(String[] args) {
		contr.init();
	}

	public void init() {
		initModel();
		initView();
	}

	public List<Runway> setAirportName(String name) {
		this.airportName = name;

		if (database.getAirport(name) == null) {
			
			database.insertAirport(name, name);
			System.out.println("Airport added");
		}
		
		this.airport = database.getAirport(name);
		return airport.getRunways();
	}

	private void initModel() {
		database = new SQLiteJDBC();
	}

	private void initView() {
		Menu menu = new Menu(airport);
		menu.init();
	}

	public void readXml(Menu menu, File file) throws Exception {
		XMLParser parser = new XMLParser(file);
		airport = parser.readFile();
		airportName = airport.getAirportName();
		
		for (Runway rn : airport.getRunways()) {
			addRunway(rn);
		}
		menu.refresh();
	}

	public Obstacle readObstXml(File file) throws Exception {
		XMLParser parser = new XMLParser(file);
		Obstacle obst = parser.readObstactleXML(file.getAbsolutePath());
		return obst;
	}

	public void writeXml(File file) {
		XMLParser parser = new XMLParser(file);
		parser.XMLAirportWrite(airport);
	}

	public void addRunway(Runway runway) {
		database.insertRunway(runway, airportName);
		airport.addRunway(runway);
		runwaysAndUpdates.put(runway, new ArrayList<UpdateInterface>());
	}

	public List<Runway> getRunways()
	{
		if(airport == null)
		{
			return new ArrayList<Runway>();
		}
		
		return airport.getRunways();
	}

	public void removeRunway(Runway runway)
	{
		if(airport != null)
		{
			airport.removeRunway(runway.getId());
		}
		database.removeRunway(runway.getId());
	}

	public void registerUpdateInterest(Runway runway, UpdateInterface updater) {
		List<UpdateInterface> updaters = runwaysAndUpdates.get(runway);

		if (updaters != null) {
			updaters.add(updater);
		}
	}

	public void unregisterUpdateInterface(Runway runway, UpdateInterface updater) {
		List<UpdateInterface> updaters = runwaysAndUpdates.get(runway);

		if (updaters != null) {
			updaters.remove(updater);
		}
	}

	public void calculateRequest(Runway runway) {
		Computations comp = new Computations(runway);

		HashMap<String, Double> results = comp.getCalcs(runway);
		List<UpdateInterface> updaters = runwaysAndUpdates.get(runway);

		if (updaters == null)
			return;

		for (UpdateInterface updater : updaters) {
			updater.updateGui(results);
		}
	}

	public void calculateResults(Runway runway, HashMap<String, Double> results) {
		List<UpdateInterface> updaters = runwaysAndUpdates.get(runway);

		if (updaters != null) {
			for (UpdateInterface toUpdate : updaters) {
				toUpdate.updateGui(results);
			}
		}
	}

	public Airport getAirport() {
		return airport;
	}

	public String getHelp() {
		HelpReader h = new HelpReader();

		return h.readHelpFile();
	}
	
	public void close()
	{
		database.shutDownDBConnetion();
	}
}
