package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Random;

/* Class used to generate view of runway. Runway parameters optional and
 * can be set to null if they are to be removed/not shown. Although after setting
 * new values, repaint() should be called.
 * 
 * Be aware that width in terms of 
 * the JPanel is not the same as the width of the runway. Width of runway interpreted 
 * as though standing on one end looking towards other and measureing left to right distance. 
 */

public class TopDownRunway extends ViewMaster
{
	// Left and right border size in pixels
	private int endBuffer;
	
	// Holds runway details
	protected double runwayWidth, runwayLength,
		clearwayWidth, clearwayLength, stopwayLength, lThresh, rThresh;
	protected String leftId, rightId;
	private int rotation;
	
	// Holds runway details that are optional
	/* If any are null the cleared and/or graded areas may not be shown
	 * on the view
	 * setting repaint() method should be called
	 */
	private Double clearedWidth, gradedWidth, endToFullCleared;
	
	// Colors for views
	protected Color backgroundC, runwayC, clearwayC, stopwayC,
		todaC, asdaC, toraC, ldaC,
		leftRightArrowC, rightLeftArrowC,
		clearedWidthC, gradedWidthC,
		obstacleC, stopwaySecondaryC,
		runwayMarkingsC, displacedThresholdC,
		backgroundGrassC, compassC;
	
	// Runway parameters to be displayed
	/* If any parameters are null, they will not be displayed on
	 * the view. After using setter methods repaint() should be called. */
	private Double toraL, asdaL, todaL, ldaL,
		toraR, asdaR, todaR, ldaR;
	
	// Obstacle parameters to be displayed
	/* If any are set to null then the obstacle will not be displayed */
	private Double obsWidth, obsDepth, leftDistance, centerLineDist;
	
	// Holds height and width of windows after last repaint call
	private int height, width;
	
	// Defines if to draw rotated
	private boolean rotated, obsLeft;
	
	// Holds text values to display for complement runway
	private String ldaTextComp, toraTextComp, todaTextComp, asdaTextComp, displacementTextComp;
	
	public TopDownRunway(double runwayWidth, double runwayLength, double clearwayWidth,
						 double clearwayLength, double stopwayLength, String leftId, 
						 String rightId, double lThresh, double rThresh, boolean rotated)
	{
		this.runwayWidth = runwayWidth;
		this.runwayLength = runwayLength;
		this.clearwayWidth = clearwayWidth;
		this.clearwayLength = clearwayLength;
		this.stopwayLength = stopwayLength;
		this.lThresh = lThresh;
		this.rThresh = rThresh;
		
		this.leftId = leftId;
		this.rightId = rightId;
		
		this.backgroundC = new Color(150, 253, 138);
		backgroundGrassC = new Color(12, 183, 0, 60);
		this.runwayC = new Color(198, 198, 198, 255);
		this.runwayMarkingsC = Color.WHITE;
		this.clearwayC = new Color(34, 20, 164, 120);
		this.stopwayC = new Color(242, 242, 242, 255);
		this.stopwaySecondaryC = new Color(255, 255, 255, 80);
		this.displacedThresholdC = Color.BLACK;
		
		this.todaC = Color.black;
		this.asdaC = Color.black;
		this.toraC = Color.black;
		this.ldaC = Color.black;

		this.leftRightArrowC = Color.black;
		this.rightLeftArrowC = Color.black;
		
		this.clearedWidthC = new Color(34, 20, 164, 75);
		this.gradedWidthC = new Color(111, 0, 158, 75);
		
		this.obstacleC = new Color(255, 0, 0, 255);
		
		this.compassC = new Color(0, 0, 0);
		
		this.toraL = null;
		this.asdaL = null;
		this.todaL = null;
		this.ldaL = null;
		
		this.toraR = null;
		this.asdaR = null;
		this.todaR = null;
		this.ldaR = null;
		
		this.endToFullCleared = null;
		this.clearedWidth = null;
		this.gradedWidth = null;
		
		obsWidth = null;
		obsDepth = null;
		leftDistance = null;
		centerLineDist = null;
		
		ldaTextComp = "LDA";
		toraTextComp = "TORA";
		todaTextComp = "TODA";
		asdaTextComp = "ASDA";
		displacementTextComp = "Displacement";
		
		this.rotated = rotated;
		obsLeft = true;
		rotation = 0;
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		if(rotated)
		{
			Graphics2D g2 = (Graphics2D) g;
			double angle = Math.toRadians(rotation);
			
			width = getWidth();
			height = getHeight();
			
			AffineTransform at = g2.getTransform();
			genBackground(g);
			
			g2.rotate(angle, width/2, height/2);
			
			endBuffer = 100 + (width - height)/2;
			
			genClearGraded(g);
			genRunway(g);
			genParams(g);
			genObstacle(g);
			
			g2.setTransform(at);
			genCompass(g);

		}
		else
		{
			width = getWidth();
			height = getHeight();
			endBuffer = 100;
			
			genBackground(g);
			genClearGraded(g);
			genRunway(g);
			genParams(g);
			genObstacle(g);
		}
	}
	
	// Method to generate the background for the top down view
	private void genBackground(Graphics g)
	{
		
		// Sets colour of background to predifined value
		g.setColor(backgroundC);
		// Created the background 
		g.fillRect(0,  0, width, height);
		
		// Sets the colour for the grass texture
		g.setColor(backgroundGrassC);
		
		// Randomly generates grass blades to add to the view 
		Random rand = new Random();
		for(int x = 0; x < width; x+=2)
		{
			for(int y = 0; y < height; y+=2)
			{
				if(rand.nextInt(5) == 0)
				{
					g.drawArc(x, y, rand.nextInt(30), rand.nextInt(30), rand.nextInt(20), rand.nextInt(20));
				}
			}
		}
	}
	
	/* Draws a compass on the view */
	private void genCompass(Graphics g)
	{
		g.setColor(compassC);
		genArrow(g, width - 50, 150, width - 50, 50, "", 10, 0);
		
		g.setFont(g.getFont().deriveFont(30f));
		drawMessage((Graphics2D) g, "N", width - 50, 170);
	}
	
	/* Produces runway on screen taking into account the
	 * clearway and stopway sizes
	 */
	private void genRunway(Graphics g)
	{
		genClearway(g);
		
		g.setColor(runwayC);
		g.fillRect(endBuffer + getPixelConverted(clearwayLength - stopwayLength),
				(height / 2) - (getPixelConverted(runwayWidth) / 2), 
				getPixelConverted(runwayLength), getPixelConverted(runwayWidth));
		
		int threshold = 3;
		
		for(int x = endBuffer + getPixelConverted(clearwayLength) + threshold; 
				x < width - (endBuffer + getPixelConverted(clearwayLength) + threshold + 5); x+=10)
		{
			g.setColor(runwayMarkingsC);
			g.drawLine(x, height/2, x+5, height/2);
		}
		
		
		genStopway(g);
	}
	
	/* Methods takes an integer and converts it to a pixel id on the screen
	 * relative to the screen size, borders and other runway sizes
	 */
	private int getPixelConverted(double val)
	{
		double total = (clearwayLength * 2) + runwayLength - (2 * stopwayLength);
		double converterVal = (width - 2 * endBuffer) / total;
		
		return (int) (val * converterVal);
	}
	
	// Generates the clearway area of the runway
	private void genClearway(Graphics g)
	{
		
		// Sets the colour to the predefined colour
		g.setColor(clearwayC);
		
		// Draws the clearway
		g.fillRect(endBuffer, 
				Math.round((height - getPixelConverted(clearwayWidth)) / 2), 
				Math.round(getPixelConverted(clearwayLength)), 
						Math.round(getPixelConverted(clearwayWidth)));
		g.fillRect(width - endBuffer - getPixelConverted(clearwayLength), 
				(height - getPixelConverted(clearwayWidth)) / 2, 
				getPixelConverted(clearwayLength), 
				getPixelConverted(clearwayWidth));
	}
	
	// Method that generates stopway
	private void genStopway(Graphics g)
	{
		// Line markings on the stopway
		int numLines = 5;
		int threshold = 5;
		
		final int startY = ((height / 2) - (getPixelConverted(runwayWidth) / 2)) + threshold;
		final int endY = startY + getPixelConverted(runwayWidth) - 2*threshold;
		
		/* If lines are so small they cannot be seen
		 * then doesn't attempt to draw
		 */
		if((endY - startY) / numLines == 0)
		{
			return;
		}
		
		// Left runway
		int startX = endBuffer + (getPixelConverted(clearwayLength) - getPixelConverted(stopwayLength)) + threshold;
		int xWidth = getPixelConverted(stopwayLength) - (3 * threshold);
		
		g.setColor(stopwaySecondaryC);
		g.fillRect(startX - threshold, startY - threshold, xWidth + (2*threshold), endY - startY + 2*threshold);
		
		g.setColor(stopwayC);
		for(int y = startY; y < endY; y+= (endY - startY) / numLines)
		{
			g.fillRect(startX, y, 
					xWidth, (endY - startY) / (numLines * 2));
		}
		
		// Right runway
		startX = width - (endBuffer + getPixelConverted(clearwayLength)) + threshold;
		xWidth = getPixelConverted(stopwayLength) - (2 * threshold);
		
		g.setColor(stopwaySecondaryC);
		g.fillRect(startX - threshold, startY - threshold, xWidth + (2*threshold), endY - startY + 2*threshold);
		
		g.setColor(stopwayC);
		for(int y = startY; y < endY; y+= (endY - startY) / numLines)
		{
			g.fillRect(startX, y, xWidth, 
					(endY - startY) / (numLines * 2));
		}
	}
	
	/* Adds to display the view including a to-scale arrow
	 * of TORA, TODA, etc. If global variable null corresponding
	 * to TORA/TODA/etc then will not draw allowing for custom view
	 */
	private void genParams(Graphics g)
	{
		boolean rightArrow = false, leftArrow = false;
		int arrowSize = 3;
		float textSize = 14f;
		
		double transition = (height/2 - runwayWidth/2) / 6;
		double upperY = transition;
		
		// Left to right
		if(todaR != null)
		{
			rightArrow = true;
			
			int startX = 0, endX = 0;
			if(obsLeft)
			{
				endX = width - endBuffer;
				startX = endX - getPixelConverted(todaR);
			}
			else
			{
				startX = endBuffer + getPixelConverted(clearwayLength);
				endX = startX + getPixelConverted(todaR);
			}
			
			g.setColor(todaC);
			genArrow(g, startX, upperY, endX, upperY, getTodaText(), arrowSize, textSize);
			upperY += transition;
		}
		if(asdaR != null)
		{
			rightArrow = true;
			
			int startX = 0, endX = 0;
			if(obsLeft)
			{
				endX = width - (endBuffer + getPixelConverted(clearwayLength - stopwayLength));
				startX = endX - getPixelConverted(asdaR);
			}
			else
			{
				startX = endBuffer + getPixelConverted(clearwayLength);
				endX = startX + getPixelConverted(asdaR);
			}
			
			g.setColor(asdaC);
			genArrow(g, startX, upperY, endX, upperY, getAsdaText(), arrowSize, textSize);
			upperY += transition;
		}
		if(toraR != null)
		{
			rightArrow = true;
			
			int startX = 0, endX = 0;
			if(obsLeft)
			{
				endX = width - (endBuffer + getPixelConverted(clearwayLength));
				startX = endX - getPixelConverted(toraR);
			}
			else
			{
				startX = endBuffer + getPixelConverted(clearwayLength);
				endX = startX + getPixelConverted(toraR);
			}
			
			g.setColor(toraC);
			genArrow(g, startX, upperY, endX, upperY, getToraText(), arrowSize, textSize);
			upperY += transition;
		}
		if(ldaR != null)
		{
			rightArrow = true;
		
			int startX = 0, endX = 0;
			if(obsLeft)
			{
				endX = width - (endBuffer + getPixelConverted(clearwayLength));
				startX = endX - getPixelConverted(ldaR);
			}
			else
			{
				startX = endBuffer + getPixelConverted(clearwayLength + lThresh);
				endX = startX + getPixelConverted(ldaR);
			}
			
			g.setColor(displacedThresholdC);
			((Graphics2D) g).setStroke(new BasicStroke(2));
			g.drawLine(startX, (int) upperY, startX, height/2 + getPixelConverted(runwayWidth/2));
			drawMessage((Graphics2D) g, getDisplacementText(), startX, 
					upperY + transition/2);
			
			g.setColor(ldaC);
			genArrow(g, startX, upperY, endX, upperY, getLdaText(), arrowSize, textSize);
			
			upperY += transition;
		}
		
		int lowerY = (int) (height - transition);
		
		// Right to left
		if(todaL != null)
		{
			leftArrow = true;
			
			int startX = 0, endX = 0;
			if(!obsLeft)
			{
				endX = endBuffer;
				startX = endX + getPixelConverted(todaL);
			}
			else
			{
				startX = width - (endBuffer +  getPixelConverted(clearwayLength));
				endX = startX - getPixelConverted(todaL);
			}
			
			g.setColor(todaC);
			genArrow(g, startX, lowerY, endX, lowerY, todaTextComp, arrowSize, textSize);
			lowerY -= transition;
		}
		if(asdaL != null)
		{
			leftArrow = true;
			
			int startX = 0, endX = 0;
			if(!obsLeft)
			{
				endX = (endBuffer + getPixelConverted(clearwayLength - stopwayLength));
				startX = endX + getPixelConverted(asdaL);
			}
			else
			{
				startX = width - (endBuffer + getPixelConverted(clearwayLength));
				endX = startX - getPixelConverted(asdaL);
			}
			
			g.setColor(asdaC);
			genArrow(g, startX, lowerY, endX, lowerY, asdaTextComp, arrowSize, textSize);
			lowerY -= transition;
		}
		if(toraL != null)
		{
			leftArrow = true;
			
			int startX = 0, endX = 0;
			if(!obsLeft)
			{
				endX = endBuffer + getPixelConverted(clearwayLength);
				startX = endX + getPixelConverted(toraL);
			}
			else
			{
				startX = width - (endBuffer + getPixelConverted(clearwayLength));
				endX = startX - getPixelConverted(toraL);
			}
			
			g.setColor(toraC);
			genArrow(g, startX, lowerY, endX, lowerY, toraTextComp, arrowSize, textSize);
			lowerY -= transition;
		}
		if(ldaL != null)
		{
			leftArrow = true;
			
			int startX = 0, endX = 0;
			if(!obsLeft)
			{
				endX = endBuffer + getPixelConverted(clearwayLength);
				startX = endX + getPixelConverted(ldaL);
			}
			else
			{
				startX = width - (endBuffer + getPixelConverted(clearwayLength + rThresh));
				endX = startX - getPixelConverted(ldaL);
			}
			
			g.setColor(displacedThresholdC);
			((Graphics2D) g).setStroke(new BasicStroke(2));
			g.drawLine(startX+1, lowerY, startX+1, height/2 - getPixelConverted(runwayWidth/2));
			drawMessage((Graphics2D) g, displacementTextComp, startX + 1, 
					lowerY - transition/2);
			
			g.setColor(ldaC);
			genArrow(g, startX, lowerY, endX, lowerY, ldaTextComp, arrowSize, textSize);
			lowerY -= transition;
		}
		
		// Right arrow
		if(rightArrow)
		{
			g.setColor(leftRightArrowC);
			genArrow(g, endBuffer - 100, height/2, endBuffer - 20, height/2, leftId, 8, 16);
		}
		// Left arrow
		if(leftArrow)
		{
			g.setColor(rightLeftArrowC);
			genArrow(g, width - (endBuffer - 100), height/2, width-(endBuffer - 20), height/2, rightId, 8, 16);
		}
	}
	
	// Method to generate the cleared area for the top down view
	private void genClearGraded(Graphics g)
	{
		if(gradedWidth != null)
		{
			// Sets the colour for the graded area
			g.setColor(gradedWidthC);
			g.fillRect(endBuffer,
					 (height/2) - getPixelConverted(gradedWidth), 
					 width -(2*endBuffer), 
					 2 * getPixelConverted(gradedWidth));
		}
		
		if(clearedWidth != null && endToFullCleared != null) {
			g.setColor(clearedWidthC);
			
			g.fillRect(endBuffer + getPixelConverted(clearwayLength + endToFullCleared - stopwayLength), 
					(height/2) - getPixelConverted(clearedWidth/2), 
					width - 2*(endBuffer + getPixelConverted(clearwayLength - stopwayLength + endToFullCleared)) , 
					2 * getPixelConverted(clearedWidth/2));
		
			g.fillRect(endBuffer + getPixelConverted(clearwayLength), 
					(height/2) - getPixelConverted(clearwayWidth/2), 
					getPixelConverted(endToFullCleared - stopwayLength), 
					getPixelConverted(clearwayWidth));
		
			g.fillRect(width - (endBuffer + getPixelConverted(clearwayLength + endToFullCleared - stopwayLength)), 
					(height/2) - getPixelConverted(clearwayWidth/2), 
					getPixelConverted(endToFullCleared - stopwayLength), 
					getPixelConverted(clearwayWidth));
			
			//Top left triangle
			int xPolyTL[] = {endBuffer + getPixelConverted(clearwayLength), 
					endBuffer + getPixelConverted(endToFullCleared - stopwayLength + clearwayLength),
					endBuffer + getPixelConverted(endToFullCleared - stopwayLength + clearwayLength)};
			int yPolyTL[] = {(height/2)-getPixelConverted(clearwayWidth/2),
					(height/2) - getPixelConverted(clearedWidth/2),
					(height/2) - getPixelConverted(clearwayWidth/2)};
			g.fillPolygon(new Polygon(xPolyTL, yPolyTL, xPolyTL.length));
			
			//Bottom left triangle
			int xPolyBL[] = {endBuffer + getPixelConverted(clearwayLength), 
					endBuffer + getPixelConverted(endToFullCleared - stopwayLength + clearwayLength),
					endBuffer + getPixelConverted(endToFullCleared - stopwayLength + clearwayLength)};
			int yPolyBL[] = {(height/2) + getPixelConverted(clearwayWidth/2),
					(height/2) + getPixelConverted(clearedWidth/2),
					(height/2) + getPixelConverted(clearwayWidth/2)};
			g.fillPolygon(new Polygon(xPolyBL, yPolyBL, xPolyBL.length));
			
			//Top right triangle
			int xPolyTR[] = {width - (endBuffer + getPixelConverted(clearwayLength)), 
					width - (endBuffer + getPixelConverted(clearwayLength + endToFullCleared - stopwayLength)),
					width - (endBuffer + getPixelConverted(clearwayLength + endToFullCleared - stopwayLength))};
			int yPolyTR[] = {(height/2)-getPixelConverted(clearwayWidth/2),
					(height/2) - getPixelConverted(clearedWidth/2),
					(height/2) - getPixelConverted(clearwayWidth/2)};
			g.fillPolygon(new Polygon(xPolyTR, yPolyTR, xPolyTR.length));
			
			//Bottom right triangle
			int xPolyBR[] = {width - (endBuffer + getPixelConverted(clearwayLength)), 
					width - (endBuffer + getPixelConverted(endToFullCleared - stopwayLength + clearwayLength)),
					width - (endBuffer + getPixelConverted(endToFullCleared - stopwayLength + clearwayLength))};
			int yPolyBR[] = {(height/2) + getPixelConverted(clearwayWidth/2),
					(height/2) + getPixelConverted(clearedWidth/2),
					(height/2) + getPixelConverted(clearwayWidth/2)};
			g.fillPolygon(new Polygon(xPolyBR, yPolyBR, xPolyBR.length));
		}
	}
	
	// Generates the obstacle on the runway
	private void genObstacle(Graphics g)
	{
		// Checks that the obstacle has some dimensions
		if(obsWidth != null && obsDepth != null && leftDistance != null && centerLineDist != null)
		{
			// Sets the obstacle colour
			g.setColor(obstacleC);
			// Draws the obstacle on the runway
			g.fillRect(endBuffer + getPixelConverted(clearwayLength + leftDistance),
					(height/2) - getPixelConverted(centerLineDist - obsDepth/2), 
					getPixelConverted(obsWidth), getPixelConverted(obsDepth));
		}
	}
	
	/* SETTERS FOR RUNWAY TODA, ASDA, TORA, LDA */
	public void setToda(Double todaL, Double todaR)
	{
		this.todaL = todaR;
		this.todaR = todaL;
	}
	
	// Sets the asda for the runway
	public void setAsda(Double asdaL, Double asdaR)
	{
		this.asdaL = asdaR;
		this.asdaR = asdaL;
	}
	
	// Sets the tora for the runway
	public void setTora(Double toraL, Double toraR)
	{
		this.toraL = toraR;
		this.toraR = toraL;
	}
	
	// Method to set the lda for the runway
	public void setLda(Double ldaL, Double ldaR)
	{
		this.ldaL = ldaR;
		this.ldaR = ldaL;
	}
	
	// Method to set the cleared and graded areas
	public void setClearedGraded(Double endToFullCleared, Double widthFullCleared, Double widthFullGraded)
	{
		this.clearedWidth = widthFullCleared;
		this.gradedWidth = widthFullGraded;
		this.endToFullCleared = endToFullCleared;
	}
	
	// Method to set the obstacle for the runway
	public void setObstacle(Double obsWidth, Double obsDepth, Double leftDistance, Double centerLineDist, boolean obsLeft)
	{
		this.obsWidth = obsDepth;
		this.obsDepth = obsWidth;
		this.leftDistance = leftDistance;
		this.centerLineDist = centerLineDist;
		this.obsLeft = obsLeft;
	}
	
	// Method to set the orientation of the runway
	public void setOrientation(int rot)
	{
		this.rotation = rot;
	}
	
	public void setTextComp(String ldaText, String toraText,
			String todaText, String asdaText, String displacementText)
	{
		if(ldaText == null)
		{
			ldaTextComp = "LDA";
		}
		else
		{
			this.ldaTextComp = ldaText;
		}
		
		if(toraText == null)
		{
			toraTextComp = "TORA";
		}
		else
		{
			this.toraTextComp = toraText;
		}
		
		if(todaText == null)
		{
			todaTextComp = "TODA";
		}
		else
		{
			this.todaTextComp = todaText;
		}
		
		if(asdaText == null)
		{
			asdaTextComp = "ASDA";
		}
		else
		{
			this.asdaTextComp = asdaText;
		}
		
		if(displacementText == null)
		{
			displacementTextComp = "Displacement";
		}
		else
		{
			this.displacementTextComp = displacementText;
		}	
	}
}