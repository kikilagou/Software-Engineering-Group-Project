package Model;

import java.util.HashMap;

import Controller.Controller;

/**
 * Created by asherfischbaum on 25/02/2017.
 */
public class Computations {
	// resa 240 meters
    // we'll take blast  to be 500 m
    // strip end 60 meters

	private final double blastLength;
    private Runway runway;


    public 	Computations(Runway runway) {
        this.runway = runway;
        blastLength = 500;
    }

    public boolean checkIfObstacleExists(){
        return runway.getObstacle() != null;
    }

    //Method to retrieve the map of recalculated values
    public HashMap<String, Double> getCalcs(Runway runway)
    {
    	HashMap<String, Double> results = new HashMap<String, Double>();
    	Runway complement = runway.getComplement();
    	
    	//check if there is an obstacle
    	if(runway.getObstacle() != null)
    	{	
    		//call the appropriate methods depending on which side of the runway the obstacle is
	    	if(runway.getObstacle().getDistanceFromThresh() + runway.getObstacle().getDepth()/2
	    			< (runway.getRunwayLength()/2) )
			{
	    		results.putAll(takeOffAwayLandingover(runway, true));
	    		results.put(Controller.OBS_POS, -1d);
	    		if(complement!=null)
	    			results.putAll(takeOffTowardsLandingTowards(runway.getComplement(), false));
			}
	    	else
	    	{
	    		results.putAll(takeOffTowardsLandingTowards(runway, true));
	    		results.put(Controller.OBS_POS, 1d);
	    		if(complement!=null)
	    			results.putAll(takeOffAwayLandingover(runway.getComplement(), false));
	    	}
    	}
    	else
    	{
    		results.put(Controller.orgLDAl, runway.getLda());
    		results.put(Controller.orgTORAl, runway.getTora());
    		results.put(Controller.orgTODAl, runway.getToda());
    		results.put(Controller.orgASDAl, runway.getAsda());
    		
    		if(complement != null)
    		{
    			results.put(Controller.orgLDAr, complement.getLda());
        		results.put(Controller.orgTORAr, complement.getTora());
        		results.put(Controller.orgTODAr, complement.getToda());
        		results.put(Controller.orgASDAr, complement.getAsda());
    		}
    	}
    	
    	return results;
    }
    
    // Calculates from left to right if obs on left
    // Calculates from right to left if obs on right    
    private HashMap<String, Double> takeOffAwayLandingover(Runway runway, boolean leftRight){
        if (!checkIfObstacleExists()) {

            return getValueMap(runway.getTora(), runway.getAsda(), runway.getToda(), runway.getLda(), runway.getObstacle().getHeight() * 50d, runway.getTora(), runway.getAsda(), runway.getToda(), runway.getLda(), leftRight);
        }

        double currentTORA = runway.getTora();
        double currentTODA = runway.getToda();
        double currentASDA = runway.getAsda();
        double currentLDA = runway.getLda();
        
        // original tora - blast protection - distance for threshold - displaced threshold
        double newTORA = currentTORA - blastLength - runway.getObstacle().getDistanceFromThresh() - runway.getThresholdDisplacement();
        
        // new asda = TORA + stopway
        double newASDA = newTORA + runway.getStopwayLength();
        
        // new TODA = TORA + Clearway
        double newTODA = newTORA + runway.getClearwayLength();        
        
        //(R)LDA = (C)LDA - DistFromThresh - max(max(slope,RESA)+strip end, blast protection) 
        double newLDA = currentLDA - runway.getObstacle().getDistanceFromThresh() -
                Math.max(Math.max(240, (runway.getObstacle().getHeight() * 50)) + runway.getStripEnd(), blastLength);
 
               		
                
        return getValueMap(newTORA, newASDA, newTODA, newLDA, runway.getObstacle().getHeight() * 50d, currentTORA, currentASDA, currentTODA, currentLDA, leftRight);
    }

    // Calculate right to left if obs on left
    // Calculate left to right if obs on right
    private HashMap<String, Double> takeOffTowardsLandingTowards(Runway runway, boolean leftRight)
    {

        if (!checkIfObstacleExists()) {

            return getValueMap(runway.getTora(), runway.getAsda(), runway.getToda(), runway.getLda(),runway.getObstacle().getHeight() * 50d, runway.getTora(), runway.getAsda(), runway.getToda(), runway.getLda(), leftRight);
        }
        
        
        double currentTORA = runway.getTora();
        double currentTODA = runway.getToda();
        double currentASDA = runway.getAsda();
        double currentLDA = runway.getLda();
        
        // Distance from threshold - strip end - max(RESA,slope calculation)
        double newTORA = runway.getObstacle().getDistanceFromThresh() + runway.getThresholdDisplacement() - runway.getStripEnd() - Math.max(240, (runway.getObstacle().getHeight() * 50));

        double ASDA = newTORA;

        double TODA = newTORA;

        // distance from threshold - RESA - strip end
        double LDA  = runway.getObstacle().getDistanceFromThresh() - 240 - runway.getStripEnd();

        return getValueMap(newTORA, ASDA, TODA, LDA, runway.getObstacle().getHeight() * 50d, currentTORA, currentTODA, currentASDA, currentLDA, leftRight);
    }

    // Returns the values in a universally recognisable format
    private HashMap<String, Double> getValueMap(double newTORA, double newASDA, double newTODA, 
    		double newLDA, double hx50, double orgTORA, 
    		double orgASDA, double orgTODA, double orgLDA, 
    		boolean leftRight){
        HashMap<String, Double> newValues = new HashMap<>();
       
        if(leftRight)
        {
        	newValues.put(Controller.TORAl, newTORA);
            newValues.put(Controller.ASDAl, newASDA);
            newValues.put(Controller.TODAl, newTODA);
            newValues.put(Controller.LDAl, newLDA);
            newValues.put(Controller.hx50, hx50);
            newValues.put(Controller.orgTORAl, orgTORA);
            newValues.put(Controller.orgTODAl, orgTODA);
            newValues.put(Controller.orgASDAl, orgASDA);
            newValues.put(Controller.orgLDAl, orgLDA);
        }
        else
        {
        	newValues.put(Controller.TORAr, newTORA);
            newValues.put(Controller.ASDAr, newASDA);
            newValues.put(Controller.TODAr, newTODA);
            newValues.put(Controller.LDAr, newLDA);
            newValues.put(Controller.hx50, hx50);
            newValues.put(Controller.orgTORAr, orgTORA);
            newValues.put(Controller.orgTODAr, orgTODA);
            newValues.put(Controller.orgASDAr, orgASDA);
            newValues.put(Controller.orgLDAr, orgLDA);
        }
        
        return newValues;
    }
    
}


