package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import Controller.Controller;
import Model.Runway;

//Class to display the calcualtions on a panel by the view for the user's convinience
public class CalculationsDisplay extends JPanel implements UpdateInterface {

	private Runway runway;
	public JTextArea calcs;
	public JScrollPane scroll;

	public CalculationsDisplay(Runway runway) {
		this.runway = runway;
	}

	protected void init() {

		this.setLayout(new BorderLayout());

		calcs = new JTextArea();
		calcs.setEditable(false);
		scroll= new JScrollPane(calcs);
		add(scroll);
	}

	// Method that updates the panel with the values when an obstacle is added
	@Override
	public void updateGui(HashMap<String, Double> values) {		
		
		// Panel plain to begin with
		calcs.setText(null);
		
		// Values to display
		String newTORAStrL, newASDAStrL, newTODAStrL, newLDAStrL, newTORAStrR=null, newASDAStrR=null, newTODAStrR=null, newLDAStrR = null;
		String toraStringL, todaStringL, asdaStringL, ldaStringL, toraStringR=null, todaStringR=null, asdaStringR=null, ldaStringR = null;
		// Check to see if obstacle is on runway
		if (runway.getObstacle() != null) {
			Runway complement = runway.getComplement();
			double slope = values.get(Controller.hx50);

			if (runway.getObstacle().getDistanceFromThresh()
					+ runway.getObstacle().getDepth() / 2 < (runway.getRunwayLength() / 2)) {
			

				// Strings for takeOffAwayLandingOver for runway
				toraStringL= "(R)TORA = TORA - blastProt - distFromThreshold - dt \n";
				newTORAStrL = String.valueOf(values.get(Controller.TORAl)) + " = "
						+ String.valueOf(values.get(Controller.orgTORAl)) + " - " + String.valueOf(500d) + " - "
						+ String.valueOf(runway.getObstacle().getDistanceFromThresh()) + " - "
						+ String.valueOf(runway.getThresholdDisplacement()) + " \n";
				asdaStringL= "(R)ASDA = (R)TORA + stopwayLength \n";
				newASDAStrL = String.valueOf(values.get(Controller.ASDAl)) + " = "
						+ String.valueOf(values.get(Controller.TORAl)) + " + "
						+ String.valueOf(runway.getStopwayLength()) + " \n";
				todaStringL= "(R)TODA = (R)TORA + clearwayLength \n";
				newTODAStrL = String.valueOf(values.get(Controller.TODAl)) + " = "
						+ String.valueOf(values.get(Controller.TORAl)) + " + "
						+ String.valueOf(runway.getClearwayLength()) + " \n";
				ldaStringL= "(R)LDA = LDA - distFromThreshold - max(max(RESA, h*50) + stripEnd , blastProt) \n";
				newLDAStrL = String.valueOf(values.get(Controller.LDAl))
						+ " = " + String
								.valueOf(
										values.get(Controller.orgLDAl))
						+ " - " + String.valueOf(runway.getObstacle().getDistanceFromThresh()) + " - "
						+ String.valueOf(Math.max(
								Math.max(240, (runway.getObstacle().getHeight() * 50)) + runway.getStripEnd(), 500))
						+ " \n";

				
				// Values for the runway complement
				if (complement != null) {
					
					// Strings for takeOffTowardsLandingTowards for complement
					toraStringR= "(R)TORA = distFromThreshold + dt - stripEnd - max(RESA, h*50) \n";
					newTORAStrR = String.valueOf(values.get(Controller.TORAr)) + " = "
							+ String.valueOf(complement.getObstacle().getDistanceFromThresh()) + " + "
							+ String.valueOf(complement.getThresholdDisplacement()) + " - "
							+ String.valueOf(complement.getStripEnd()) + " - "
							+ String.valueOf(Math.max(240, (complement.getObstacle().getHeight() * 50))) + " \n";
					asdaStringR="(R)ASDA = (R)TORA \n";
					newASDAStrR = String.valueOf(values.get(Controller.ASDAr)) + " = "
							+ String.valueOf(values.get(Controller.TORAr)) + " \n";
					todaStringR="(R)TODA = (R)TORA \n";
					newTODAStrR = String.valueOf(values.get(Controller.TODAr)) + " = "
							+ String.valueOf(values.get(Controller.TORAr)) + " \n";
					ldaStringR="(R)LDA = distFromThreshold - RESA - stripEnd \n";
					newLDAStrR = String.valueOf(values.get(Controller.LDAr))
							+ " = " +String.valueOf(complement.getObstacle().getDistanceFromThresh()) + " - "
							+ String.valueOf(240d)+ " - " + String.valueOf(complement.getStripEnd()) + " \n";
					
				}
			} else {
				// TakeOffTowardsLandingTowards for runway
				toraStringL= "(R)TORA = distFromThreshold + dt - stripEnd - max(RESA, h*50) \n";
				newTORAStrL = String.valueOf(values.get(Controller.TORAl)) + " = "
						+ String.valueOf(runway.getObstacle().getDistanceFromThresh()) + " + "
						+ String.valueOf(runway.getThresholdDisplacement()) + " - "
						+ String.valueOf(runway.getStripEnd()) + " - "
						+ String.valueOf(Math.max(240, (runway.getObstacle().getHeight() * 50))) + " \n";
				asdaStringL= "(R)ASDA = (R)TORA \n";
				newASDAStrL = String.valueOf(values.get(Controller.TORAl)) + " = "
						+ String.valueOf(values.get(Controller.TORAl)) + " \n";
				todaStringL= "(R)TODA = (R)TORA \n";
				newTODAStrL = String.valueOf(values.get(Controller.TORAl)) + " = "
						+ String.valueOf(values.get(Controller.TORAl)) + " \n";
				ldaStringL="(R)LDA = distFromThreshold - RESA - stripEnd \n";
				newLDAStrL = String.valueOf(values.get(Controller.LDAr)) + " = " +
						String.valueOf(runway.getObstacle().getDistanceFromThresh()) + " - "
						+ String.valueOf(240d)+ " - " + String.valueOf(runway.getStripEnd()) + " \n";
				
				// If a complement runway exists also do this for the complement
				if(complement!=null){
					
					// Strings for takeOffAwayLandingOver for complement
					toraStringR= "(R)TORA = TORA - blastProt - distFromThreshold - dt \n";
					newTORAStrR = String.valueOf(values.get(Controller.TORAr)) + " = "
							+ String.valueOf(values.get(Controller.orgTORAr)) + " - " + String.valueOf(500d) + " - "
							+ String.valueOf(complement.getObstacle().getDistanceFromThresh()) + " - "
							+ String.valueOf(complement.getThresholdDisplacement()) + " \n";
					asdaStringR= "(R)ASDA = (R)TORA + stopwayLength \n";
					newASDAStrR = String.valueOf(values.get(Controller.ASDAr)) + " = "
							+ String.valueOf(values.get(Controller.TORAr)) + " + "
							+ String.valueOf(complement.getStopwayLength()) + " \n";
					todaStringR= "(R)TORA = (R)TORA + clearwayLength \n";
					newTODAStrR = String.valueOf(values.get(Controller.TODAr)) + " = "
							+ String.valueOf(values.get(Controller.TORAr)) + " + "
							+ String.valueOf(complement.getClearwayLength()) + " \n";
					ldaStringR= "(R)LDA = LDA - distFromThreshold - max(max(RESA, h*50)+stripEnd, blastProt) \n";
					newLDAStrR = String.valueOf(values.get(Controller.LDAr))
							+ " = " + String
									.valueOf(
											values.get(Controller.orgLDAr))
							+ " - " + String.valueOf(complement.getObstacle().getDistanceFromThresh()) + " - "
							+ String.valueOf(Math.max(
									Math.max(240, (complement.getObstacle().getHeight() * 50)) + complement.getStripEnd(), 500))
							+ " \n";
					
				}
				
				
							
				}
			
			// Creating labels to make calculations more clear
			calcs.append("Runway " + runway.toString() + ": \n");
			calcs.append(" \n");
			calcs.append(toraStringL);
			calcs.append(newTORAStrL);
			calcs.append(asdaStringL);
			calcs.append(newASDAStrL);
			calcs.append(todaStringL);
			calcs.append(newTODAStrL);
			calcs.append(ldaStringL);
			calcs.append(newLDAStrL);
			
			// If there is a complement runway also created labels for that
			if(complement!=null){
				calcs.append("\n\n" + "Runway " + complement.toString() + ": \n");
				calcs.append(" \n");
				calcs.append(toraStringR);
				calcs.append(newTORAStrR);
				calcs.append(asdaStringR);
				calcs.append(newASDAStrR);
				calcs.append(todaStringR);
				calcs.append(newTODAStrR);
				calcs.append(ldaStringR);
				calcs.append(newLDAStrR);	
			}
		
		}
	}
}
