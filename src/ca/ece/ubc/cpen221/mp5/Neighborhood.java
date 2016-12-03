package ca.ece.ubc.cpen221.mp5;

public class Neighborhood {
	
	double x;
	double y;
	String name;
	
	
	public Neighborhood(String name) {
		this.name = name;
	}
	
	public Neighbourhood(double x, double y){
		
	}

	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public String getNeighborhoodName() {
		return this.name;
	}
}
