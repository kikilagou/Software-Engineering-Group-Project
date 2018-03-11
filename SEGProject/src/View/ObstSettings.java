package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import Controller.Controller;
import Model.Obstacle;
import Model.Runway;

// Class allowing user to specify parameters for the obstacle
class ObstSettings extends JFrame {

	private Runway runway;
	protected JTextArea notField;
	MainFrame tool;

	// Initialy no obstacles on the runway
	protected Boolean obstExistsOnRunway = false;

	public ObstSettings(Runway runway, JTextArea notField, Boolean obstExistsOnRunway, MainFrame tool) {
		super("Obstacle Settings");
		this.runway = runway;
		this.notField = notField;
		this.tool = tool;
		this.obstExistsOnRunway = obstExistsOnRunway;
	}

	// Method to set the look and feel of the GUI
	protected void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {

		}
	}

	protected void init() {

		// Sets the look and feel when the GUI is initialised
		setLookAndFeel();

		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

		// Adding the main content to the main panel
		SettingsPanel sp = new SettingsPanel(runway, notField, obstExistsOnRunway, this, tool);
		sp.init();
		main.add(sp);

		// Setting a border on the settings panel
		TitledBorder title;
		title = BorderFactory.createTitledBorder("");
		sp.setBorder(title);

		this.setContentPane(main);
		this.setResizable(false);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

// Class that creates the main content for the obstacle settings GUI
class SettingsPanel extends JPanel {

	// Frame to hold everything
	private JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
	private ObstSettings obstSettingsFrame;
	// Initaly no obstacle exists
	protected Boolean obstExistsOnRunway = false;
	MainFrame tool;

	private Runway runway;

	private File airbus1, airbus2, boeing1, boeing2;
	private JTextArea notField;

	protected SettingsPanel(Runway runway, JTextArea notField, Boolean obstExistsOnRunway, ObstSettings obstFrame, MainFrame tool) {
		this.notField = notField;
		this.tool = tool;
		this.obstSettingsFrame = obstFrame;
		this.runway = runway;
		this.obstExistsOnRunway = obstExistsOnRunway;
		airbus1 = new File("AirbusA320.txt");
		airbus2 = new File("AirbusA330.txt");
		boeing1 = new File("Boeing737NG.txt");
		boeing2 = new File("Boeing777-300ER.txt");
	}

	protected void init() {

		this.setPreferredSize(new Dimension(300, 600));
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(7, 7, 7, 7);
		int i = 0;

		JLabel heightL = new JLabel("Height (m)");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;
		add(heightL, c);

		i++;

		c.insets = new Insets(0, 7, 7, 7);

		// Allows user to specify height of the obstacle
		final JTextField heightField = new JTextField();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = i;
		add(heightField, c);

		i++;

		c.insets = new Insets(2, 7, 7, 7);

		JLabel widthL = new JLabel("Width (m)");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;
		add(widthL, c);

		i++;

		c.insets = new Insets(0, 7, 7, 7);

		// Allows user to specify width of the obstacle
		final JTextField widthField = new JTextField(15);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = i;
		add(widthField, c);

		i++;

		c.insets = new Insets(2, 7, 7, 7);

		JLabel depthL = new JLabel("Depth (m)");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;
		add(depthL, c);

		i++;

		c.insets = new Insets(0, 7, 7, 7);

		// Allows user to specify depth of the obstacle
		final JTextField depthField = new JTextField(15);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = i;
		add(depthField, c);

		i++;

		JLabel distanceThreshL = new JLabel("Distance to " + runway.toString() + " Threshold (m) **");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;
		add(distanceThreshL, c);

		i++;

		c.insets = new Insets(0, 7, 7, 7);

		// Allows user to specify a distance from the right displaced threshold
		// for the obstacle
		final JTextField distanceThreshLR = new JTextField(15);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = i;
		add(distanceThreshLR, c);

		i++;

		JLabel distanceThreshR = new JLabel("Distance to " + runway.getComplement().toString() + " Threshold (m) **");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;
		add(distanceThreshR, c);

		i++;

		c.insets = new Insets(0, 7, 7, 7);

		// Allows user to specify a distance from the left displaced threshold
		// for the obstacle
		final JTextField distanceThreshRL = new JTextField(15);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = i;
		add(distanceThreshRL, c);

		i++;

		c.insets = new Insets(2, 7, 7, 7);

		JLabel centerDist = new JLabel("Centerline Distance (m)");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;
		add(centerDist, c);

		i++;

		c.insets = new Insets(0, 7, 7, 7);

		// Allows user to specify vertical distance from the runway centerline
		// for the obstacle
		final JTextField centerDistField = new JTextField();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = i;
		add(centerDistField, c);

		i++;

		c.insets = new Insets(2, 7, 7, 7);

		JLabel desc = new JLabel("Description");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;
		add(desc, c);

		i++;

		c.insets = new Insets(0, 7, 7, 7);

		// Allows user to add some brief description for the obstacle
		final JTextArea descArea = new JTextArea();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = i;

		// This description area is able to be scrollable
		JScrollPane scroll = new JScrollPane(descArea);
		add(scroll, c);

		i++;

		c.insets = new Insets(2, 7, 7, 7);

		JLabel preDef = new JLabel("Select Predefined Obstacle");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;

		add(preDef, c);

		i++;

		c.insets = new Insets(0, 7, 7, 7);

		File[] predefObstacles = { airbus1, airbus2, boeing1, boeing2 };
		// Specifies some predefined obstacles as per our scenarios
		final JComboBox<File> obstacles = new JComboBox<File>(predefObstacles);
		obstacles.setSelectedItem(null);
		obstacles.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				File file = (File) obstacles.getSelectedItem();
				try {

					// Sets the obstacle to the one defined in the XML file
					Obstacle obst = Controller.getContr().readObstXml(file);

					// Sets the variables for the obstacle to those specified in
					// the XML file
					heightField.setText(String.valueOf(obst.getHeight()));
					widthField.setText(String.valueOf(obst.getWidth()));
					depthField.setText(String.valueOf(obst.getDepth()));
					descArea.setText(obst.getDescription());
					obstacles.setSelectedItem(obst);
				} catch (Exception e1) {
					System.err.println("Couldn't read obstacle xml file");
					e1.printStackTrace();
				}
			}

		});

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;
		add(obstacles, c);

		i++;

		c.insets = new Insets(0, 7, 7, 7);

		// Adds the obstacle specified to the runway
		JButton addButton = new JButton("Set Obstacle");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					// Validation checks for the parameters of the obstacle
					// The obstacle must be at least 1m x 1m x 1m cuboid
					if (Double.parseDouble(heightField.getText()) < 1
							|| Double.parseDouble(heightField.getText()) > 100) {

						// Sets incorrect values to red so user can easily
						// identify them
						heightField.setForeground(Color.RED);
						widthField.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Obstacle Height!\nThe obstacle must have height at least 1m\nand at most 100m");
					} else if (Double.parseDouble(widthField.getText()) < 1
							|| Double.parseDouble(widthField.getText()) > 100) {
						widthField.setForeground(Color.RED);
						JOptionPane.showMessageDialog(null,
								"Invalid Obstacle Width!\nThe obstacle must have width at least 1m\nand at most 100m");
					} else if (Double.parseDouble(depthField.getText()) < 1
							|| Double.parseDouble(depthField.getText()) > 100) {
						depthField.setForeground(Color.RED);
						widthField.setForeground(Color.BLACK);
						heightField.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Obstacle Depth!\nThe obstacle must have depth at least 1m\nand at most "
										+ runway.getRunwayLength() + "m");
					} else if (Double
							.parseDouble(distanceThreshLR.getText()) < (runway.getThresholdDisplacement() - 50)
							|| Double.parseDouble(distanceThreshLR.getText()) > (runway.getRunwayLength() + 50)) {
						distanceThreshLR.setForeground(Color.RED);
						widthField.setForeground(Color.BLACK);
						heightField.setForeground(Color.BLACK);
						depthField.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Threshold Distance.\nObstacle must have minimum -50m distance from the displaced threshold,\nand at most "
										+ (runway.getRunwayLength() + 50) + ("m from the displaced threshold!"));
					} else if (Double.parseDouble(
							distanceThreshRL.getText()) < (runway.getComplement().getThresholdDisplacement() - 50)
							|| Double
									.parseDouble(distanceThreshRL.getText()) > (runway.getComplement().getRunwayLength() + 50)) {
						distanceThreshRL.setForeground(Color.RED);
						distanceThreshLR.setForeground(Color.BLACK);
						widthField.setForeground(Color.BLACK);
						heightField.setForeground(Color.BLACK);
						depthField.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Threshold Distance.\nObstacle must have minimum -50m distance from the displaced threshold,\nand at most "
										+ (runway.getComplement().getRunwayLength() + 50) + ("m from the displaced threshold!"));
					} else if (Double.parseDouble(centerDistField.getText()) > runway.getRunwayWidth() / 2) {
						centerDistField.setForeground(Color.RED);
						widthField.setForeground(Color.BLACK);
						heightField.setForeground(Color.BLACK);
						depthField.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Centerline Distance.\nThe obstacle must be on the runway!");
					} else {
						centerDistField.setForeground(Color.BLACK);
						widthField.setForeground(Color.BLACK);
						heightField.setForeground(Color.BLACK);
						depthField.setForeground(Color.BLACK);

						// If all values match the contraints the obstacle is
						// created and added to the runway
						Obstacle obstacle = new Obstacle(Double.parseDouble(heightField.getText()),
								Double.parseDouble(widthField.getText()), Double.parseDouble(depthField.getText()),
								Double.parseDouble(distanceThreshLR.getText()),
								Double.parseDouble(centerDistField.getText()),
								String.valueOf(new Random().nextInt(10000)), descArea.getText());

						// Complement obstacle also created
						Obstacle obstacleComplement = new Obstacle(Double.parseDouble(heightField.getText()),
								Double.parseDouble(widthField.getText()), Double.parseDouble(depthField.getText()),
								Double.parseDouble(distanceThreshRL.getText()),
								Double.parseDouble(centerDistField.getText()),
								String.valueOf(new Random().nextInt(10000)), descArea.getText());

						// Adding to the runways
						runway.setObstacle(obstacle);
						runway.getComplement().setObstacle(obstacleComplement);
						
						tool.getCurrentDisplay().repaint();

						// Controller calculates new runway values based on
						// obstacle values and updates the collections and
						// values displays
						Controller.getContr().calculateRequest(runway);

						// When obstacle is added a new notification is created
						// on the notifications panel of the main GUI
						// It gives details of obstacle parameters
						String addedObstNotif = ">> New obstacle was added: " + " H: " + heightField.getText() + ", W: "
								+ widthField.getText() + ", D: " + depthField.getText();

						String current = notField.getText();

						// Appends the notification to existing notifications
						current = addedObstNotif + "\n" + current;
						notField.setText(current);

						// Sets the obstacle to exist on the runway
						obstExistsOnRunway = true;
						obstSettingsFrame.dispose();
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Error, Please check your inputs are valid");
				}
			}

		});
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;
		addButton.setPreferredSize(new Dimension(150, 30));
		add(addButton, c);

		i++;
		c.insets = new Insets(2, 7, 7, 7);

		JLabel nbLabel = new JLabel(
				"<html>**NB Distance from threshold must be from the closest point of the obstacle to the specified threshold</html>");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;

		add(nbLabel, c);
	}
}
