package Model;

import java.util.*;

/**
 * Created by asherfischbaum on 22/02/2017.
 */
// Represents and 
public class Airport {

    HashMap<String, Runway> runways;
    String airportName;

    public Airport(String name){
        airportName = name;
        
        runways = new HashMap<>();
    }

    public void addRunway(Runway runway){
        runways.put(runway.getId(), runway);
    }

    public void removeRunway(String runwayID){
        runways.remove(runwayID);
    }

    public List<Runway> getRunways() {
        ArrayList<Runway> runwaysList = new ArrayList<>();
        Iterator it =  runways.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            runwaysList.add((Runway) pair.getValue());
        }

        return runwaysList;
    }

    public String getAirportName() {
        return airportName;
    }

    public Runway getRunway(String runwayID){
        return runways.get(runwayID);
    }
}
