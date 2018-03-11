package View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Runway;

// Class enabling the change of colour scheme for the GUI view
public class ColourScheme extends JPanel {

	private MainFrame tool;

	// To hold preset schemes
	private JComboBox<String> setSchemes;

	protected ColourScheme(MainFrame tool) {
		this.tool = tool;
	}

	// Method that created the GUI for the colour scheme editor, this will be
	// added to the controls panel of the main GUI
	protected void init() {

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(3, 1, 3, 1);

		int i = 0;

		JLabel predf = new JLabel("Preified Colour Schemes");
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = i;
		add(predf, c);

		i++;

		// Will have some preset colour schemes for the user to choose from
		setSchemes = new JComboBox<String>();
		addPresetColours();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = i;
		add(setSchemes, c);

		i++;

		JLabel options = new JLabel("Options");
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = i;
		add(options, c);

		i++;
		
		// Will allow the user to customise the colours to anything the want
		// using JColorChooser
		JButton customise = new JButton("Customise Colour Scheme");
		customise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				customiseColours();
			}

		});
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = i;
		add(customise, c);

		i++;

		JLabel ap = new JLabel("Apply to View:");
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = i;
		add(ap, c);

		i++;

		// Click this button to apply the preset colour scheme to the view
		JButton apply = new JButton("Apply");
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				setColours();
				tool.topDownWrapper.getToWrap().repaint();
				tool.compassWrapper.getToWrap().repaint();
				tool.sideRWrapper.getToWrap().repaint();
				tool.sideLWrapper.getToWrap().repaint();
			}

		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 1;
		c.gridy = i;
		add(apply, c);

	}

	// Method to define the colours for the preset colour scheme
	protected void addPresetColours() {
		
		// There are 4 preset schemes to choose from
		String select = "Select Colour Scheme";
		// Sets the colours to the defaults
		String original = "Original";
		// Sets colours to grey hues
		String greyscale = "Greyscale";
		// Sets colours to suitable ones for the colour blind
		String colourblind = "Colour Blind";
		// Sets colours to the opposite of the defaults
		String negative = "Negative";

		// Adding to the combo box
		setSchemes.addItem(select);
		setSchemes.addItem(original);
		setSchemes.addItem(greyscale);
		setSchemes.addItem(colourblind);
		setSchemes.addItem(negative);
	}

	// Method to set the colours for all the components of the view
	protected void setColours() {
		
		// Checks if the selected option is "Original" and sets the original
				// colours for all possible views
		if (setSchemes.getSelectedItem().equals("Original")) {

			// Setting top down view colours
			TopDownRunway tdv = (TopDownRunway) tool.topDownWrapper.getToWrap();
			tdv.backgroundC = new Color(150, 253, 138);
			tdv.backgroundGrassC = new Color(12, 183, 0, 60);
			tdv.runwayC = new Color(198, 198, 198, 255);
			tdv.runwayMarkingsC = Color.WHITE;
			tdv.clearwayC = new Color(34, 20, 164, 120);
			tdv.stopwayC = new Color(242, 242, 242, 255);
			tdv.stopwaySecondaryC = new Color(255, 255, 255, 80);
			tdv.displacedThresholdC = Color.BLACK;
			tdv.todaC = Color.black;
			tdv.asdaC = Color.black;
			tdv.toraC = Color.black;
			tdv.ldaC = Color.black;
			tdv.leftRightArrowC = Color.black;
			tdv.rightLeftArrowC = Color.black;
			tdv.clearedWidthC = new Color(34, 20, 164, 75);
			tdv.gradedWidthC = new Color(111, 0, 158, 75);
			tdv.obstacleC = new Color(255, 0, 0, 255);

			// Setting the compass orientation colours
			TopDownRunway tdvComp= (TopDownRunway) tool.compassWrapper.getToWrap();
			tdvComp.backgroundC = new Color(150, 253, 138);
			tdvComp.backgroundGrassC = new Color(12, 183, 0, 60);
			tdvComp.runwayC = new Color(198, 198, 198, 255);
			tdvComp.runwayMarkingsC = Color.WHITE;
			tdvComp.clearwayC = new Color(34, 20, 164, 120);
			tdvComp.stopwayC = new Color(242, 242, 242, 255);
			tdvComp.stopwaySecondaryC = new Color(255, 255, 255, 80);
			tdvComp.displacedThresholdC = Color.BLACK;
			tdvComp.todaC = Color.black;
			tdvComp.asdaC = Color.black;
			tdvComp.toraC = Color.black;
			tdvComp.ldaC = Color.black;
			tdvComp.leftRightArrowC = Color.black;
			tdvComp.rightLeftArrowC = Color.black;
			tdvComp.clearedWidthC = new Color(34, 20, 164, 75);
			tdvComp.gradedWidthC = new Color(111, 0, 158, 75);
			tdvComp.obstacleC = new Color(255, 0, 0, 255);

			// Setting the right side view colours
			SideOnView sideR = (SideOnView) tool.sideRWrapper.getToWrap();
			sideR.backgroundBelowC = new Color(150, 253, 138);
			sideR.backgroundAboveC = new Color(135, 206, 235);
			sideR.backgroundCloudC = new Color(240, 240, 240, 50);
			sideR.backgroundGrassC = new Color(12, 183, 0);
			sideR.runwayC = Color.black;
			sideR.ldaC = Color.BLACK;
			sideR.toraC = Color.BLACK;
			sideR.todaC = Color.black;
			sideR.asdaC = Color.black;
			sideR.stripEndC = Color.black;
			sideR.resaC = Color.BLACK;
			sideR.hx50C = Color.BLACK;
			sideR.obsC = Color.red;
			sideR.slopeC = Color.black;
			sideR.leftRightArrowC = Color.black;
			sideR.rightLeftArrowC = Color.black;

			// Setting the left side view colours
			SideOnView sideL = (SideOnView) tool.sideLWrapper.getToWrap();
			sideL.backgroundBelowC = new Color(150, 253, 138);
			sideL.backgroundAboveC = new Color(135, 206, 235);
			sideL.backgroundCloudC = new Color(240, 240, 240, 50);
			sideL.backgroundGrassC = new Color(12, 183, 0);
			sideL.runwayC = Color.black;
			sideL.ldaC = Color.BLACK;
			sideL.toraC = Color.BLACK;
			sideL.todaC = Color.black;
			sideL.asdaC = Color.black;
			sideL.stripEndC = Color.black;
			sideL.resaC = Color.BLACK;
			sideL.hx50C = Color.BLACK;
			sideL.obsC = Color.red;
			sideL.slopeC = Color.black;
			sideL.leftRightArrowC = Color.black;
			sideL.rightLeftArrowC = Color.black;
		}

		// Checks if the selected option is "Greyscale" and sets the colours for
				// all possible views
		if (setSchemes.getSelectedItem().equals("Greyscale")) {

			// Sets colours for top down view
			TopDownRunway tdv = (TopDownRunway) tool.topDownWrapper.getToWrap();
			tdv.backgroundC = new Color(102, 102, 102);
			tdv.backgroundGrassC = new Color(0, 0, 0);
			tdv.runwayC = new Color(204, 204, 204);
			tdv.runwayMarkingsC = Color.WHITE;
			tdv.clearwayC = new Color(50, 50, 50);
			tdv.stopwayC = new Color(178, 178, 178);
			tdv.stopwaySecondaryC = new Color(178, 178, 178);
			tdv.displacedThresholdC = Color.BLACK;
			tdv.todaC = Color.black;
			tdv.asdaC = Color.black;
			tdv.toraC = Color.black;
			tdv.ldaC = Color.black;
			tdv.leftRightArrowC = Color.black;
			tdv.rightLeftArrowC = Color.black;
			tdv.clearedWidthC = new Color(102, 102, 102);
			tdv.gradedWidthC = new Color(153, 153, 153);
			tdv.obstacleC = new Color(0, 0, 0);

			// Sets colours for the compass orientation view
			TopDownRunway tdvComp= (TopDownRunway) tool.compassWrapper.getToWrap();
			tdvComp.backgroundC = new Color(102, 102, 102);
			tdvComp.backgroundGrassC = new Color(0, 0, 0);
			tdvComp.runwayC = new Color(204, 204, 204);
			tdvComp.runwayMarkingsC = Color.WHITE;
			tdvComp.clearwayC = new Color(50, 50, 50);
			tdvComp.stopwayC = new Color(178, 178, 178);
			tdvComp.stopwaySecondaryC = new Color(178, 178, 178);
			tdvComp.displacedThresholdC = Color.BLACK;
			tdvComp.todaC = Color.black;
			tdvComp.asdaC = Color.black;
			tdvComp.toraC = Color.black;
			tdvComp.ldaC = Color.black;
			tdvComp.leftRightArrowC = Color.black;
			tdvComp.rightLeftArrowC = Color.black;
			tdvComp.clearedWidthC = new Color(102, 102, 102);
			tdvComp.gradedWidthC = new Color(153, 153, 153);
			tdvComp.obstacleC = new Color(0, 0, 0);

			// Sets the colours for the right side view
			SideOnView sideR = (SideOnView) tool.sideRWrapper.getToWrap();
			sideR.backgroundBelowC = new Color(102, 102, 102);
			sideR.backgroundAboveC = new Color(204, 204, 204);
			sideR.backgroundCloudC = Color.WHITE;
			sideR.backgroundGrassC = new Color(0, 0, 0);
			sideR.runwayC = Color.black;
			sideR.ldaC = Color.BLACK;
			sideR.toraC = Color.BLACK;
			sideR.todaC = Color.black;
			sideR.asdaC = Color.black;
			sideR.stripEndC = Color.black;
			sideR.resaC = Color.BLACK;
			sideR.hx50C = Color.BLACK;
			sideR.obsC = new Color(0, 0, 0);
			sideR.slopeC = Color.black;
			sideR.leftRightArrowC = Color.black;
			sideR.rightLeftArrowC = Color.black;

			// Sets the colours for the left side view
			SideOnView sideL = (SideOnView) tool.sideLWrapper.getToWrap();
			sideL.backgroundBelowC = new Color(102, 102, 102);
			sideL.backgroundAboveC = new Color(204, 204, 204);
			sideL.backgroundCloudC = Color.WHITE;
			sideL.backgroundGrassC = new Color(0, 0, 0);
			sideL.runwayC = Color.black;
			sideL.ldaC = Color.BLACK;
			sideL.toraC = Color.BLACK;
			sideL.todaC = Color.black;
			sideL.asdaC = Color.black;
			sideL.stripEndC = Color.black;
			sideL.resaC = Color.BLACK;
			sideL.hx50C = Color.BLACK;
			sideL.obsC = new Color(0, 0, 0);
			sideL.slopeC = Color.black;
			sideL.leftRightArrowC = Color.black;
			sideL.rightLeftArrowC = Color.black;
		}

		// Checks if the selected option is "Negative" and sets the colours for
				// all possible views
		if (setSchemes.getSelectedItem().equals("Negative")) {

			// Sets colours for top down view
			TopDownRunway tdv = (TopDownRunway) tool.topDownWrapper.getToWrap();
			tdv.backgroundC = new Color(241, 138, 253);
			tdv.backgroundGrassC = new Color(171, 0, 183);
			tdv.runwayC = new Color(198, 198, 198, 255); 
			tdv.runwayMarkingsC = Color.BLACK;
			tdv.clearwayC = new Color(34, 20, 164, 120);
			tdv.stopwayC = new Color(242, 242, 242, 255);
			tdv.stopwaySecondaryC = new Color(255, 255, 255, 80);
			tdv.displacedThresholdC = Color.WHITE;
			tdv.todaC = Color.white;
			tdv.asdaC = Color.white;
			tdv.toraC = Color.white;
			tdv.ldaC = Color.white;
			tdv.leftRightArrowC = Color.white;
			tdv.rightLeftArrowC = Color.white;
			tdv.clearedWidthC = new Color(150, 164, 20);
			tdv.gradedWidthC = new Color(47, 158, 0);
			tdv.obstacleC = new Color(0, 255, 255);

			// Sets colours for compass orientation view
			TopDownRunway tdvComp = (TopDownRunway) tool.compassWrapper.getToWrap();
			tdvComp.backgroundC = new Color(241, 138, 253);
			tdvComp.backgroundGrassC = new Color(171, 0, 183);
			tdvComp.runwayC = new Color(198, 198, 198, 255); 
			tdvComp.runwayMarkingsC = Color.BLACK;
			tdvComp.clearwayC = new Color(34, 20, 164, 120);
			tdvComp.stopwayC = new Color(242, 242, 242, 255);
			tdvComp.stopwaySecondaryC = new Color(255, 255, 255, 80);
			tdvComp.displacedThresholdC = Color.WHITE;
			tdvComp.todaC = Color.white;
			tdvComp.asdaC = Color.white;
			tdvComp.toraC = Color.white;
			tdvComp.ldaC = Color.white;
			tdvComp.leftRightArrowC = Color.white;
			tdvComp.rightLeftArrowC = Color.white;
			tdvComp.clearedWidthC = new Color(150, 164, 20);
			tdvComp.gradedWidthC = new Color(47, 158, 0);
			tdvComp.obstacleC = new Color(0, 255, 255);

			// Sets colours for right side view
			SideOnView sideR = (SideOnView) tool.sideRWrapper.getToWrap();
			sideR.backgroundBelowC = new Color(241, 138, 253);
			sideR.backgroundAboveC = new Color(235, 164, 135);
			sideR.backgroundCloudC = new Color(0, 0, 0);
			sideR.backgroundGrassC = new Color(171, 0, 183);
			sideR.runwayC = Color.white;
			sideR.ldaC = Color.white;
			sideR.toraC = Color.white;
			sideR.todaC = Color.white;
			sideR.asdaC = Color.white;
			sideR.stripEndC = Color.white;
			sideR.resaC = Color.white;
			sideR.hx50C = Color.white;
			sideR.obsC = new Color(0, 255, 255);
			sideR.slopeC = Color.white;
			sideR.leftRightArrowC = Color.white;
			sideR.rightLeftArrowC = Color.white;

			// Sets colours for left side view
			SideOnView sideL = (SideOnView) tool.sideLWrapper.getToWrap();
			sideL.backgroundBelowC = new Color(241, 138, 253);
			sideL.backgroundAboveC = new Color(235, 164, 135);
			sideL.backgroundCloudC = new Color(0, 0, 0);
			sideL.backgroundGrassC = new Color(171, 0, 183);
			sideL.runwayC = Color.white;
			sideL.ldaC = Color.white;
			sideL.toraC = Color.white;
			sideL.todaC = Color.white;
			sideL.asdaC = Color.white;
			sideL.stripEndC = Color.white;
			sideL.resaC = Color.white;
			sideL.hx50C = Color.white;
			sideL.obsC = new Color(0, 255, 255);
			sideL.slopeC = Color.white;
			sideL.leftRightArrowC = Color.white;
			sideL.rightLeftArrowC = Color.white;
		}

		// Checks if the selected option is "Colour Blind" and sets the colours
				// for all possible views
		if (setSchemes.getSelectedItem().equals("Colour Blind")) {

			// Sets colours for top down view
			TopDownRunway tdv = (TopDownRunway) tool.topDownWrapper.getToWrap();
			tdv.backgroundC = new Color(240, 228, 66);
			tdv.backgroundGrassC = new Color(213, 94, 0);
			tdv.runwayC = new Color(230, 159, 0);
			tdv.runwayMarkingsC = Color.BLACK;
			tdv.clearwayC = new Color(0, 158, 115);
			tdv.stopwayC = new Color(204, 121, 167);
			tdv.stopwaySecondaryC = new Color(204, 121, 167);
			tdv.displacedThresholdC = Color.BLACK;
			tdv.todaC = Color.black;
			tdv.asdaC = Color.black;
			tdv.toraC = Color.black;
			tdv.ldaC = Color.black;
			tdv.leftRightArrowC = Color.black;
			tdv.rightLeftArrowC = Color.black;
			tdv.clearedWidthC = new Color(150, 164, 20);
			tdv.gradedWidthC = new Color(0, 114, 178);
			tdv.obstacleC = new Color(86, 180, 233);

			// Sets colours for compass orientation
			TopDownRunway tdvComp = (TopDownRunway) tool.compassWrapper.getToWrap();
			tdvComp.backgroundC = new Color(240, 228, 66);
			tdvComp.backgroundGrassC = new Color(213, 94, 0);
			tdvComp.runwayC = new Color(230, 159, 0);
			tdvComp.runwayMarkingsC = Color.BLACK;
			tdvComp.clearwayC = new Color(0, 158, 115);
			tdvComp.stopwayC = new Color(204, 121, 167);
			tdvComp.stopwaySecondaryC = new Color(204, 121, 167);
			tdvComp.displacedThresholdC = Color.BLACK;
			tdvComp.todaC = Color.black;
			tdvComp.asdaC = Color.black;
			tdvComp.toraC = Color.black;
			tdvComp.ldaC = Color.black;
			tdvComp.leftRightArrowC = Color.black;
			tdvComp.rightLeftArrowC = Color.black;
			tdvComp.clearedWidthC = new Color(150, 164, 20);
			tdvComp.gradedWidthC = new Color(0, 114, 178);
			tdvComp.obstacleC = new Color(86, 180, 233);

			// Sets colours for right side view
			SideOnView sideR = (SideOnView) tool.sideRWrapper.getToWrap();
			sideR.backgroundBelowC = new Color(240, 228, 66);
			sideR.backgroundAboveC = new Color(255, 109, 182);
			sideR.backgroundCloudC = Color.white;
			sideR.backgroundGrassC = new Color(213, 94, 0);
			sideR.runwayC = Color.black;
			sideR.ldaC = Color.BLACK;
			sideR.toraC = Color.BLACK;
			sideR.todaC = Color.black;
			sideR.asdaC = Color.black;
			sideR.stripEndC = Color.black;
			sideR.resaC = Color.BLACK;
			sideR.hx50C = Color.BLACK;
			sideR.obsC = new Color(86, 180, 233);
			sideR.slopeC = Color.black;
			sideR.leftRightArrowC = Color.black;
			sideR.rightLeftArrowC = Color.black;
			
			// Sets colours for left side view
			SideOnView sideL = (SideOnView) tool.sideLWrapper.getToWrap();
			sideL.backgroundBelowC = new Color(240, 228, 66);
			sideL.backgroundAboveC = new Color(255, 109, 182);
			sideL.backgroundCloudC = Color.white;
			sideL.backgroundGrassC = new Color(213, 94, 0);
			sideL.runwayC = Color.black;
			sideL.ldaC = Color.BLACK;
			sideL.toraC = Color.BLACK;
			sideL.todaC = Color.black;
			sideL.asdaC = Color.black;
			sideL.stripEndC = Color.black;
			sideL.resaC = Color.BLACK;
			sideL.hx50C = Color.BLACK;
			sideL.obsC = new Color(86, 180, 233);
			sideL.slopeC = Color.black;
			sideL.leftRightArrowC = Color.black;
			sideL.rightLeftArrowC = Color.black;
		}
	}

	// Method for the GUI that allows users to pick their own colours
	protected JFrame customiseColours() {
		final JFrame f = new JFrame("Custom Colour Scheme");
		final JPanel customP = new JPanel();
		f.add(customP);

		customP.setLayout(new GridLayout(13, 3));

		// Labels for the possible things that can be changed on the top down
				// and side views
		JLabel background = new JLabel("Background");
		JLabel gradedarea = new JLabel("Graded Area");
		JLabel clearedarea = new JLabel("Cleared Area");
		JLabel clearway = new JLabel("Clearway");
		JLabel stopway = new JLabel("Stopway");
		JLabel runway = new JLabel("Runway");
		JLabel obstacle = new JLabel("Obstacle");
		JLabel grass = new JLabel("Grass Colour");
		JLabel markings = new JLabel("Runway Markings");
		JLabel labels = new JLabel("Arrows");
		JLabel sky = new JLabel("Sky (Side View)");
		JLabel clouds = new JLabel("Clouds (Side View)");

		// Labels to show the current colours for everything
		final JLabel backgroundC = new JLabel();
		backgroundC.setOpaque(true);
		backgroundC.setBackground(tool.topDownRunway.backgroundC);
		final JLabel gradedareaC = new JLabel();
		gradedareaC.setOpaque(true);
		gradedareaC.setBackground(tool.topDownRunway.gradedWidthC);
		final JLabel clearedareaC = new JLabel();
		clearedareaC.setOpaque(true);
		clearedareaC.setBackground(tool.topDownRunway.clearedWidthC);
		final JLabel clearwayC = new JLabel();
		clearwayC.setOpaque(true);
		clearwayC.setBackground(tool.topDownRunway.clearwayC);
		final JLabel stopwayC = new JLabel();
		stopwayC.setOpaque(true);
		stopwayC.setBackground(tool.topDownRunway.stopwayC);
		final JLabel runwayC = new JLabel();
		runwayC.setOpaque(true);
		runwayC.setBackground(tool.topDownRunway.runwayC);
		final JLabel obstacleC = new JLabel();
		obstacleC.setOpaque(true);
		obstacleC.setBackground(tool.topDownRunway.obstacleC);
		final JLabel grassC = new JLabel();
		grassC.setOpaque(true);
		grassC.setBackground(tool.topDownRunway.backgroundGrassC);
		final JLabel markingsC = new JLabel();
		markingsC.setOpaque(true);
		markingsC.setBackground(tool.topDownRunway.runwayMarkingsC);
		final JLabel labelsC = new JLabel();
		labelsC.setOpaque(true);
		labelsC.setBackground(tool.topDownRunway.toraC);
		final JLabel skyC = new JLabel();
		skyC.setOpaque(true);
		skyC.setBackground(tool.sideR.backgroundAboveC);
		final JLabel cloudsC = new JLabel();
		cloudsC.setOpaque(true);
		cloudsC.setBackground(tool.sideR.backgroundCloudC);


		// Button that allows user to pick a new colour for whatever item they
		// want to on the list
		JButton backgroundChoose = new JButton("Change");
		backgroundChoose.addActionListener(new ActionListener() {
			
			// Here the colours are formatted for all possible things
			public void actionPerformed(ActionEvent e) {
				
				// A new ColorChooser is opened for that particular colour,
				// allowing user to chose from a massive range of colours
				Color newColor = JColorChooser.showDialog(customP, "Choose Background Color",
						backgroundC.getBackground());
				if (newColor != null) {
					
					// Sets the preview label's colour to the selected colour
					backgroundC.setBackground(newColor);
				}
			}
		});

		// Allows change of the graded area colour
		JButton gradedareaChoose = new JButton("Change");
		gradedareaChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Graded Area Color",
						gradedareaC.getBackground());
				if (newColor != null) {
					gradedareaC.setBackground(newColor);
				}
			}
		});

		// Allows change of the cleared area colour
		JButton clearedareaChoose = new JButton("Change");
		clearedareaChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Cleared Area Color",
						clearedareaC.getBackground());
				if (newColor != null) {
					clearedareaC.setBackground(newColor);
				}
			}
		});

		// Allows change of the clearway colour
		JButton clearwayChoose = new JButton("Change");
		clearwayChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Clearway Color", clearwayC.getBackground());
				if (newColor != null) {
					clearwayC.setBackground(newColor);
				}
			}
		});

		// Allows change of the stopway colour
		JButton stopwayChoose = new JButton("Change");
		stopwayChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Stopway Color", stopwayC.getBackground());
				if (newColor != null) {
					stopwayC.setBackground(newColor);
				}
			}
		});


		// Allows change of the runway colour
		JButton runwayChoose = new JButton("Change");
		runwayChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Runway Color", runwayC.getBackground());
				if (newColor != null) {
					runwayC.setBackground(newColor);
				}
			}
		});

		// Allows change of the obstacle colour
		JButton obstacleChoose = new JButton("Change");
		obstacleChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Obstacle Color", obstacleC.getBackground());
				if (newColor != null) {
					obstacleC.setBackground(newColor);
				}
			}
		});

		// Allows change of the grass colour
		JButton grassChoose = new JButton("Change");
		obstacleChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Grass Color", grassC.getBackground());
				if (newColor != null) {
					grassC.setBackground(newColor);
				}
			}
		});

		// Allows change of the markings colour
		JButton markingsChoose = new JButton("Change");
		markingsChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Markings Color", markingsC.getBackground());
				if (newColor != null) {
					markingsC.setBackground(newColor);
				}
			}
		});

		// Allows change of the labels colour
		JButton labelsChoose = new JButton("Change");
		labelsChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Arrow Color", labelsC.getBackground());
				if (newColor != null) {
					labelsC.setBackground(newColor);
				}
			}
		});

		// Allows change of the sky colour
		JButton skyChoose = new JButton("Change");
		skyChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Sky Color", skyC.getBackground());
				if (newColor != null) {
					skyC.setBackground(newColor);
				}
			}
		});

		// Allows change of the clouds colour
		JButton cloudChoose = new JButton("Change");
		cloudChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(customP, "Choose Cloud Color", cloudsC.getBackground());
				if (newColor != null) {
					cloudsC.setBackground(newColor);
				}
			}
		});

		// Applies changes to colour selected above to all possible views
		JButton apply = new JButton("Apply");
		apply.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// Sets colours for top down view
				TopDownRunway tdv = (TopDownRunway) tool.topDownWrapper.getToWrap();
				tdv.backgroundC = backgroundC.getBackground();
				tdv.backgroundGrassC = grassC.getBackground();
				tdv.runwayC = runwayC.getBackground();
				tdv.runwayMarkingsC = markingsC.getBackground();
				tdv.clearwayC = clearwayC.getBackground();
				tdv.stopwayC = stopwayC.getBackground();
				tdv.stopwaySecondaryC = new Color(204, 121, 167);
				tdv.displacedThresholdC = labelsC.getBackground();
				tdv.todaC = labelsC.getBackground();
				tdv.asdaC = labelsC.getBackground();
				tdv.toraC = labelsC.getBackground();
				tdv.ldaC = labelsC.getBackground();
				tdv.leftRightArrowC = labelsC.getBackground();
				tdv.rightLeftArrowC = labelsC.getBackground();
				tdv.clearedWidthC = clearedareaC.getBackground();
				tdv.gradedWidthC = gradedareaC.getBackground();
				tdv.obstacleC = obstacleC.getBackground();

				// Sets colour for compass orientation view
				TopDownRunway tdvComp= (TopDownRunway) tool.compassWrapper.getToWrap();
				tdvComp.backgroundC = backgroundC.getBackground();
				tdvComp.backgroundGrassC = grassC.getBackground();
				tdvComp.runwayC = runwayC.getBackground();
				tdvComp.runwayMarkingsC = markingsC.getBackground();
				tdvComp.clearwayC = clearwayC.getBackground();
				tdvComp.stopwayC = stopwayC.getBackground();
				tdvComp.stopwaySecondaryC = new Color(204, 121, 167);
				tdvComp.displacedThresholdC = labelsC.getBackground();
				tdvComp.todaC = labelsC.getBackground();
				tdvComp.asdaC = labelsC.getBackground();
				tdvComp.toraC = labelsC.getBackground();
				tdvComp.ldaC = labelsC.getBackground();
				tdvComp.leftRightArrowC = labelsC.getBackground();
				tdvComp.rightLeftArrowC = labelsC.getBackground();
				tdvComp.clearedWidthC = clearedareaC.getBackground();
				tdvComp.gradedWidthC = gradedareaC.getBackground();
				tdvComp.obstacleC = obstacleC.getBackground();

				// Sets colours for right side view
				SideOnView sideR = (SideOnView) tool.sideRWrapper.getToWrap();
				sideR.backgroundBelowC = backgroundC.getBackground();
				sideR.backgroundAboveC = skyC.getBackground();
				sideR.backgroundCloudC = cloudsC.getBackground();
				sideR.backgroundGrassC = grassC.getBackground();
				sideR.runwayC = labelsC.getBackground();
				sideR.ldaC = labelsC.getBackground();
				sideR.toraC = labelsC.getBackground();
				sideR.todaC = labelsC.getBackground();
				sideR.asdaC = labelsC.getBackground();
				sideR.stripEndC = labelsC.getBackground();
				sideR.resaC = labelsC.getBackground();
				sideR.hx50C = labelsC.getBackground();
				sideR.obsC = obstacleC.getBackground();
				sideR.slopeC = labelsC.getBackground();
				sideR.leftRightArrowC = labelsC.getBackground();
				sideR.rightLeftArrowC = labelsC.getBackground();

				// Sets colours for left side view
				SideOnView sideL = (SideOnView) tool.sideLWrapper.getToWrap();
				sideL.backgroundBelowC = backgroundC.getBackground();
				sideL.backgroundAboveC = skyC.getBackground();
				sideL.backgroundCloudC = cloudsC.getBackground();
				sideL.backgroundGrassC = grassC.getBackground();
				sideL.runwayC = labelsC.getBackground();
				sideL.ldaC = labelsC.getBackground();
				sideL.toraC = labelsC.getBackground();
				sideL.todaC = labelsC.getBackground();
				sideL.asdaC = labelsC.getBackground();
				sideL.stripEndC = labelsC.getBackground();
				sideL.resaC = labelsC.getBackground();
				sideL.hx50C = labelsC.getBackground();
				sideL.obsC = obstacleC.getBackground();
				sideL.slopeC = labelsC.getBackground();
				sideL.leftRightArrowC = labelsC.getBackground();
				sideL.rightLeftArrowC = labelsC.getBackground();

				tool.topDownRunway.repaint();
				tool.compassOrientation.repaint();
				tool.sideR.repaint();
				tool.sideL.repaint();
				tool.sideR.repaint();
				tool.sideL.repaint();
				f.dispose();

			}

		});

		// Adding all the labels and button defined above to the GUI for the customisabel colour scheme
		customP.add(background);
		customP.add(backgroundC);
		customP.add(backgroundChoose);

		customP.add(gradedarea);
		customP.add(gradedareaC);
		customP.add(gradedareaChoose);

		customP.add(clearedarea);
		customP.add(clearedareaC);
		customP.add(clearedareaChoose);

		customP.add(clearway);
		customP.add(clearwayC);
		customP.add(clearwayChoose);

		customP.add(stopway);
		customP.add(stopwayC);
		customP.add(stopwayChoose);

		customP.add(runway);
		customP.add(runwayC);
		customP.add(runwayChoose);

		customP.add(obstacle);
		customP.add(obstacleC);
		customP.add(obstacleChoose);

		customP.add(grass);
		customP.add(grassC);
		customP.add(grassChoose);

		customP.add(markings);
		customP.add(markingsC);
		customP.add(markingsChoose);

		customP.add(labels);
		customP.add(labelsC);
		customP.add(labelsChoose);

		customP.add(sky);
		customP.add(skyC);
		customP.add(skyChoose);

		customP.add(clouds);
		customP.add(cloudsC);
		customP.add(cloudChoose);

		customP.add(new JLabel(""));
		customP.add(apply);

		f.setVisible(true);
		f.pack();
		f.setLocationRelativeTo(null);

		return f;
	}
}
