package View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Zoom extends JPanel {
	private MainFrame tool;
	private JProgressBar progressBar;
	private JButton reset;
	private int progress;

	public Zoom(MainFrame tool) {
		this.tool = tool;
		
	}
	
	// Call to intialze view
	public void init() {
		
		final DecimalFormat numberFormat = new DecimalFormat("#.00");

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(2, 0, 2, 0);
		int i = 0;

		JLabel fac = new JLabel("Factor:");
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 0;
		c.gridx = 0;
		c.gridy = i;
		add(fac, c);

		final JLabel facDisp = new JLabel();
		c.gridx = 1;
		c.gridy = i;
		add(facDisp, c);

		i++;
		
		// Allows the user to see how much further in they can zoom
		progressBar = new JProgressBar(0, (int) tool.topDownWrapper.getMaxZoom() );
		// Starts from 0%
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		c.gridx = 0;
		c.gridy = i;
		add(progressBar, c);
		
		i++;
		
		c.fill = GridBagConstraints.BOTH;

		// Allows the user to zoom into the view 
		JButton zoomin = new JButton("Zoom In");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = i;
		add(zoomin, c);
		zoomin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Retrieves which runway view is being zoomed into
				tool.getCurrentDisplay().zoomIn();
				// Repaints to display the changes
				tool.getCurrentDisplay().repaint();
				
				// Sets the zoom factor label
				facDisp.setText("x " + numberFormat.format(tool.getCurrentDisplay().getZoomAmount()));
				
				// Updates the progress bar
				progressBar.setVisible(true);
				progress = (int) tool.topDownWrapper.getZoomAmount();
				progressBar.setValue(progress);

				// Allows the user to use the reset button to go back to the original zoom
				reset.setEnabled(true);
			}
		});

		// Allows user to zoom out of the view
		JButton zoomout = new JButton("Zoom Out");
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = i;
		add(zoomout, c);
		zoomout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Gets the runway display to zoom out of
				tool.getCurrentDisplay().zoomOut();
				// Updates the GUI view 
				tool.getCurrentDisplay().repaint();
				
				// Updates the label that shows the zoom factor
				facDisp.setText("x " + numberFormat.format(tool.getCurrentDisplay().getZoomAmount()));
				
				// Updates the zoom bar
				progress = (int) tool.topDownWrapper.getZoomAmount();
				progressBar.setValue(progress);
			}
		});

		i++;

		// When the view is zoomed into it allows the user to move up the image
		JButton up = new JButton("Up");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = i;
		add(up, c);
		up.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Gets the view to change and applies the function
				tool.getCurrentDisplay().moveUp();
				// Updates view
				tool.getCurrentDisplay().repaint();
			}
		});

		// When the view is zoomed into it allows the user to move down the image
		JButton down = new JButton("Down");
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = i;
		add(down, c);
		down.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Gets the view to change and applies the function
				tool.getCurrentDisplay().moveDown();
				// Updates view
				tool.getCurrentDisplay().repaint();
			}
		});

		i++;

		// When the view is zoomed into it allows the user to move left the image
		JButton left = new JButton("Left");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = i;
		add(left, c);
		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Gets the view to change and applies the function
				tool.getCurrentDisplay().moveLeft();
				// Updates view
				tool.getCurrentDisplay().repaint();
			}
		});

		// When the view is zoomed into it allows the user to move right the image
		JButton right = new JButton("Right");
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = i;
		add(right, c);
		right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Gets the view to change and applies the function
				tool.getCurrentDisplay().moveRight();
				// Updates view
				tool.getCurrentDisplay().repaint();
			}
		});

		i++;

		// Allows the user to reset the view so it is not zoomed into
		reset = new JButton("Reset");
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = i;
		add(reset, c);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Gets the view to change and applies the function
				tool.getCurrentDisplay().resetZoom();
				// Updates the view
				tool.getCurrentDisplay().repaint();
				
				// Updates the zoom amount display
				facDisp.setText("x " + numberFormat.format(tool.getCurrentDisplay().getZoomAmount()));
				
				// Updates the progress bar
				progressBar.setVisible(true);
				int progress = 0;
				progressBar.setValue(progress);
				
				// Disables the reset button, only enabled when the user zooms in again 
				reset.setEnabled(false);
			}
		});
	}
}
