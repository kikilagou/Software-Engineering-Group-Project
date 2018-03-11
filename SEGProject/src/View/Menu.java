package View;

import Model.Airport;
import Model.Runway;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import Controller.Controller;

// Menu class creates a new Menu Frame
public class Menu {
	private MenuFrame menu;
	private Airport airport;

	public Menu(Airport airport) {
		this.airport = airport;
	}

	// Creating a new Menu Frame
	public void init() {
		menu = new MenuFrame(this, "Home");
		menu.init();
	}

	// Updates the airport
	public void refresh() {
		airport = Controller.getContr().getAirport();
		menu.refresh(airport);
		menu.ap.input.setText(airport.getAirportName());
	}
}

// Class for Menu frame
class MenuFrame extends JFrame {

	private Menu menu;
	private RunwayPanel rp;
	protected Continue cont;
	AirportPanel ap;

	public MenuFrame(Menu menu, String title) {
		super(title);
		this.menu = menu;
	}

	public void refresh(Airport airport) {
		rp.refresh();
	}

	// Method to set the look and feel of the GUI
	protected void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
	}

	protected void init() {

		// Sets the look and feel when Menu is initialised
		setLookAndFeel();

		// Panel to hold all the subpanels of the Menu GUI
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

		// Panel for the Runway selections in the Menu
		rp = new RunwayPanel();
		rp.init();

		// Panel for the buttons that take the user to the runway redeclaration
		// tool
		ap = new AirportPanel(rp);
		cont = new Continue(menu, rp, this, ap);
		cont.init();

		// Panel for the Airport selections in the Menu
		ap.cp = cont;
		ap.init();
		main.add(ap);

		// Adding a border around the Airport panel
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Select Airport");
		ap.setBorder(title);

		main.add(Box.createRigidArea(new Dimension(10, 1)));

		main.add(rp);
		main.add(cont);

		// Adding a border around the Ruwnway panel
		TitledBorder title2;
		title2 = BorderFactory.createTitledBorder("Select Runway");
		rp.setBorder(title2);

		this.setContentPane(main);
		this.setResizable(false);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

// Class for the Airport Panel that goes into the Menu Frame
class AirportPanel extends JPanel {

	JTextField input;
	RunwayPanel rp;
	Continue cp;

	protected AirportPanel(RunwayPanel rp) {
		this.rp = rp;

	}

	protected void init() {


		this.setLayout(new GridLayout(3, 1));

		JLabel add = new JLabel("Specify Airport:");
		add(add);

		input = new JTextField();
		add(input);

		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (!input.getText().isEmpty()) {

						for (Runway r : Controller.getContr().setAirportName(input.getText().trim())) {
							rp.runwaysList.addElement(r);

							rp.add.setEnabled(true);
							cp.importBtn.setEnabled(true);
						}
					
				} else {
//					rp.add.setEnabled(false);
//					cp.importBtn.setEnabled(false);
					JOptionPane.showMessageDialog(null, "Please Specify an Airport");

				}

			}

		});
		add(ok);

	}
}

// Class for the Runway panel that goes into the Menu
class RunwayPanel extends JPanel {

	DefaultComboBoxModel<Runway> runwaysList;

	JComboBox<Runway> runways;
	JButton add;

	public void refresh() {
		runwaysList.removeAllElements();
		List<Runway> rnwys = Controller.getContr().getRunways();

		// Adding all runways to the runway list
		for (Runway rn : rnwys) {
			runwaysList.addElement(rn);
		}
	}

	protected void init() {

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(7, 7, 7, 7);
		int i = 0;

		// List of user specified runways
		runwaysList = new DefaultComboBoxModel<Runway>();

		for (Runway runway : Controller.getContr().getRunways())
			runwaysList.addElement(runway);
		runways = new JComboBox<Runway>();
		runways.setModel(runwaysList);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = i;
		add(runways, c);

		i++;

		// Allows user to add a new runway, by navigating to the runway settings
		add = new JButton("Add New Runway");
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Opens runway settings window
				RunwaySettings customise = new RunwaySettings("Runway Settings", runwaysList);
				customise.init();

			}

		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = i;
		add(add, c);

		// Allows user to removed selected runway from combo box
		JButton remove = new JButton("Remove Runway");
		remove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Checks that there are runways specified that can be removed
				if (runways.getItemCount() > 0) {

					// Gets the selected runway and removes it
					Controller.getContr().removeRunway((Runway) runwaysList.getElementAt(runways.getSelectedIndex()));
					runways.removeItemAt(runways.getSelectedIndex());
				} else {
					JOptionPane.showMessageDialog(null, "No runways to remove");
				}

			}

		});
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = i;
		add(remove, c);
	}

	// Returns the selected item for the runways combo box
	protected Runway getSelected() {
		return (Runway) runwaysList.getSelectedItem();
	}
}

// Panel that allows the menu to open a new runway redeclatation tool with all
// the selected runways
class Continue extends JPanel {

	protected Menu menu;
	MenuFrame menuframe;
	private RunwayPanel rPanel;
	AirportPanel ap;
	JButton importBtn;

	// private Boolean isFirstClick = true;

	public Continue(Menu menu, RunwayPanel rPanel, MenuFrame m, AirportPanel ap) {
		this.menu = menu;
		this.rPanel = rPanel;
		this.ap = ap;
		this.menuframe = m;
	}

	protected void init() {
		this.setLayout(new FlowLayout());

		// Creates a new runway redeclaration tool
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (ap.input.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Specify an Airport");
				} else {
					Runway r = rPanel.getSelected();
					RunwayRedeclarationTool rdt = new RunwayRedeclarationTool(r, menuframe);

					if (rPanel.getSelected() != null) {
						rdt.init();

						Controller.getContr().calculateRequest(r);

						// Makes the menu dissapear
						menuframe.setVisible(false);

					} else {
						JOptionPane.showMessageDialog(null, "You need to select a runway!", "Warning",
								JOptionPane.WARNING_MESSAGE);
					}

				}
			}

		});

		// Navigates to the help manual
		JButton help = new JButton("Help");
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Helper h = new Helper();

				h.init();
			}
		});

		// Allows runways to be imported as XML files. These may have been
		// previously saved by the user or newly defined
		importBtn = new JButton("Import");
		importBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// Allows naviagyion to the file
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Select xml file to import");

				// Gets the user selection
				int userSelection = chooser.showOpenDialog(null);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File toImport = chooser.getSelectedFile();

					try {
						Controller.getContr().readXml(menu, toImport);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Please check the file format");
					}
				}
			}

		});

		// Adding all the items to the panel
		add(ok);
		add(help);
		add(importBtn);

	}
}
