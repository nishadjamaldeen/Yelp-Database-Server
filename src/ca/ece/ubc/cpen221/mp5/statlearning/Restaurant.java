package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Exceptions.PointBoundException;

public class Restaurant {
	
	private double x;
	private double y;
	private int clusterGroup;
	private String name;
	
	public Restaurant(double x, double y, String name){
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setCluster(int cluster){
		this.clusterGroup = cluster;
	}
	
	public int getCluster(){
		return this.clusterGroup; 
	}
	
	public static double getDistance(Restaurant point, Restaurant centroidPoint){
		return Math.sqrt(Math.pow((point.getX() - centroidPoint.getX()),2) + Math.pow((point.getY() - centroidPoint.getY()),2));
	}
	
	public static Restaurant randomLocationPointGenerator(int locationLow, int locationHigh) throws PointBoundException{
		
		if (locationLow - locationHigh > 0)
			throw new PointBoundException("The lower bound is greater than the upper bound");
		
		Random randomFactor = new Random();
		double x = randomFactor.nextDouble()*(locationHigh - locationLow) + locationLow;
		double y = randomFactor.nextDouble()*(locationHigh - locationLow) + locationLow;
		Restaurant randomPoint = new Restaurant(x,y);
		return randomPoint;
	}
	
	public static List generateAllRandomPoints(int locationLow, int locationHigh, int numPoints) throws PointBoundException{
		List<Restaurant> pointList = new ArrayList<Restaurant>();
		
		for (int i = 0; i<numPoints; i++){
			pointList.add(randomLocationPointGenerator(locationLow, locationHigh));
		}
		
		return pointList;
	}
	
	public static List 
	
	public String toString(){
		return "(" + this.x + ", " + this.y + ")";
	}
	
	

}
