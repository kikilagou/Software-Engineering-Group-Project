package Model;

public class Obstacle
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
	 */
	
	private String id, description;
	private double height, width, depth, 
		distanceFromThresh, centerlineDist;
	
	public Obstacle(double height, double width, double depth,
			double distanceFromThresh, double centerlineDist, 
			String id, String description) 
	{
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.distanceFromThresh = distanceFromThresh;
		this.centerlineDist = centerlineDist;
		this.id = id;
		this.description = description;
	}

	/**
	 * Overloaded constructor for when you are creating a sample obstacle that is on no runway
     */
	public Obstacle(double height, double width, double depth,
					String id, String description)
	{
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.id = id;
		this.description = description;
	}


	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getDepth() {
		return depth;
	}

	public double getDistanceFromThresh() {
		return distanceFromThresh;
	}

	public double getCenterlineDist() {
		return centerlineDist;
	}
}
