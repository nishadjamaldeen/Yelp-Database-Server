package ca.ece.ubc.cpen221.mp5;

public class Neighborhood {
	
	double x;
	double y;
	String name;
	RestaurantDB db;
	int clusterNum;
	
	
	public Neighborhood(String name) {
		this.name = name;
	}
	
	public Neighborhood(double x, double y, int clusterNum){
		this.x = x;
		this.y = y;
		this.clusterNum = clusterNum;
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
	
	public void setClusterNum(int clusterNum){
		this.clusterNum = clusterNum;
	}
	
	public int getClusterNum(){
		return this.clusterNum;
	}
}
