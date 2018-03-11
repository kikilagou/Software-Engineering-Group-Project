package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import Controller.Controller;
import Model.Runway;

public class ObstacleControls extends JPanel {
	MainFrame tool;
	protected Boolean obstExistsOnRunway = false;
	private Runway runway;

	public ObstacleControls(Runway runway, MainFrame tool) {
		this.runway = runway;
		this.tool = tool;
	}

	// Call to initialise view
	public void init() {

		// Creates a text area where notifications will be displayed
		final JTextArea notField = new JTextArea();
		notField.setEditable(false);
		notField.setLineWrap(true);

		this.setLayout(new BorderLayout());

		JPanel top = new JPanel();
		top.setLayout(new GridLayout(1, 2));
		this.add(top, BorderLayout.SOUTH);

		// Notification that will be displayed when an existing obstacle is
		// removed from the runway
		final String removedNotif = ">> Obstacle was removed from runway.";

		// Button that navigates to obstacle settings. Allows the user to add 1
		// new obstacle to the runway
		JButton addNewObst = new JButton("Add New Obstacle");
		addNewObst.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (obstExistsOnRunway == false) {

					// Navigates to obstacle settings where obstacle parameters
					// can
					// be specified
					ObstSettings settings = new ObstSettings(runway, notField, obstExistsOnRunway, tool);
					settings.init();
					obstExistsOnRunway = true;
				} else {
					String current = notField.getText();
					String error = "!!  A runway can only have one obstacle at a time!";

					// "<html><font color=\"red\">A runway can only have one
					// obstacle at a time!</font></html>" + "\n" + current;
					notField.setText(error + "\n" + current);
				}

			}

		});
		top.add(addNewObst);

		// Allows the user to remove an existing obstacle from the runway
		JButton removeObstacle = new JButton("Remove Obstacle");
		removeObstacle.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent ev) {

				// Setting the obstacle to null on both runway directions
				runway.setObstacle(null);
				runway.getComplement().setObstacle(null);

				// Appends a notification to the notification text area
				String current = notField.getText();

				// First checking if an obstacle is present on the runway
				if (obstExistsOnRunway == true) {

					// Alerting user the obstacle has been removed
					current = removedNotif + "\n" + current;
					notField.setText(current);
				} else {

					// If no obstacle was present, user is notified that is
					// cannot be removed
					current = "!!  No obstacles on the runway!\n" + current;
					notField.setText(current);
				}

				// Recalculate values for the runway without the obstacle
				Controller.getContr().calculateRequest(runway);

				obstExistsOnRunway = false;
			}

		});
		top.add(removeObstacle);

		// Allows notifications panel to be scrollable
		JScrollPane scrollpane = new JScrollPane(notField);
		scrollpane.setPreferredSize(new Dimension(250, 100));

		TitledBorder title2 = BorderFactory.createTitledBorder("Notifications");
		scrollpane.setBorder(title2);

		// Only allows vertical scrolling, never horizontal scroll
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollpane, BorderLayout.CENTER);

	}
}
