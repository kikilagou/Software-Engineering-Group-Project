package Model;

public class Runway
{
	/**
	 * 
	 * 
	 * Do Not Modify Class 
	 * Without Authorization
	 * From All Group Members
	 * 
	 * ASHER WILL CASTRATE YOU
	 * 
	 * 
	 */
	
	private String id, remarks;
	private int heading;
	private char directionChar;
	private double runwayLength, runwayWidth,
		tora, toda, asda, lda,
		thresholdDisplacement, 
		stopwayLength, clearwayWidth,
		clearwayLength, stripEnd, resa,
		clearedArea, gradedArea,
		endToFullCleared;
	private Runway complement;
	private Obstacle obstacle;
	
	public Runway(String id, String remarks, int heading, char directionChar,
			double runwayLength, double runwayWidth, double tora, double toda,
			double asda, double lda, double thresholdDisplacement,
			double stopwayLength, double clearwayWidth, double clearwayLength) {
		this.id = id;
		this.remarks = remarks;
		this.heading = heading;
		this.directionChar = directionChar;
		this.runwayLength = runwayLength;
		this.runwayWidth = runwayWidth;
		this.tora = tora;
		this.toda = toda;
		this.asda = asda;
		this.lda = lda;
		this.thresholdDisplacement = thresholdDisplacement;
		this.stopwayLength = stopwayLength;
		this.clearwayWidth = clearwayWidth;
		this.clearwayLength = clearwayLength;
		
		stripEnd = 60d;
		resa = 240d;
		clearedArea = 105d;
		gradedArea = 150d;
		endToFullCleared = 300d;
		
		complement = null;
		obstacle = null;
	}

	public String getId() {
		return id;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getHeading() {
		return heading;
	}

	public char getDirectionChar() {
		return directionChar;
	}

	public double getRunwayLength() {
		return runwayLength;
	}

	public double getRunwayWidth() {
		return runwayWidth;
	}

	public double getTora() {
		return tora;
	}

	public double getToda() {
		return toda;
	}

	public double getAsda() {
		return asda;
	}

	public double getLda() {
		return lda;
	}

	public double getThresholdDisplacement() {
		return thresholdDisplacement;
	}

	public double getStopwayLength() {
		return stopwayLength;
	}

	public double getClearwayWidth() {
		return clearwayWidth;
	}

	public double getClearwayLength() {
		return clearwayLength;
	}

	public double getStripEnd() {
		return stripEnd;
	}
	
	public double getResa() {
		return resa;
	}
	
	public double getClearedArea()
	{
		return clearedArea;
	}
	
	public double getGradedArea()
	{
		return gradedArea;
	}
	
	public double getEndToFullCleared()
	{
		return endToFullCleared;
	}

	public Runway getComplement() {
		return complement;
	}

	public void setComplement(Runway complement) {
		this.complement = complement;
	}

	public Obstacle getObstacle() {
		return obstacle;
	}

	public void setObstacle(Obstacle obstacle) {
		this.obstacle = obstacle;
	}
	
	public String toString()
	{
		return String.valueOf(this.heading) + String.valueOf(directionChar);
	}
}
