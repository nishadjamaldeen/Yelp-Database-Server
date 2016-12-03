package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
	
	private int clusterNum;
	private List<Restaurant> clusterPoints;
	private Restaurant clusterCentroid;
	
	public Cluster(int clusterNum){
		this.clusterNum = clusterNum;
		this.clusterPoints = new ArrayList<Restaurant>();
		this.clusterCentroid = null;
	}
	public void setPoints(List points){
		this.clusterPoints = points;
	}
	public List getPoints(){
		return clusterPoints;
	}
	
	public void add(Restaurant point){
		clusterPoints.add(point);
	}
	
	public void setCentroid(Restaurant centroid){
		this.clusterCentroid = centroid;
	}
	
	public Restaurant getCentroid(){
		return this.clusterCentroid;
	}
	
	public int getClusterNum(){
		return this.clusterNum;
	}
	
	public void clearPoints(){
		this.clusterPoints.clear();
	}

}
