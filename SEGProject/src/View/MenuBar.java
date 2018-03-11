package View;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import Controller.Controller;

// Class to create the menu bar for the GUI
public class MenuBar extends JPanel {

	private MainFrame tool;
	private JFileChooser fileChooser;
	JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;

	protected MenuBar(MainFrame tool) {
		this.tool = tool;
		menuBar = new JMenuBar();
	}

	// Method to initialise the menu bar
	protected void init() {

		// Creates a menu title - Options to contain all the general tool
		// functions
		menu = new JMenu("<html><a style='text-decoration:underline'>O</a>ptions</html>");
		// Can be accessed by using Ctrl+O
		menu.setMnemonic(KeyEvent.VK_O);
		menu.getAccessibleContext().setAccessibleDescription("Program Options");
		menuBar.add(menu);

		// Option that opens the main menu to allow user to define a new runway
		// redclaration tool
		menuItem = new JMenuItem("New", KeyEvent.VK_N);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// Creates new menu object
				/** TODO: CHANGE THIS NULL **/
				Menu menu = new Menu(null);
				menu.init();
			}

		});
		// Allows a key shortcut of Ctrl+N
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		// Allows user to navigate back to the main menu used to start the
		// original runway redeclaration tool
		menuItem = new JMenuItem("Open start menu", KeyEvent.VK_P);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tool.menuframe.setVisible(true);
			}

		});
		// Allows keyboard shortcut of Ctrl+P
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		menu.addSeparator();

		// Allows user to save a screenshot of the current situation
		menuItem = new JMenuItem("Save Snapshot", new ImageIcon("images/middle.gif"));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					Robot robot = new Robot();
					String format = "jpg";

					// Alerts user to save the file using the correct extension
					JOptionPane.showMessageDialog(null, "Please add .jpeg to the file name!");

					// Allows user to navigate to where they would like to save
					// the file
					fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

					String fileName = save(fileChooser);

					// Draws the screenshot square
					Point p = tool.getLocationOnScreen();

					// This is th area that will be screenshot
					Rectangle captureRect = new Rectangle(p.x, p.y, tool.getWidth(), tool.getHeight());

					// Creates an image of the screenshot
					BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
					ImageIO.write(screenFullImage, format, new File(fileName));

					fileChooser.setDialogTitle("Specify a file to save");

					JOptionPane.showMessageDialog(null, "Screenshot saved!\nFind it in project directory");
				} catch (AWTException | IOException | NullPointerException ex) {
					System.err.println(ex);
				}

			}

		});
		// Allows keyboard shortcut using Ctrl+C
		menuItem.setMnemonic(KeyEvent.VK_C);
		menu.add(menuItem);

		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menu.add(menuItem);

		// Allows user to export the current runway and obstacle (if one is defined) to an XML file
		menuItem = new JMenuItem("Export", KeyEvent.VK_E);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Allows user to naivagate to where they want to save the file
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(""));

				// Saves the file and writes the XML
				int retrieval = chooser.showSaveDialog(null);
				if (retrieval == JFileChooser.APPROVE_OPTION) {
					Controller.getContr().writeXml(chooser.getSelectedFile());
				}
			}

		});
		// Allows keyboard shortut for this function, using Ctrl+E
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		menu.addSeparator();

		// Allows the user to quit. This function closes down the whole application
		menuItem = new JMenuItem("<html><a style='text-decoration:underline'>Q</a>uit</html>", KeyEvent.VK_Q);
		menuItem.addActionListener(new ActionListener() {

			@Override
			// Checks with the user that they want to really close down the tool
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(tool, "Are you sure you wish to quit?", "", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					// If they confirm the tool closes
					System.exit(0);

				}
			}

		});
		// Allows keyboard shortcuts using the Ctrl+Q shortcut
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		// Build second menu in the menu bar for the file options
		menu = new JMenu("<html><a style='text-decoration:underline'>F</a>ile</html>");
		// Sets keyboard shortcut
		menu.setMnemonic(KeyEvent.VK_F);
		// Prompt for the purpose of this menu
		menu.getAccessibleContext().setAccessibleDescription("File Options");
		menuBar.add(menu);

		// Allows the user to save a snapshot of the system
		menuItem = new JMenuItem("Save Snapshot", KeyEvent.VK_A);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					Robot robot = new Robot();
					String format = "jpg";

					// Alerts user to save the file using the correct extension
					JOptionPane.showMessageDialog(null, "Please add .jpeg to the file name!");

					// Allows user to navigate to where they would like to save
					// the file
					fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

					String fileName = save(fileChooser);

					// Draws the screenshot square
					Point p = tool.getLocationOnScreen();

					// This is th area that will be screenshot
					Rectangle captureRect = new Rectangle(p.x, p.y, tool.getWidth(), tool.getHeight());

					// Creates an image of the screenshot
					BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
					ImageIO.write(screenFullImage, format, new File(fileName));

					fileChooser.setDialogTitle("Specify a file to save");

					JOptionPane.showMessageDialog(null, "Screenshot saved!\nFind it in project directory");
				} catch (AWTException | IOException | NullPointerException ex) {
					System.err.println(ex);
				}

			}

		});
		// Keyboard shortcut function using Ctrl+A
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		// Allows user to save the calculations in the calculations display to a text file
		menuItem = new JMenuItem("Save Calculations", KeyEvent.VK_C);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Allows use to navigate to the area where they want to save their file
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.home")));

				String fileName = save(chooser);

				// Writes the calculations to a file using the TextArea built in writer
				try (BufferedWriter fileOut = new BufferedWriter(new FileWriter(fileName))) {
					tool.calcsDisp.calcs.write(fileOut);
				} catch (IOException e) {
					e.printStackTrace();
				}

				// Notifies user
				JOptionPane.showMessageDialog(null, "Calculations saved to " + fileName);

			}

		});
		// Allows keyboard shortcut using Ctrl+C
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		menu.addSeparator();

		// Option to allow user to import a new runway. This naviagates to the import function on the main menu
		menuItem = new JMenuItem("Import", KeyEvent.VK_I);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tool.menuframe.setVisible(true);
				// Allows naviagation to the file
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Select xml file to import");

				// Gets the user selection
				int userSelection = chooser.showOpenDialog(null);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File toImport = chooser.getSelectedFile();

					try {
						Controller.getContr().readXml(tool.menuframe.cont.menu, toImport);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Please check the file format");
					}
				}
			}

		});
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		// Allows user to export the current situation of the runway and any obstacle that may be present to XML file
		menuItem = new JMenuItem("Export", KeyEvent.VK_E);
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Allows user to navigate to where they want to save the file
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(""));

				int retrieval = chooser.showSaveDialog(null);
				if (retrieval == JFileChooser.APPROVE_OPTION) {
					Controller.getContr().writeXml(chooser.getSelectedFile());
				}
			}

		});
		// Allows keyboard shortcut using Ctrl+E
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		// Build second menu in the menu bar for the help options
		menu = new JMenu("Help");

		// Shortcut for accessing help menu
		menu.setMnemonic(KeyEvent.VK_H);
		// Hint
		menu.getAccessibleContext().setAccessibleDescription("Help");
		menuBar.add(menu);

		// Option to allow user to navigate to the help documentation
		menuItem = new JMenuItem("View Help", KeyEvent.VK_H);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Opens help document
				Helper h = new Helper();

				h.init();
			}
		});
		// Keyboard shortcut for the help documentation
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menu.add(menuItem);
	}

	// File path when saving a screenshot
	private String save(JFileChooser f) {
		int retVal = f.showSaveDialog(null);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			File file = f.getSelectedFile();
			return file.getAbsolutePath();
		}
		return null;
	}
}
