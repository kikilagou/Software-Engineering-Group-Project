package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import Controller.Controller;
import Model.Obstacle;
import Model.Runway;

// Class to display the tool
public class RunwayRedeclarationTool {

	protected MainFrame mf;
	protected MenuFrame menuframe;

	public RunwayRedeclarationTool(Runway runway, MenuFrame m) {

		this.menuframe = m;

		mf = new MainFrame(m.ap.input.getText() + " Runway Redeclaration Tool", runway, menuframe);
	}

	public void init() {
		mf.init();

		mf.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(mf, "Are you sure you wish to close this window?", "",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					
					mf.setVisible(false);
					mf.menuframe.setVisible(true);
				} 
			}
		});
	}
}

// Main class combining the components of the runway redeclaration tool
class MainFrame extends JFrame implements UpdateInterface {

	private ViewWrapper currentDisplay;

	public ViewWrapper getCurrentDisplay() {
		return currentDisplay;
	}

	TopDownRunway topDownRunway;
	TopDownRunway compassOrientation;
	SideOnView sideL, sideR;
	Runway runway;
	CalculationsDisplay calcsDisp;
	ValueDisplay1 runwayValsDisp;
	ValueDisplay2 runwayCompValsDisp;
	Runway runwayC;
	JPanel values;
	JPanel main;
	JPanel view;
	MenuFrame menuframe;
	ViewWrapper compassWrapper;
	ViewWrapper topDownWrapper;
	ViewWrapper sideLWrapper;
	ViewWrapper sideRWrapper;

	protected MainFrame(String title, Runway runway, MenuFrame m) {
		super(title);
		this.runway = runway;
		runwayC = runway.getComplement();
		runwayCompValsDisp = new ValueDisplay2(runway);
		runwayValsDisp = new ValueDisplay1(runway);
		calcsDisp = new CalculationsDisplay(runway);

		this.menuframe = m;
	}

	protected void init() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Sets the look and feel of the GUI when it is initialised
		setLookandFeel();

		// tabbedPane = new JTabbedPane();

		// main panel to hold all the components
		main = new JPanel();

		// In the centre will be the main view, the right panel hold
		// calculations displays, south panel holds controls
		main.setLayout(new BorderLayout());

		MenuBar mb = new MenuBar(this);
		mb.init();
		// main.add(mb, BorderLayout.NORTH);
		this.setJMenuBar(mb.menuBar);

		// Card layout allows switching between all the possible views for the
		// runway
		final CardLayout cl = new CardLayout();

		view = new JPanel(cl);
		view.setLayout(cl);
		view.setPreferredSize(new Dimension(1300, 700));
		main.add(view, BorderLayout.CENTER);

		// Creating a new top down runway view for the specified runway
		topDownRunway = new TopDownRunway(runway.getRunwayWidth(),
				runway.getRunwayLength() + 2 * runway.getStopwayLength(), runway.getClearwayWidth(),
				runway.getClearwayLength(), runway.getStopwayLength(), runway.toString(), runwayC.toString(),
				runway.getThresholdDisplacement(), runway.getComplement().getThresholdDisplacement(), false);
		topDownRunway.setToda(runway.getRunwayLength() - runway.getToda(),
				runway.getComplement().getRunwayLength() - runway.getComplement().getToda());
		topDownRunway.setAsda(runway.getRunwayLength() - runway.getAsda(),
				runway.getComplement().getRunwayLength() - runway.getComplement().getAsda());
		topDownRunway.setTora(runway.getRunwayLength() - runway.getTora(),
				runway.getComplement().getRunwayLength() - runway.getComplement().getTora());
		topDownRunway.setLda(runway.getRunwayLength() - runway.getLda(),
				runway.getComplement().getRunwayLength() - runway.getComplement().getLda());

		// Setting values for top down runway
		topDownRunway.setToda(0d, 0d);
		topDownRunway.setAsda(0d, 0d);
		topDownRunway.setTora(0d, 0d);
		topDownRunway.setLda(0d, 0d);
		topDownRunway.setClearedGraded(runway.getEndToFullCleared(), runway.getClearedArea(), runway.getGradedArea());

		// Adding the top down runway as a card on the view panel
		topDownWrapper = new ViewWrapper(topDownRunway);
		currentDisplay = topDownWrapper;
		view.add(topDownWrapper, "Top Down View");

		// Creating a new compass orientation view runway view for the specified
		// runway
		compassOrientation = new TopDownRunway(runway.getRunwayWidth(),
				runway.getRunwayLength() + 2 * runway.getStopwayLength(), runway.getClearwayWidth(),
				runway.getClearwayLength(), runway.getStopwayLength(), runway.toString(), runwayC.toString(),
				runway.getThresholdDisplacement(), runway.getComplement().getThresholdDisplacement(), true);
		compassOrientation.setToda(0d, 0d);
		compassOrientation.setAsda(0d, 0d);
		compassOrientation.setTora(0d, 0d);
		compassOrientation.setLda(0d + runway.getThresholdDisplacement(),
				0d + runway.getComplement().getThresholdDisplacement());
		compassOrientation.setClearedGraded(runway.getEndToFullCleared(), runway.getClearedArea(),
				runway.getGradedArea());
		compassOrientation.setOrientation(runway.getHeading() * 10);

		// Adding the compass orientation view runway as a card on the view
		// panel
		compassWrapper = new ViewWrapper(compassOrientation);
		view.add(compassWrapper, "Compass Orientation View");

		// Panel to hold the calcualations display
		final JPanel rightSidePanel = new JPanel();

		final CardLayout clcalcs = new CardLayout();
		rightSidePanel.setLayout(clcalcs);
		rightSidePanel.add(calcsDisp, "calcs");
		clcalcs.show(rightSidePanel, "calcs");

		// Adding the calculations display to the main GUI
		main.add(rightSidePanel, BorderLayout.EAST);
		calcsDisp.setPreferredSize(new Dimension(250, 400));
		// rightSidePanel.setLayout((new BoxLayout(rightSidePanel,
		// BoxLayout.Y_AXIS)));

		// Panel to hold the controls
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		main.add(southPanel, BorderLayout.SOUTH);

		// Displays the calculations
		calcsDisp.init();

		// Sections for the values display
		values = new JPanel();
		values.setLayout(new GridLayout(1, 2));
		southPanel.add(values, BorderLayout.EAST);

		// Panel to hold the zoom and the colour scheme panels
		JPanel leftSideSouth = new JPanel();
		leftSideSouth.setLayout(new GridLayout(1, 2));

		// Displaying the values for the runway
		runwayValsDisp.init();
		values.add(runwayValsDisp);

		// Displaying the values for the runway complement
		runwayCompValsDisp.init();
		values.add(runwayCompValsDisp);

		// Adding the controls panel to the main GUI
		final ControlsPanel cp = new ControlsPanel(runway, this);
		cp.init();
		southPanel.add(cp, BorderLayout.WEST);

		// Radio button for the top down view
		cp.getTopDown().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// When selected the top down view is displayed
				if (e.getSource() == cp.getTopDown()) {
					cl.show(view, "Top Down View");		
					
					currentDisplay = topDownWrapper;
					topDownWrapper.repaint();
				}

			}
		});

		// Creating new left side on view display
		sideL = new SideOnView((int) runway.getRunwayLength(),
				String.valueOf(runway.getHeading()) + runway.getDirectionChar(), true);

		// Adding the side on view as a card to the view panel
		sideLWrapper = new ViewWrapper(sideL);
		view.add(sideLWrapper, "Left Side View");

		// Radio button listener to display the side on view from the left when
		// selected
		cp.getSide().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Displays left side view
				if (e.getSource() == cp.getSide()) {
					cl.show(view, "Left Side View");
					currentDisplay = sideLWrapper;
					sideLWrapper.repaint();
				}

			}
		});

		// Creating new right side on view display
		sideR = new SideOnView((int) runway.getComplement().getRunwayLength(),
				String.valueOf(runway.getComplement().getHeading()) + runway.getComplement().getDirectionChar(), false);

		// Adding the right side view as a card to the view panel
		sideRWrapper = new ViewWrapper(sideR);
		view.add(sideRWrapper, "Right Side View");

		// Radio button action listener to display the side on view
		cp.getRightSide().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// When clicked displays the right side
				if (e.getSource() == cp.getRightSide()) {
					cl.show(view, "Right Side View");
					currentDisplay = sideRWrapper;

					sideRWrapper.repaint();
				}

			}
		});

		// Action listener to display the compass orientation
		cp.getCompassOrientation().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// When selected compass orientation is shown on view panel
				if (e.getSource() == cp.getCompassOrientation()) {
					cl.show(view, "Compass Orientation View");
					currentDisplay = compassWrapper;


					compassWrapper.repaint();
				}
			}
		});

		ButtonGroup group = new ButtonGroup();
		group.add(cp.getTopDown());
		group.add(cp.getSide());
		group.add(cp.getRightSide());
		group.add(cp.getCompassOrientation());

		ObstacleControls obstCont = new ObstacleControls(runway, this);
		obstCont.init();
		southPanel.add(obstCont, BorderLayout.CENTER);

		/**
		 * JPanel controls = new JPanel(); controls.setLayout(new
		 * BorderLayout()); controls.add(cp.infoPanel(), BorderLayout.NORTH);
		 * controls.add(cp.presetObstPanel() , BorderLayout.SOUTH);
		 * controls.add(cp.customObstacle(), BorderLayout.CENTER);
		 * southPanel.add(controls, BorderLayout.CENTER);
		 **/

		// Adding borders to all panels to make them more clear and usable
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Calculations");
		calcsDisp.setBorder(title);

		TitledBorder title1;
		title1 = BorderFactory.createTitledBorder("Top Down View");
		topDownRunway.setBorder(title1);

		TitledBorder title15;
		title15 = BorderFactory.createTitledBorder("Compass View");
		compassOrientation.setBorder(title15);

		TitledBorder title2;
		title2 = BorderFactory.createTitledBorder("" + runway.getHeading() + runway.getDirectionChar());
		runwayValsDisp.setBorder(title2);

		TitledBorder title3;
		title3 = BorderFactory.createTitledBorder("" + runwayC.getHeading() + runwayC.getDirectionChar());
		runwayCompValsDisp.setBorder(title3);

		TitledBorder title4;
		title4 = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Controls");
		cp.setBorder(title4);

		TitledBorder title5;
		title5 = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Obstacle");
		obstCont.setBorder(title5);

		TitledBorder title6;
		title6 = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Values");
		values.setBorder(title6);

		this.setContentPane(main);
		this.setResizable(true);
		this.setMinimumSize(new Dimension(1300, 700));
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);

		// Setting values for the left side for the specified runway
		sideL.setLda(runway.getLda());
		sideL.setToda(runway.getToda());
		sideL.setTora(runway.getTora());
		sideL.setAsda(runway.getAsda());
		sideL.setStripEnd(null);
		sideL.setResa(null);
		sideL.setHx50(null);
		sideL.setObstacle(null, null, null, true);

		// Getting the complement runway
		Runway compl = runway.getComplement();

		// Setting values for the right side of the runway
		sideR.setLda(compl.getLda());
		sideR.setToda(compl.getToda());
		sideR.setTora(compl.getTora());
		sideR.setAsda(compl.getAsda());
		sideR.setStripEnd(null);
		sideR.setResa(null);
		sideR.setHx50(null);
		sideR.setObstacle(null, null, null, false);

		// Calculating, updating values and displaying them
		Controller.getContr().registerUpdateInterest(runway, this);
		Controller.getContr().registerUpdateInterest(runway, runwayValsDisp);
		Controller.getContr().registerUpdateInterest(runway, runwayCompValsDisp);
		Controller.getContr().registerUpdateInterest(runway, calcsDisp);

		final MainFrame current = this;

		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				Controller.getContr().unregisterUpdateInterface(runway, current);
				Controller.getContr().unregisterUpdateInterface(runway, runwayValsDisp);
				Controller.getContr().unregisterUpdateInterface(runway, runwayCompValsDisp);
				Controller.getContr().unregisterUpdateInterface(runway, calcsDisp);
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

		});

		// Updating everything in the GUI, such as values and calculations
		// displays.
		repaint();
	}

	// Updating the labels and arrows on the runway view after and obstacle is
	// added
	@Override
	public void updateGui(HashMap<String, Double> values) {

		// Will also be doing this for the runway complement
		Runway rCompl = runway.getComplement();

		// Gets the obstacle position on the runway
		Double obsPos = values.get(Controller.OBS_POS);

		boolean obsLeft = true;
		if (obsPos == null) {
			obsLeft = true;
		} else {
			obsLeft = obsPos < 0;
		}

		// Getting new values for the runway labels
		Double ldaL = values.get(Controller.LDAl);
		Double toraL = values.get(Controller.TORAl);
		Double todaL = values.get(Controller.TODAl);
		Double asdaL = values.get(Controller.ASDAl);
		Double hx50 = values.get(Controller.hx50);
		Double ldaR = values.get(Controller.LDAr);
		Double toraR = values.get(Controller.TORAr);
		Double todaR = values.get(Controller.TODAr);
		Double asdaR = values.get(Controller.ASDAr);
		
		if(ldaL != null && toraL != null && todaL != null && asdaL != null)
		{
			sideL.setText("LDA: " + String.valueOf(ldaL) + "m", 
					"TORA: " + String.valueOf(toraL) + "m", 
					"TODA: " + String.valueOf(todaL) + "m", 
					"ASDA: " + String.valueOf(asdaL) + "m", 
					"Displacement");
			compassOrientation.setText("LDA: " + String.valueOf(ldaR) + "m", 
					"TORA: " + String.valueOf(toraR) + "m", 
					"TODA: " + String.valueOf(todaR) + "m", 
					"ASDA: " + String.valueOf(asdaR) + "m", 
					"Displacement");
			topDownRunway.setText("LDA: " + String.valueOf(ldaL) + "m", 
					"TORA: " + String.valueOf(toraL) + "m", 
					"TODA: " + String.valueOf(todaL) + "m", 
					"ASDA: " + String.valueOf(asdaL) + "m", 
					"Displacement");
		}
		else
		{
			sideL.setText(null,null,null,null,null);
			topDownRunway.setText(null,null,null,null,null);
			compassOrientation.setText(null,null,null,null,null);
		}
		if(ldaR != null && toraR != null && todaR != null && asdaR != null)
		{
			sideR.setText("LDA: " + String.valueOf(ldaR) + "m", 
					"TORA: " + String.valueOf(toraR) + "m", 
					"TODA: " + String.valueOf(todaR) + "m", 
					"ASDA: " + String.valueOf(asdaR) + "m", 
					"Displacement");
			compassOrientation.setTextComp("LDA: " + String.valueOf(ldaR) + "m", 
					"TORA: " + String.valueOf(toraR) + "m", 
					"TODA: " + String.valueOf(todaR) + "m", 
					"ASDA: " + String.valueOf(asdaR) + "m", 
					"Displacement");
			topDownRunway.setTextComp("LDA: " + String.valueOf(ldaL) + "m", 
					"TORA: " + String.valueOf(toraL) + "m", 
					"TODA: " + String.valueOf(todaL) + "m", 
					"ASDA: " + String.valueOf(asdaL) + "m", 
					"Displacement");
		}
		else
		{
			sideR.setText(null,null,null,null,null);
			topDownRunway.setText(null,null,null,null,null);
			compassOrientation.setText(null,null,null,null,null);
		}

		// Update it to this value and length specified above
		sideL.setLda(ldaL == null ? runway.getLda() : ldaL);
		sideR.setLda(ldaR == null ? rCompl.getLda() : ldaR);
		
		topDownRunway.setLda(ldaL == null ? runway.getLda() : ldaL, 
				ldaR == null ? rCompl.getLda() : ldaR);
		compassOrientation.setLda(ldaL == null ? runway.getLda() : ldaL, 
				ldaR == null ? rCompl.getLda() : ldaR);

		sideL.setTora(toraL == null ? runway.getTora() : toraL);
		sideR.setTora(toraR == null ? rCompl.getTora() : toraR);
		
		// Set the tora values for both top down displays
		topDownRunway.setTora(toraL == null ? runway.getTora() : toraL, 
				toraR == null ? rCompl.getTora() : toraR);
		compassOrientation.setTora(toraL == null ? runway.getTora() : toraL, 
				toraR == null ? rCompl.getTora() : toraR);

		sideL.setToda(todaL == null ? runway.getToda() : todaL);
		sideR.setToda(todaR == null ? rCompl.getToda() : todaR);
		
		topDownRunway.setToda(todaL == null ? runway.getToda() : todaL, 
				todaR == null ? rCompl.getToda() : todaR);
		compassOrientation.setToda(todaL == null ? runway.getToda() : todaL, 
				todaR == null ? rCompl.getToda() : todaR);

		sideL.setAsda(asdaL == null ? runway.getAsda() : asdaL);
		sideR.setAsda(asdaR == null ? rCompl.getAsda() : asdaR);
		
		// Set the asda for the top down views
		topDownRunway.setAsda(asdaL == null ? runway.getAsda() : asdaL, 
				asdaR == null ? rCompl.getAsda() : asdaR);
		compassOrientation.setAsda(asdaL == null ? runway.getAsda() : asdaL, 
				asdaR == null ? rCompl.getAsda() : asdaR);

		// Set the scaled labels for the side views
		sideL.setHx50(hx50);
		sideR.setHx50(hx50);

		// Setting values for the side views
		sideL.setStripEnd(runway.getStripEnd());
		sideR.setStripEnd(rCompl.getStripEnd());
		sideL.setResa(runway.getResa());
		sideR.setResa(rCompl.getResa());

		// Gets the runway obstacle is one exists
		Obstacle obs = runway.getObstacle();

		// If obstacle present of runway
		if (obs != null) {

			// Display is on the top down view
			topDownRunway.setObstacle(obs.getWidth(), obs.getDepth(), obs.getDistanceFromThresh(),
					obs.getCenterlineDist(), obsLeft);
			// Display it on the compass orientation view
			compassOrientation.setObstacle(obs.getWidth(), obs.getDepth(), obs.getDistanceFromThresh(),
					obs.getCenterlineDist(), obsLeft);
			// Display it on the left side runway view
			sideL.setObstacle(obs.getDistanceFromThresh(), obs.getDepth(), obs.getHeight(), obsLeft);

			// If a runway complement has been defined
			if (runway.getComplement() != null) {
				// Set the obstacle for the right side view
				Obstacle compl = runway.getComplement().getObstacle();
				sideR.setObstacle(compl.getDistanceFromThresh(), obs.getDepth(), obs.getHeight(), !obsLeft);
			}
		} else {
			// Otherwise no obstacle exists on the runway
			topDownRunway.setObstacle(null, null, null, null, true);
			compassOrientation.setObstacle(null, null, null, null, true);
			sideL.setObstacle(null, null, null, true);
			sideR.setObstacle(null, null, null, false);
		}

		// Repaint all the views to display updated labels and arrows
		topDownRunway.repaint();
		topDownWrapper.invalidate();
		compassOrientation.repaint();
		compassWrapper.invalidate();
		sideL.repaint();
		sideLWrapper.invalidate();
		sideR.repaint();
		sideRWrapper.invalidate();
	}

	// Sets the look and feel of the GUI
	protected void setLookandFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
	}

}