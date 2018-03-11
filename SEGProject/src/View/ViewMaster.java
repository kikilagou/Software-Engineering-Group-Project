package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public abstract class ViewMaster extends JPanel 
{
	public ViewMaster()
	{
		ldaText = "LDA";
		toraText = "TORA";
		todaText = "TODA";
		asdaText = "ASDA";
		displacementText = "Displacement";
	}
	
	// Holds text values to display on gui (eg side on text labels)
	private String ldaText, toraText, todaText, asdaText, displacementText;
	
	protected String getLdaText() {
		return ldaText;
	}

	protected String getToraText() {
		return toraText;
	}

	protected String getTodaText() {
		return todaText;
	}

	protected String getAsdaText() {
		return asdaText;
	}

	protected String getDisplacementText() {
		return displacementText;
	}
	
	/* Methods used to generate an arrow and place some text just above it in the center
	 * of the coordinates inputed as parameters. Also allows setting of thickness in last
	 * parameter. Color to be set before calling method.
	 */
	protected void genArrow(Graphics g, int x, double upperY, int x2, double upperY2, String text, int arrowThickness, float textSize)
	{
		Graphics2D g2 = (Graphics2D) g;
		// Determines thickness of arrows
		g2.setStroke(new BasicStroke(arrowThickness));
		
		double arrowBack = 8d, arrowPerp = 8d;
		
		// Determines angel of the arrow heads
		double angle = Math.atan(((double) (upperY2 - upperY)) / ((double) (x2 - x)));
		
		if(x2 - x < 0)
		{
			angle = angle - Math.PI;
		}
		
		double zX = ((double) x2) - (arrowBack * Math.cos(angle));
		double zY = ((double) upperY2) - (arrowBack * Math.sin(angle));
		
		int l1x = (int) (zX + (arrowPerp * Math.cos(Math.PI/2 - angle)));
		int l1y = (int) (zY - (arrowPerp * Math.sin(Math.PI/2 - angle)));
		
		int l2x = (int) (zX - (arrowPerp * Math.cos(Math.PI/2 - angle)));
		int l2y = (int) (zY + (arrowPerp * Math.sin(Math.PI/2 - angle)));
		
		g.setFont(g.getFont().deriveFont(textSize));
		g2.drawLine(x2, (int) upperY2, l1x,  l1y);
		g2.drawLine(x2, (int) upperY2, l2x, l2y);
		
		g2.drawLine(x, (int) upperY,
				(int) (x2 - ((arrowThickness) * Math.cos(angle))), 
				(int) (upperY2 - ((arrowThickness) * Math.sin(angle))));
		
		if(text!=null)
		{
			g.setColor(Color.black);
			drawMessage((Graphics2D) g, text, x + (x2-x) / 2, (upperY + (upperY2-upperY) / 2) - 15);
		}
	}
	
	/* Draws text on screen. Coordinates given correspond to center
	 * of line of text not top-left
	 */
	protected void drawMessage(Graphics2D g, String message, int x, double d)
	{
		// Calculates text width and height
		int textWidth =  g.getFontMetrics().stringWidth(message);
		int textHeight = g.getFontMetrics().getAscent();
		
		// Sets text colour and draws text
		g.drawString(message, 
				x - textWidth/2,
				(int) (d + textHeight/2));
	}
	
	// Sets text to new values, or if null to a default value
	public void setText(String ldaText, String toraText,
			String todaText, String asdaText, String displacementText)
	{
		if(ldaText == null)
		{
			ldaText = "LDA";
		}
		else
		{
			this.ldaText = ldaText;
		}
		
		if(toraText == null)
		{
			toraText = "TORA";
		}
		else
		{
			this.toraText = toraText;
		}
		
		if(todaText == null)
		{
			todaText = "TODA";
		}
		else
		{
			this.todaText = todaText;
		}
		
		if(asdaText == null)
		{
			asdaText = "ASDA";
		}
		else
		{
			this.asdaText = asdaText;
		}
		
		if(displacementText == null)
		{
			displacementText = "Displacement";
		}
		else
		{
			this.displacementText = displacementText;
		}
		
	}
}
