package View;

import java.util.HashMap;

import Model.Runway;

// Implemented by classes that need updates from the calculation results
public interface UpdateInterface {
	public void updateGui(HashMap<String, Double> results);
}
