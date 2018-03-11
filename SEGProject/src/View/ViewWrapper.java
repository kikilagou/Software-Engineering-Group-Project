package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

// Used to render the zoom by rendering a larger image than
// what is available on the screen
public class ViewWrapper extends JPanel 
{
	// View to wrap
	protected ViewMaster toWrap;
	
	// Current image on screen versus larger zoomed image
	private BufferedImage image, renderedImage;
	
	// Indicates if needs to render image on screen or
	// zoomed in image
	private boolean needRender, zoomChanged;

	// Zoom and positions
	private double maxZoom = 3;
	private int width, height;
	private double xThresh, yThresh, zoomAmount;

	public double getMaxZoom() {
		return maxZoom;
	}
	
	public ViewWrapper(ViewMaster toWrap) {
		this.toWrap = toWrap;
		xThresh = 0d;
		yThresh = 0d;
		zoomAmount = 1d;

		zoomChanged = true;

		width = getWidth();
		height = getHeight();

		this.setLayout(new GridLayout(1, 1));
		this.add(toWrap);
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;

		// Checks if the width/height changed and indicates re-render if so
		if (width != getWidth() | height != getHeight()) {
			zoomChanged = true;
			width = getWidth();
			height = getHeight();
		}

		g2.drawImage(getImage(xThresh, yThresh, zoomAmount), 0, 0, null);
	}

	private BufferedImage getImage(double xThresh, double yThresh, double zoomAmount) {
		
		// If zoom changed, must redraw image to new zoom amount
		if (zoomChanged) {
			image = new BufferedImage((int) (getWidth() * zoomAmount), (int) (getHeight() * zoomAmount),
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = (Graphics2D) image.getGraphics();
			toWrap.setSize(image.getWidth(), image.getHeight());
			toWrap.paint(g);
			needRender = true;
			zoomChanged = false;
		}

		// Draws sub image to current user selection
		if (needRender == true) {
			renderedImage = image.getSubimage((int) (getWidth() * xThresh * zoomAmount),
					(int) (getHeight() * yThresh * zoomAmount), getWidth(), getHeight());

		}

		return renderedImage;
	}

	/* METHODS FOR MANIPULATING ZOOM */
	public void resetZoom() {
		xThresh = 0d;
		yThresh = 0d;
		zoomAmount = 1d;
		zoomChanged = true;
	}

	public void zoomIn() {
		zoomAmount *= 1.1d;
		zoomChanged = true;

		if (zoomAmount >= maxZoom) {
			zoomAmount = maxZoom;
		}
	}

	public void zoomOut() {
		zoomAmount *= 0.9d;
		zoomChanged = true;
		if (zoomAmount <= 1d) {
			zoomAmount = 1d;
		}
	}

	public void moveLeft() {
		xThresh -= 0.1d;

		xThresh = adjustX(xThresh);

		needRender = true;
	}

	public void moveRight() {
		xThresh += 0.1d;

		xThresh = adjustX(xThresh);

		needRender = true;
	}

	public void moveUp() {
		yThresh -= 0.1d;

		yThresh = adjustY(yThresh);

		needRender = true;
	}

	public void moveDown() {
		yThresh += 0.1d;

		yThresh = adjustY(yThresh);
		needRender = true;
	}

	// Adjust the xThresh and yThresh if will cause
	// image to overflow
	private Double adjustX(Double current) {

		double max = (getWidth() * zoomAmount - getWidth()) / (getWidth() * zoomAmount);

		if (current < 0) {
			return 0d;
		} else if (current >= max) {
			return max;
		}
		return current;
	}
	private Double adjustY(Double current) {

		double max = (getHeight() * zoomAmount - getHeight()) / (getHeight() * zoomAmount);

		if (current < 0) {
			return 0d;
		} else if (current >= max) {
			return max;
		}
		return current;
	}

	protected ViewMaster getToWrap() {
		return toWrap;
	}

	public double getZoomAmount() {
		return zoomAmount;
	}
	
	// Needs to modify image
	public void repaint() {
		zoomChanged = true;
		super.repaint();
	}

}
