package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

// Class that created the side on view for a runway
public class SideOnView extends ViewMaster {
	private int endBuffer;

	private double runwayLength;
	private String id;

	private Double ldaL, toraL, todaL, asdaL, stripEndL, resaL, hx50L, obsDistL, displacementThreshold;
	private boolean leftRight, obsLeft;

	private Double obsWidth, obsHeight;

	Color backgroundBelowC, backgroundAboveC, backgroundCloudC, backgroundGrassC, runwayC, ldaC, toraC, todaC, asdaC,
			stripEndC, resaC, hx50C, obsC, leftRightArrowC, rightLeftArrowC, slopeC;

	private int width, height;

	private final int arrowThickness, nameArrowThickness, textSize, arrowGap, slopeThickness;

	public SideOnView(double runwayLength, String id, boolean leftRight) {
		endBuffer = 100;

		this.runwayLength = runwayLength;
		this.id = id;
		this.leftRight = leftRight;
		this.obsLeft = leftRight;

		ldaL = null;
		toraL = null;
		todaL = null;
		asdaL = null;
		stripEndL = null;
		resaL = null;
		hx50L = null;
		obsDistL = null;
		displacementThreshold = 0d;

		this.backgroundBelowC = new Color(150, 253, 138);
		this.backgroundAboveC = new Color(135, 206, 235);
		this.backgroundCloudC = new Color(240, 240, 240, 50);
		this.backgroundGrassC = new Color(12, 183, 0);
		this.runwayC = Color.black;
		this.ldaC = Color.BLACK;
		this.toraC = Color.BLACK;
		this.todaC = Color.black;
		this.asdaC = Color.black;
		this.stripEndC = Color.black;
		this.resaC = Color.BLACK;
		this.hx50C = Color.BLACK;
		this.obsC = Color.red;
		this.slopeC = Color.black;
		this.leftRightArrowC = Color.black;
		this.rightLeftArrowC = Color.black;

		arrowThickness = 3;
		nameArrowThickness = 10;
		textSize = 10;
		arrowGap = 35;
		slopeThickness = 1;
	}

	// Setter for displacement threshold
	public void displacementThreshold(Double displThresh) {
		if (displThresh != null) {
			this.displacementThreshold = displThresh;
		} else {
			this.displacementThreshold = 0d;
		}
	}

	// Setter for Lda
	public void setLda(Double ldaL) {
		this.ldaL = ldaL;
	}

	// Setter for Tora
	public void setTora(Double toraL) {
		this.toraL = toraL;
	}

	// Setter for Toda
	public void setToda(Double todaL) {
		this.todaL = todaL;
	}

	// Setter for Asda
	public void setAsda(Double asdaL) {
		this.asdaL = asdaL;
	}

	// Setter for strip end
	public void setStripEnd(Double stripEndL) {
		this.stripEndL = stripEndL;
	}

	// Setter for Resa
	public void setResa(Double resaL) {
		this.resaL = resaL;
	}

	// Setter for Hx50
	public void setHx50(Double hx50L) {
		this.hx50L = hx50L;
	}

	// Setter for obstacle
	public void setObstacle(Double obsL, Double width, Double height, boolean obsLeft) {
		this.obsDistL = obsL;
		this.obsWidth = width;
		this.obsHeight = height;

		this.obsLeft = obsLeft;
	}

	@Override
	public void repaint() {
		width = getWidth();
		height = getHeight();
		super.repaint();
	}

	// Draws the view
	public void paint(Graphics g) {
		super.paint(g);

		genBackground(g);
		genArrowName(g);
		genRunway(g);

		Double displ = 0d;
		if (resaL != null && hx50L != null) {
			displ = resaL > hx50L ? resaL : hx50L;
		}
		displ += stripEndL != null ? stripEndL : 0;
		displ = displ > displacementThreshold ? displ : displacementThreshold;

		if (!obsLeft && obsDistL != null && obsWidth != null) {
			displ += runwayLength - (obsDistL + obsWidth);
		}
		else if(obsDistL != null && obsWidth != null)
		{
			displ += obsDistL + obsWidth;
		}

		genParameters(g, getPixelConverted(displ) + endBuffer);

		// Draws in the obstacle and relevant labels if one exists
		if (obsWidth != null && obsHeight != null && obsDistL != null) {
			genStripEnd(g);
			genObstacle(g);
			genSlope(g);
		}
	}

	// Methods takes an integer and converts it to a pixel id on the screen
	// relative to the screen size, borders and other runway sizes
	private int getPixelConverted(double val) {
		float value = (float) val;
		float scaleFactor = (width - (2 * endBuffer)) / ((float) runwayLength);
		return (int) (value * scaleFactor);
	}

	// Generates the arrows for the view that will have labels inidication what
	// they represent, eg TODA TORA ASDA etc.
	protected void genArrow(Graphics g, int x, int y, int x2, int y2, String text, int arrowThickness, float textSize,
			boolean doubleHeaded) {
		// Draws the arrow
		super.genArrow(g, x, y, x2, y2, text, arrowThickness, textSize);

		if (doubleHeaded) {
			super.genArrow(g, x2, y2, x, y, "", arrowThickness, textSize);
		}
	}

	// Generates the backgroud for the view
	private void genBackground(Graphics g) {
		// Colours the sky with predefined colour
		g.setColor(backgroundAboveC);
		// Draws in the sky
		g.fillRect(0, 0, width, height / 2);

		// Draws in the ground with predefined colour
		g.setColor(backgroundBelowC);
		// Draws the ground
		g.fillRect(0, height / 2, width, height);

		// Sets the colour for the grass
		g.setColor(backgroundGrassC);
		// Generates the grass texture for the ground
		Random rand = new Random();
		// Randomly draws the grass
		for (int x = 0; x < width; x += 2) {
			for (int y = height / 2; y < height; y += 2) {
				if (rand.nextInt(5) == 0) {
					// Grass has length max and min and length is randomly
					// generated and drawn
					g.drawArc(x, y, rand.nextInt(30), rand.nextInt(30), rand.nextInt(20), rand.nextInt(20));
				}
			}
		}
		for (int x = 0; x < width; x++) {
			int h = rand.nextInt(40);
			g.drawArc(x, height / 2 - h / 2, rand.nextInt(30), h, rand.nextInt(30), rand.nextInt(30));
		}

		int buffer = 100;

		// Sets the colour for the clouds accroding to predefined colour
		g.setColor(backgroundCloudC);
		// Randomly draws the clouds as circles in the sky
		for (int x = 0; x < width; x += 2) {
			if (rand.nextInt(10) == 0) {
				int yPos = rand.nextInt((height / 2) - 2 * buffer);

				// The clouds has a max diameter and their size is randomly
				// generated
				g.fillOval(x, yPos, rand.nextInt(50) + 50, rand.nextInt(50) + 50);

				// They are randomly added to the sky
				int numExtras = rand.nextInt(5);
				for (int i = 0; i < numExtras; i++) {
					g.fillOval(50 + x - rand.nextInt(100), 50 + yPos - rand.nextInt(100), rand.nextInt(50) + 50,
							rand.nextInt(50) + 50);
				}
			}
		}
	}

	// Generates the runway for the side view
	private void genRunway(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(8));

		// Sets the colour of the runway
		g2.setColor(runwayC);
		// Draws the runway as a line
		g2.drawLine(endBuffer + 4, height / 2, width - (endBuffer + 4), height / 2);
	}

	// Generates the lengths of the arrows and labels
	private void genParameters(Graphics g, int start) {
		int gapX = 3;

		if (leftRight) {
			if (obsLeft) {
				// For LEFT to LIGHT runway
				if (ldaL != null) {
					g.setColor(ldaC);

					// Generates arrow for the Lda
					genArrow(g, start,
							height / 2 + (gapX * arrowGap),
							start + getPixelConverted(ldaL),
							height / 2 + (gapX * arrowGap), getLdaText(), arrowThickness, textSize, true);
					gapX++;
				}
				if (toraL != null) {
					g.setColor(toraC);

					// Generated arrow for the Tora
					genArrow(g, width - endBuffer, height / 2 + (gapX * arrowGap),
							width - endBuffer - getPixelConverted(toraL), height / 2 + (gapX * arrowGap), getTodaText(),
							arrowThickness, textSize, true);
					gapX++;
				}
				if (todaL != null) {
					g.setColor(todaC);

					// Generates arrow for the Toda
					genArrow(g, width - endBuffer, height / 2 + (gapX * arrowGap),
							width - endBuffer - getPixelConverted(todaL), height / 2 + (gapX * arrowGap), getTodaText(),
							arrowThickness, textSize, true);
					gapX++;
				}
				if (asdaL != null) {
					g.setColor(asdaC);

					// Generates arrow for the Asda
					genArrow(g, width - endBuffer, height / 2 + (gapX * arrowGap),
							width - endBuffer - getPixelConverted(asdaL), height / 2 + (gapX * arrowGap), getAsdaText(),
							arrowThickness, textSize, true);
					gapX++;
				}
			} else {
				// For LEFT to LIGHT
				if (ldaL != null) {
					g.setColor(ldaC);

					genArrow(g, width - start, height / 2 + (gapX * arrowGap),
							width - (start + getPixelConverted(ldaL)), height / 2 + (gapX * arrowGap),
							getLdaText(), arrowThickness, textSize, true);
					gapX++;
				}
				if (toraL != null) {
					g.setColor(toraC);

					genArrow(g, endBuffer, height / 2 + (gapX * arrowGap), endBuffer + getPixelConverted(toraL),
							height / 2 + (gapX * arrowGap), "TORA", arrowThickness, textSize, true);
					gapX++;
				}
				if (todaL != null) {
					g.setColor(todaC);

					genArrow(g, endBuffer, height / 2 + (gapX * arrowGap), endBuffer  + getPixelConverted(todaL),
							height / 2 + (gapX * arrowGap), getTodaText(), arrowThickness, textSize, true);
					gapX++;
				}
				if (asdaL != null) {
					g.setColor(asdaC);

					genArrow(g, endBuffer, height / 2 + (gapX * arrowGap), endBuffer + getPixelConverted(asdaL),
							height / 2 + (gapX * arrowGap), getAsdaText(), arrowThickness, textSize, true);
					gapX++;
				}
			}
		} else {
			if (obsLeft) {
				// For RIGHT to LEFT runway
				if (ldaL != null) {
					g.setColor(ldaC);

					// Generates complement Lda arrow
					genArrow(g, width - start, height / 2 + (gapX * arrowGap),
							width - (start + getPixelConverted(ldaL)), height / 2 + (gapX * arrowGap),
							getLdaText(), arrowThickness, textSize, true);
					gapX++;
				}
				if (toraL != null) {
					g.setColor(toraC);

					// Generates complement Tora arrow
					genArrow(g, endBuffer, height / 2 + (gapX * arrowGap), endBuffer + getPixelConverted(toraL),
							height / 2 + (gapX * arrowGap), getToraText(), arrowThickness, textSize, true);
					gapX++;
				}
				if (todaL != null) {
					g.setColor(todaC);

					// Generates complement Toda arrow
					genArrow(g, endBuffer, height / 2 + (gapX * arrowGap), endBuffer + getPixelConverted(todaL),
							height / 2 + (gapX * arrowGap), getTodaText(), arrowThickness, textSize, true);
					gapX++;
				}
				if (asdaL != null) {
					g.setColor(asdaC);

					// Generates complement Asda arrow
					genArrow(g, endBuffer, height / 2 + (gapX * arrowGap), endBuffer + getPixelConverted(asdaL),
							height / 2 + (gapX * arrowGap), getAsdaText(), arrowThickness, textSize, true);
					gapX++;
				}
			} else {
				// For RIGHT to LEFT runway with obstacle on the right
				if (ldaL != null) {
					g.setColor(ldaC);

					// Generates arrow for Lda
					genArrow(g, start,
							height / 2 + (gapX * arrowGap),
							start + getPixelConverted(ldaL),
							height / 2 + (gapX * arrowGap), getLdaText(), arrowThickness, textSize, true);
					gapX++;
				}
				if (toraL != null) {
					g.setColor(toraC);

					// Generates arrow for Tora
					genArrow(g, width - endBuffer, height / 2 + (gapX * arrowGap),
							width - endBuffer - getPixelConverted(toraL), height / 2 + (gapX * arrowGap), getToraText(),
							arrowThickness, textSize, true);
					gapX++;
				}
				if (todaL != null) {
					g.setColor(todaC);

					// Generates arrow for Toda
					genArrow(g, width - endBuffer, height / 2 + (gapX * arrowGap),
							width - endBuffer -  getPixelConverted(todaL), height / 2 + (gapX * arrowGap), getTodaText(),
							arrowThickness, textSize, true);
					gapX++;
				}
				if (asdaL != null) {
					g.setColor(asdaC);

					// Generates arrow for Asda
					genArrow(g, width - endBuffer, height / 2 + (gapX * arrowGap),
							width - endBuffer - getPixelConverted(asdaL), height / 2 + (gapX * arrowGap), getAsdaText(),
							arrowThickness, textSize, true);
					gapX++;
				}
			}
		}
	}

	// Generates the strip end
	private void genStripEnd(Graphics g) {

		if (resaL == null | hx50L == null) {
			return;
		}

		double displacement = resaL > hx50L ? resaL : hx50L;

		// Going left to right
		if (leftRight) {

			// Checks that stripend and resa exist
			if (stripEndL != null) {

				// Checking position of the obstacle
				if (obsLeft) {

					// Sets the colour and generates the arrows for the stripend
					g.setColor(stripEndC);

					// Generates the arrow for the stripend
					genArrow(g, endBuffer + getPixelConverted(obsDistL + obsWidth + displacement),
							height / 2 + arrowGap,
							endBuffer + getPixelConverted(obsDistL + obsWidth + stripEndL + displacement),
							height / 2 + arrowGap, "Strip End", arrowThickness, textSize, true);
				} else {
					g.setColor(stripEndC);
					genArrow(g, endBuffer + getPixelConverted(obsDistL - displacement), height / 2 + arrowGap,
							endBuffer + getPixelConverted(obsDistL - stripEndL - displacement), height / 2 + arrowGap,
							"Strip End", arrowThickness, textSize, true);
				}
			}
		} else {

			// Checks stripend and resa exist
			if (stripEndL != null && resaL != null) {

				// If the obstacle is on the left of the centre
				if (obsLeft) {

					// Set the coloud of the arrow
					g.setColor(stripEndC);

					// Draw the arrow for the stripend
					genArrow(g, width - (endBuffer + getPixelConverted(obsDistL + obsWidth + displacement)),
							height / 2 + arrowGap,
							width - (endBuffer + getPixelConverted(obsDistL + obsWidth + stripEndL + displacement)),
							height / 2 + arrowGap, "Strip End", arrowThickness, textSize, true);
				} else {
					g.setColor(stripEndC);
					genArrow(g, width - (endBuffer + getPixelConverted(obsDistL - displacement)), height / 2 + arrowGap,
							width - (endBuffer + getPixelConverted(obsDistL - stripEndL - displacement)),
							height / 2 + arrowGap, "Strip End", arrowThickness, textSize, true);
				}
			}
		}

		genResa(g);
		genHx50(g);
	}

	// Generates Resa
	private void genResa(Graphics g) {

		// Going from left an right on runway
		if (leftRight) {

			// If Resa is defined
			if (resaL != null) {

				// Set the colour for the resa
				g.setColor(resaC);

				// If the obstacle exists on the left of the centre
				if (obsLeft) {

					// Generate RESA arrow
					genArrow(g, endBuffer + getPixelConverted(obsDistL + obsWidth), height / 2 + arrowGap,
							endBuffer + getPixelConverted(obsDistL + obsWidth + resaL), height / 2 + arrowGap, "RESA",
							arrowThickness, textSize, true);
				} else {
					genArrow(g, endBuffer + getPixelConverted(obsDistL), height / 2 + arrowGap,
							endBuffer + getPixelConverted(obsDistL - resaL), height / 2 + arrowGap, "RESA",
							arrowThickness, textSize, true);
				}
			}
		} else {

			// If Resa is defined
			if (resaL != null) {

				// Set the colour for the resa
				g.setColor(resaC);

				// If the obstacle is on the the left
				if (obsLeft) {

					// Generate the arrow for the resa
					genArrow(g, width - (endBuffer + getPixelConverted(obsDistL + obsWidth)), height / 2 + arrowGap,
							width - (endBuffer + getPixelConverted(obsDistL + obsWidth + resaL)), height / 2 + arrowGap,
							"RESA", arrowThickness, textSize, true);
				} else {

					// Generate the arrow for the resa
					genArrow(g, width - (endBuffer + getPixelConverted(obsDistL)), height / 2 + arrowGap,
							width - (endBuffer + getPixelConverted(obsDistL + obsWidth - resaL)), height / 2 + arrowGap,
							"RESA", arrowThickness, textSize, true);
				}
			}
		}
	}

	// Generate h x 50 line as per specified in the spec
	private void genHx50(Graphics g) {

		// Going left to right
		if (leftRight) {
			if (hx50L != null) {

				// Set the colour of the line
				g.setColor(hx50C);

				// If the obstacle is on the left
				if (obsLeft) {

					// Generate the h x 50 arrow depending on dimensions of view
					genArrow(g, endBuffer + getPixelConverted(obsDistL), height / 2 + 2 * arrowGap,
							endBuffer + getPixelConverted(obsDistL + hx50L), height / 2 + 2 * arrowGap, "h x 50",
							arrowThickness, textSize, true);
				} else {
					genArrow(g, endBuffer + getPixelConverted(obsDistL + obsWidth), height / 2 + 2 * arrowGap,
							endBuffer + getPixelConverted(obsDistL + obsWidth - hx50L), height / 2 + 2 * arrowGap,
							"h x 50", arrowThickness, textSize, true);
				}
			}
		} else {
			if (hx50L != null) {

				// Set the colour for the line
				g.setColor(hx50C);

				if (obsLeft) {

					// Generate the arrow for the h x 50 label according to
					// dimensions
					genArrow(g, width - (endBuffer + getPixelConverted(obsDistL)), height / 2 + 2 * arrowGap,
							width - (endBuffer + getPixelConverted(obsDistL + hx50L)), height / 2 + 2 * arrowGap,
							"h x 50", arrowThickness, textSize, true);
				} else {
					genArrow(g, width - (endBuffer + getPixelConverted(obsDistL + obsWidth)), height / 2 + 2 * arrowGap,
							width - (endBuffer + getPixelConverted(obsDistL + obsWidth - hx50L)),
							height / 2 + 2 * arrowGap, "h x 50", arrowThickness, textSize, true);
				}
			}
		}
	}

	// Generates the obstacle that goes on the runway
	private void genObstacle(Graphics g) {
		// If going left to right
		if (leftRight) {
			// If the obstacle exists and is not null
			if (obsDistL != null && obsWidth != null && obsHeight != null) {

				// Sets the colour of the obstacle
				g.setColor(obsC);

				// Draws the obstacle as a rectangle
				g.fillRect(endBuffer + getPixelConverted(obsDistL), (height / 2) - getPixelConverted(obsHeight),
						getPixelConverted(obsWidth), getPixelConverted(obsHeight));

			}
		} else {
			if (obsDistL != null && obsWidth != null && obsHeight != null) {
				g.setColor(obsC);
				g.fillRect(width - (endBuffer + getPixelConverted(obsDistL + obsWidth)),
						(height / 2) - getPixelConverted(obsHeight), getPixelConverted(obsWidth),
						getPixelConverted(obsHeight));
			}
		}
	}

	// Generates arrow labels
	private void genArrowName(Graphics g) {
		// If the arrow is pointing left to right
		if (leftRight) {
			// Sets the colour of the arrow and draws it
			g.setColor(leftRightArrowC);
			super.genArrow(g, 0, 30, 120, 30, id, nameArrowThickness, 16);
		} else {
			g.setColor(rightLeftArrowC);
			super.genArrow(g, width, 30, width - 120, 30, id, nameArrowThickness, 16);
		}
	}

	// Generates the slope lines from the obstacle
	private void genSlope(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// Sets the thickness of the lines
		g2.setStroke(new BasicStroke(slopeThickness));

		// If going left to right
		if (leftRight) {
			// Checks that values for the runway exists and that the obstacle
			// exists on the runway
			if (ldaL != null && hx50L != null && stripEndL != null && obsHeight != null) {
				// Sets the colour for the slope line
				g.setColor(slopeC);

				// If the obstacle is on the left of the centre
				if (obsLeft) {
					// Draws the line according to the calculations
					g.drawLine(endBuffer + getPixelConverted(obsDistL), height / 2 - getPixelConverted(obsHeight),
							endBuffer + getPixelConverted(obsDistL + obsWidth + resaL), height / 2);
					g.drawLine(endBuffer + getPixelConverted(obsDistL + stripEndL),
							height / 2 - getPixelConverted(obsHeight),
							endBuffer + getPixelConverted(obsDistL + obsWidth + resaL + stripEndL), height / 2);
				} else {
					g.drawLine(endBuffer + getPixelConverted(obsDistL + obsWidth),
							height / 2 - getPixelConverted(obsHeight),
							endBuffer + getPixelConverted(obsDistL - obsWidth - resaL), height / 2);
					g.drawLine(endBuffer + getPixelConverted(obsDistL + obsWidth - stripEndL),
							height / 2 - getPixelConverted(obsHeight),
							endBuffer + getPixelConverted(obsDistL - obsWidth - resaL - stripEndL), height / 2);
				}
			}
		} else {
			if (ldaL != null && hx50L != null && stripEndL != null && obsHeight != null) {

				// Sets the colour of the line
				g.setColor(slopeC);

				// If the obstacle is on the left of the centre line
				if (obsLeft) {
					// Draw the line
					g.drawLine(width - (endBuffer + getPixelConverted(obsDistL)),
							height / 2 - getPixelConverted(obsHeight),
							width - (endBuffer + getPixelConverted(obsDistL + obsWidth + resaL)), height / 2);
					g.drawLine(width - (endBuffer + getPixelConverted(obsDistL + stripEndL)),
							height / 2 - getPixelConverted(obsHeight),
							width - (endBuffer + getPixelConverted(obsDistL + obsWidth + resaL + stripEndL)),
							height / 2);
				} else {
					g.drawLine(width - (endBuffer + getPixelConverted(obsDistL + obsWidth)),
							height / 2 - getPixelConverted(obsHeight),
							width - (endBuffer + getPixelConverted(obsDistL - obsWidth - resaL)), height / 2);
					g.drawLine(width - (endBuffer + getPixelConverted(obsDistL + obsWidth - stripEndL)),
							height / 2 - getPixelConverted(obsHeight),
							width - (endBuffer + getPixelConverted(obsDistL - obsWidth - resaL - stripEndL)),
							height / 2);
				}
			}
		}
	}
}
