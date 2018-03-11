	package View;

import Model.Runway;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

import javax.swing.*;

import Controller.Controller;

// Class to create a GUI for the runway settings allowing user to specify new runways
class RunwaySettings extends JFrame {

	private SettingsPanel1 sp;
	private JScrollPane scroller;
	


	public RunwaySettings(String title, DefaultComboBoxModel<Runway> list) {
		super(title);
		sp = new SettingsPanel1(list, this);

		// This GUI is resizable so that it can scale better for various
		// displays
		scroller = new JScrollPane(sp);

		// This ensures it is only vertically scrollable not horizontally
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
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

		sp.init();

		this.setContentPane(scroller);
		this.setResizable(true);
		this.setVisible(true);
		this.setMinimumSize(new Dimension(500, 500));
		this.pack();
		this.setLocationRelativeTo(null);
	}
}

// Main panel to hold the content of the runway settings
class SettingsPanel1 extends JPanel {

	DefaultComboBoxModel<Runway> list;
	RunwaySettings runwaySetPanel;

	public SettingsPanel1(DefaultComboBoxModel<Runway> list2, RunwaySettings runwaySetPanel) {
		this.list = list2;
		this.runwaySetPanel = runwaySetPanel;
	}

	protected void init() {

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(10, 10, 10, 10);
		int i = 0;

		// Denotes runway direction 1
		JLabel direction1 = new JLabel("1");
		c.gridx = 1;
		c.gridy = i;
		add(direction1, c);

		// Denotes the complement runway
		JLabel direction2 = new JLabel("2");
		c.gridx = 2;
		c.gridy = i;
		add(direction2, c);

		i++;

		JLabel direction = new JLabel("Directionality (e.g 09L)");
		c.gridx = 0;
		c.gridy = i;
		c.anchor = GridBagConstraints.WEST;
		add(direction, c);

		// Input for the directionality of the runway
		final JTextField dir1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(dir1, c);

		// Directionality for the complement runway
		final JTextField dir2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(dir2, c);

		i++;

		JLabel runLength = new JLabel("Runway Length (m)");
		c.gridx = 0;
		c.gridy = i;
		c.anchor = GridBagConstraints.WEST;
		add(runLength, c);

		// Input runway length
		final JTextField runLInput1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(runLInput1, c);

		// Input runway complement length
		final JTextField runLInput2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(runLInput2, c);

		i++;

		JLabel runWidth1 = new JLabel("Runway Width (m)");
		c.gridx = 0;
		c.gridy = i;
		c.anchor = GridBagConstraints.WEST;
		add(runWidth1, c);

		// Input runway width
		final JTextField runWInput1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(runWInput1, c);

		// Input runway complement length
		final JTextField runWInput2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(runWInput2, c);

		i++;

		JLabel tora = new JLabel("TORA (m)");
		c.gridx = 0;
		c.gridy = i;
		c.anchor = GridBagConstraints.WEST;
		add(tora, c);

		// Input runway tora
		final JTextField toraInput1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(toraInput1, c);

		// Input runway complement tora
		final JTextField toraInput2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(toraInput2, c);

		i++;

		JLabel toda = new JLabel("TODA (m)");
		c.gridx = 0;
		c.gridy = i;
		add(toda, c);

		// Input runway toda
		final JTextField todaInput1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		add(todaInput1, c);

		// Input runway complement toda
		final JTextField todaInput2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		add(todaInput2, c);

		i++;

		JLabel asda = new JLabel("ASDA (m)");
		c.gridx = 0;
		c.gridy = i;
		add(asda, c);

		// Input runway asda
		final JTextField asdaInput1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		add(asdaInput1, c);

		// Input runway complement asda
		final JTextField asdaInput2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		add(asdaInput2, c);

		i++;

		JLabel lda = new JLabel("LDA (m)");
		c.gridx = 0;
		c.gridy = i;
		add(lda, c);

		// Input runway lda
		final JTextField ldaInput1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		add(ldaInput1, c);

		// Input runway complement lda
		final JTextField ldaInput2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		add(ldaInput2, c);

		i++;

		JLabel displacementThreshold = new JLabel("<html>Displacement<br>Threshold (m)</html>");
		c.gridx = 0;
		c.gridy = i;
		add(displacementThreshold, c);

		// Input runway displaced threshold
		final JTextField dtInput1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		add(dtInput1, c);

		// Input runway complement displaced threshold
		final JTextField dtInput2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		add(dtInput2, c);

		i++;

		JLabel clearwayWidth = new JLabel("Clearway Width (m)");
		c.gridx = 0;
		c.gridy = i;
		add(clearwayWidth, c);

		// Input runway clearway width
		final JTextField clrwyWdInput1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		add(clrwyWdInput1, c);

		// Input runway complement clearway width
		final JTextField clrwyWdInput2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		add(clrwyWdInput2, c);

		i++;

		JLabel clearwayLength = new JLabel("Clearway Length (m)");
		c.gridx = 0;
		c.gridy = i;
		add(clearwayLength, c);

		// Input runway clearway length
		final JTextField clrwyLnInput1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		add(clrwyLnInput1, c);

		// Input runway complement clearway width
		final JTextField clrwyLnInput2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		add(clrwyLnInput2, c);
		i++;

		JLabel stopway = new JLabel("Stopway Length (m)");
		c.gridx = 0;
		c.gridy = i;
		add(stopway, c);

		// Input runway stopway length
		final JTextField stpwyInput1 = new JTextField();
		c.gridx = 1;
		c.gridy = i;
		add(stpwyInput1, c);

		// Input runway complement stopway length
		final JTextField stpwyInput2 = new JTextField();
		c.gridx = 2;
		c.gridy = i;
		add(stpwyInput2, c);

		i++;

		// Adds the runway to the list of runways on the menu, created a new
		// runway in the database
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {

					// Gets the name of the runway
					String name1 = dir1.getText().trim();

					// Gets the name of the runway complement
					String name2 = dir2.getText().trim();

					// Gets the char for the directionality for the runway
					char chara = 0;

					// Gets the char for the directionality for the runway
					// complement
					char charb = 0;

					// If R or L has been speficied it splits it up from the
					// orientation
					if (name1.length() > 2 | name2.length() > 2) {
						chara = name1.charAt(2);
						charb = name2.charAt(2);
						name1 = name1.substring(0, 2);
						name2 = name2.substring(0, 2);
					}

					if ((Double.parseDouble(stpwyInput1.getText().trim()) > Double
							.parseDouble(clrwyLnInput1.getText().trim()))
							|| (Double.parseDouble(stpwyInput2.getText().trim()) > Double
									.parseDouble(clrwyLnInput2.getText().trim()))) {
						throw new Exception();
					}

					/**
					 * if(Integer.parseInt(name1) > 37 ||
					 * Integer.parseInt(name1) < 0 || chara != 'L' || chara !=
					 * 'R' || chara != Character.MIN_VALUE) {
					 * dir1.setForeground(Color.RED);
					 * JOptionPane.showMessageDialog(null, "Invalid
					 * Direction!\nThe direction must be between 0 to 36\nand
					 * must be either right 'R' or left 'L'."); } else
					 * if(Integer.parseInt(name2) > 37 ||
					 * Integer.parseInt(name2) < 0 || charb != 'L' || charb !=
					 * 'R' || charb != Character.MIN_VALUE) {
					 * dir2.setForeground(Color.RED);
					 * dir1.setForeground(Color.BLACK);
					 * JOptionPane.showMessageDialog(null, "Invalid
					 * Direction!\nThe direction must be between 0 to 36\nand
					 * must be either right 'R' or left 'L'."); } else
					 **/

					// Checking all the values and validation they are within
					// the correct parameters. If a values is incorrect it is
					// highlighted in red and an error message with helpful
					// guidance to correct the value appears for the user. Once
					// a value within the parameters is input the text returns
					// to black and the next value is checked. This is done
					// until all the values are checked to be correct. Once they
					// are validated a runway can be created and added.

					// "Invalid Direction!\nThe direction must be between 0
					// to 36\nand must be either right 'R' or left 'L'.");
					// } else
					if (Integer.parseInt(runLInput1.getText().trim()) > 6000
							|| Integer.parseInt(runLInput1.getText().trim()) < 400) {
						runLInput1.setForeground(Color.RED);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Runway Length!\nRunway length must be at least 400m\nand at most 6000m.");
					} else if (Integer.parseInt(runLInput2.getText().trim()) > 6000
							|| Integer.parseInt(runLInput2.getText().trim()) < 400) {
						runLInput2.setForeground(Color.RED);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Runway Length!\nRunway length must be at least 400m\nand at most 6000m.");
					} else if (Integer.parseInt(runWInput1.getText().trim()) > 100
							|| Integer.parseInt(runWInput1.getText().trim()) < 30) {
						runWInput1.setForeground(Color.RED);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Runway Width!\nRunway width must be at least 30m\nand at most 100m.");
					} else if (Integer.parseInt(runWInput2.getText().trim()) > 100
							|| Integer.parseInt(runWInput2.getText().trim()) < 30) {
						runWInput2.setForeground(Color.RED);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Runway Width!\nRunway width must be at least 30m\nand at most 100m.");
					} else if (Integer.parseInt(toraInput1.getText().trim()) > Integer
							.parseInt(runLInput1.getText().trim())
							|| Integer.parseInt(
									toraInput1.getText().trim()) < Integer.parseInt(runLInput1.getText().trim()) / 2) {
						toraInput1.setForeground(Color.RED);
						runWInput2.setForeground(Color.BLACK);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Tora!\nTora must be at least "
										+ Integer.parseInt(runLInput1.getText().trim()) / 2 + "\nand at most "
										+ Integer.parseInt(runLInput1.getText().trim()));
					} else if (Integer.parseInt(toraInput2.getText().trim()) > Integer
							.parseInt(runLInput2.getText().trim())
							|| Integer.parseInt(
									toraInput2.getText().trim()) < Integer.parseInt(runLInput2.getText().trim()) / 2) {
						toraInput2.setForeground(Color.RED);
						toraInput1.setForeground(Color.BLACK);
						runWInput2.setForeground(Color.BLACK);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Tora!\nTora must be at least "
										+ Integer.parseInt(runLInput2.getText().trim()) / 2 + "\nand at most "
										+ Integer.parseInt(runLInput2.getText().trim()));
					} else if (Integer
							.parseInt(todaInput1.getText().trim()) < Integer.parseInt(runLInput1.getText().trim()) / 2
							|| (Integer.parseInt(toraInput1.getText().trim())
									+ Integer.parseInt(clrwyLnInput1.getText().trim())) != Integer
											.parseInt(todaInput1.getText().trim())) {
						todaInput1.setForeground(Color.RED);
						toraInput2.setForeground(Color.BLACK);
						toraInput1.setForeground(Color.BLACK);
						runWInput2.setForeground(Color.BLACK);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Toda!\nTODA must be equal to : TORA + Clearway Length!\nToda must be at least "
										+ Integer.parseInt(runLInput1.getText().trim()) / 2 + "\nand at most "
										+ (Integer.parseInt(toraInput1.getText().trim())
												+ Integer.parseInt(clrwyLnInput1.getText().trim())));
					} else if (Integer
							.parseInt(todaInput2.getText().trim()) < Integer.parseInt(runLInput2.getText().trim()) / 2
							|| (Integer.parseInt(toraInput2.getText().trim())
									+ Integer.parseInt(clrwyLnInput2.getText().trim())) != Integer
											.parseInt(todaInput2.getText().trim())) {
						todaInput2.setForeground(Color.RED);
						todaInput1.setForeground(Color.BLACK);
						toraInput2.setForeground(Color.BLACK);
						toraInput1.setForeground(Color.BLACK);
						runWInput2.setForeground(Color.BLACK);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Toda!\nTODA must be equal to : TORA + Clearway Length!\nToda must be at least "
										+ Integer.parseInt(runLInput2.getText().trim()) / 2 + "\nand at most "
										+ (Integer.parseInt(toraInput2.getText().trim())
												+ Integer.parseInt(clrwyLnInput2.getText().trim())));
					} else if (Integer.parseInt(asdaInput1.getText().trim()) > Integer
							.parseInt(runLInput1.getText().trim())
							|| Integer.parseInt(
									asdaInput1.getText().trim()) < Integer.parseInt(runLInput1.getText().trim()) / 2
							|| (Integer.parseInt(toraInput1.getText().trim())
									+ Integer.parseInt(stpwyInput1.getText().trim())) != Integer
											.parseInt(asdaInput1.getText().trim())) {
						asdaInput1.setForeground(Color.RED);
						todaInput2.setForeground(Color.BLACK);
						todaInput1.setForeground(Color.BLACK);
						toraInput2.setForeground(Color.BLACK);
						toraInput1.setForeground(Color.BLACK);
						runWInput2.setForeground(Color.BLACK);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Asda!\nASDA must be equal to : TORA + Stopway Length!\nAsda must be at least "
										+ Integer.parseInt(runLInput1.getText().trim()) / 2 + "\nand at most "
										+ Integer.parseInt(runLInput1.getText().trim()));
					} else if (Integer.parseInt(asdaInput2.getText().trim()) > Integer
							.parseInt(runLInput2.getText().trim())
							|| Integer.parseInt(
									asdaInput2.getText().trim()) < Integer.parseInt(runLInput2.getText().trim()) / 2
							|| (Integer.parseInt(toraInput2.getText().trim())
									+ Integer.parseInt(stpwyInput2.getText().trim())) != Integer
											.parseInt(asdaInput2.getText().trim())) {
						asdaInput2.setForeground(Color.RED);
						asdaInput1.setForeground(Color.BLACK);
						todaInput2.setForeground(Color.BLACK);
						todaInput1.setForeground(Color.BLACK);
						toraInput2.setForeground(Color.BLACK);
						toraInput1.setForeground(Color.BLACK);
						runWInput2.setForeground(Color.BLACK);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Asda!\nASDA must be equal to : TORA + Stopway Length!\nAsda must be at least "
										+ Integer.parseInt(runLInput2.getText().trim()) / 2 + "\nand at most "
										+ Integer.parseInt(runLInput2.getText().trim()));
					} else if (Integer.parseInt(ldaInput1.getText().trim()) > Integer
							.parseInt(runLInput1.getText().trim())
							|| Integer.parseInt(
									ldaInput1.getText().trim()) < Integer.parseInt(runLInput1.getText().trim()) / 2) {
						ldaInput1.setForeground(Color.RED);
						asdaInput2.setForeground(Color.BLACK);
						asdaInput1.setForeground(Color.BLACK);
						todaInput2.setForeground(Color.BLACK);
						todaInput1.setForeground(Color.BLACK);
						toraInput2.setForeground(Color.BLACK);
						toraInput1.setForeground(Color.BLACK);
						runWInput2.setForeground(Color.BLACK);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Lda!\nLda must be at least "
										+ Integer.parseInt(runLInput1.getText().trim()) / 2 + "\nand at most "
										+ Integer.parseInt(runLInput1.getText().trim()));
					} else if (Integer
							.parseInt(dtInput1.getText().trim()) > Integer.parseInt(runLInput1.getText().trim()) / 2
							|| Integer.parseInt(dtInput1.getText().trim()) < 0) {
						dtInput1.setForeground(Color.RED);
						ldaInput1.setForeground(Color.BLACK);
						asdaInput2.setForeground(Color.BLACK);
						asdaInput1.setForeground(Color.BLACK);
						todaInput2.setForeground(Color.BLACK);
						todaInput1.setForeground(Color.BLACK);
						toraInput2.setForeground(Color.BLACK);
						toraInput1.setForeground(Color.BLACK);
						runWInput2.setForeground(Color.BLACK);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Displaced Threshold!\nDisplaced Threshold must be at least 0 \nand at most "
										+ Integer.parseInt(runLInput1.getText().trim()) / 2);
					} else if (Integer
							.parseInt(dtInput2.getText().trim()) > Integer.parseInt(runLInput2.getText().trim()) / 2
							|| Integer.parseInt(dtInput2.getText().trim()) < 0) {
						dtInput2.setForeground(Color.RED);
						dtInput1.setForeground(Color.BLACK);
						ldaInput1.setForeground(Color.BLACK);
						asdaInput2.setForeground(Color.BLACK);
						asdaInput1.setForeground(Color.BLACK);
						todaInput2.setForeground(Color.BLACK);
						todaInput1.setForeground(Color.BLACK);
						toraInput2.setForeground(Color.BLACK);
						toraInput1.setForeground(Color.BLACK);
						runWInput2.setForeground(Color.BLACK);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);
						JOptionPane.showMessageDialog(null,
								"Invalid Displaced Threshold!\nDisplaced Threshold must be at least 0 \nand at most "
										+ Integer.parseInt(runLInput2.getText().trim()) / 2);
				
					} else {
						clrwyLnInput2.setForeground(Color.BLACK);
						clrwyLnInput1.setForeground(Color.BLACK);
						clrwyWdInput2.setForeground(Color.BLACK);
						clrwyWdInput1.setForeground(Color.BLACK);
						stpwyInput2.setForeground(Color.BLACK);
						stpwyInput1.setForeground(Color.BLACK);
						dtInput2.setForeground(Color.BLACK);
						dtInput1.setForeground(Color.BLACK);
						ldaInput1.setForeground(Color.BLACK);
						asdaInput2.setForeground(Color.BLACK);
						asdaInput1.setForeground(Color.BLACK);
						todaInput2.setForeground(Color.BLACK);
						todaInput1.setForeground(Color.BLACK);
						toraInput2.setForeground(Color.BLACK);
						toraInput1.setForeground(Color.BLACK);
						runWInput2.setForeground(Color.BLACK);
						runWInput1.setForeground(Color.BLACK);
						runLInput2.setForeground(Color.BLACK);
						runLInput1.setForeground(Color.BLACK);
						dir2.setForeground(Color.BLACK);
						dir1.setForeground(Color.BLACK);

						Runway rn = new Runway(name1 + chara, "remarks", Integer.parseInt(name1), chara,
								Double.parseDouble(runLInput1.getText().trim()),
								Double.parseDouble(runWInput1.getText().trim()),
								Double.parseDouble(toraInput1.getText().trim()),
								Double.parseDouble(todaInput1.getText().trim()),
								Double.parseDouble(asdaInput1.getText().trim()),
								Double.parseDouble(ldaInput1.getText().trim()),
								Double.parseDouble(dtInput1.getText().trim()),
								Double.parseDouble(stpwyInput1.getText().trim()),
								Double.parseDouble(clrwyWdInput1.getText().trim()),
								Double.parseDouble(clrwyLnInput1.getText().trim()));

						Runway rnc = new Runway(name2 + charb, "remarks", Integer.parseInt(name2), charb,
								Double.parseDouble(runLInput2.getText().trim()),
								Double.parseDouble(runWInput2.getText().trim()),
								Double.parseDouble(toraInput2.getText().trim()),
								Double.parseDouble(todaInput2.getText().trim()),
								Double.parseDouble(asdaInput2.getText().trim()),
								Double.parseDouble(ldaInput2.getText().trim()),
								Double.parseDouble(dtInput2.getText().trim()),
								Double.parseDouble(stpwyInput2.getText().trim()),
								Double.parseDouble(clrwyWdInput2.getText().trim()),
								Double.parseDouble(clrwyLnInput2.getText().trim()));
						rn.setComplement(rnc);
						list.addElement(rn);
						// list.addElement(rnc);
						Controller.getContr().addRunway(rn);
						// controller.addRunway(rnc);

						dir1.setText("");
						runLInput1.setText("");
						runWInput1.setText("");
						toraInput1.setText("");
						todaInput1.setText("");
						asdaInput1.setText("");
						ldaInput1.setText("");
						dtInput1.setText("");
						stpwyInput1.setText("");
						clrwyWdInput1.setText("");
						clrwyLnInput1.setText("");

						dir2.setText("");
						runLInput2.setText("");
						runWInput2.setText("");
						toraInput2.setText("");
						todaInput2.setText("");
						asdaInput2.setText("");
						ldaInput2.setText("");
						dtInput2.setText("");
						stpwyInput2.setText("");
						clrwyWdInput2.setText("");
						clrwyLnInput2.setText("");

						runwaySetPanel.dispose();
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error, please check all your inputs are valid");

				}

			}
		});
		c.gridx = 1;
		c.gridy = i;
		add.setPreferredSize(new Dimension(150, 30));
		add(add, c);

		// Navigates to the help menu
		JButton help = new JButton("Help");
		help.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				// TODO : add listener to take us to new window
			}
		});
		c.gridx = 2;
		c.gridy = i;
		help.setPreferredSize(new Dimension(150, 30));
		add(help, c);
		
		i++;
		
		final JLabel nbLabel = new JLabel("<html>**NB Runway length must be equal to TORA + stopway length </html>");
		c.gridwidth=3;
		c.gridx = 0;
		c.gridy = i;
		add(nbLabel, c);

	}

}
