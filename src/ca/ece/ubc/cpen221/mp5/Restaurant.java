package ca.ece.ubc.cpen221.mp5;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class Restaurant {
	
	private String ID;
	private RestaurantDB db;
	private JSONObject restaurant;
	private String[] neighbourhoods;
	private String[] categories;
	private String[] schools;
	private String restaurantString;
	private double latitude;
	private double longitude;
	private int clusterGroup;
	
	public Restaurant(String ID, RestaurantDB db){
		this.ID = ID;
		this.db = db;
		this.restaurant = db.getRestaurants().get(ID);
		
		JSONArray neighbourhoods =  (JSONArray) restaurant.get("neighborhoods");
		String[] neighbourhoodStrings = new String[neighbourhoods.size()];
		for (int i = 0; i< neighbourhoods.size(); i++){
			neighbourhoodStrings[i] = neighbourhoods.get(i).toString();
		}
		this.neighbourhoods = neighbourhoodStrings;
		
		JSONArray category =  (JSONArray) restaurant.get("categories");
		String[] categoryStrings = new String[category.size()];
		for (int i = 0; i< category.size(); i++){
			categoryStrings[i] = category.get(i).toString();
		}
		this.categories = categoryStrings;
		
		JSONArray school =  (JSONArray) restaurant.get("schools");
		String[] schoolStrings = new String[school.size()];
		for (int i = 0; i< school.size(); i++){
			schoolStrings[i] = school.get(i).toString();
		}
		this.schools = schoolStrings;
		
		this.latitude = this.getLatitude();
		this.longitude = this.getLongitude();
		
		this.restaurantString = db.getRestaurantStrings().get(ID);
		
	}
	/**
	 * An alternate constructor that will be used to generate cluster centroid points
	 * @param x
	 * @param y
	 */

	
	public boolean isOpen(){
		if(restaurant.get("open").equals("true"))
			return true;
		else
			return false;
	}
	
	public String getURL() {
		return restaurant.get("url").toString();
	}
	
	public double getLongitude(){
		return Double.parseDouble(restaurant.get("longitude").toString());
	}
	
	public double getLatitude(){
		return Double.parseDouble(restaurant.get("latitude").toString());
	}
	
	public String[] getNeighbourhoods(){
		return neighbourhoods;
	}
	
	public String getBusinessID(){
		return this.ID;
	}
	
	public String getName(){
		return restaurant.get("name").toString();
	}
	
	public String[] getCategories(){
		return this.categories;
	}
	
	public String getState(){
		return restaurant.get("state").toString();
	}
	
	public String getType(){
		return restaurant.get("type").toString();
	}
	
	public double getStars(){
		return Double.parseDouble(restaurant.get("stars").toString());
	}
	
	public String getCity(){
		return restaurant.get("city").toString();
	}
	
	public String getFullAddress(){
		return restaurant.get("full_address").toString();
	}
	
	public int getReviewCount(){
		return Integer.parseInt(restaurant.get("review_count").toString());
	}
	
	public String getPhotoURL(){
		return restaurant.get("photo_url").toString();
	}
	
	public String[] getSchools(){
		return this.schools;
	}
	
	public int getPrice(){
		return Integer.parseInt(restaurant.get("price").toString());
	}
	
	public double getX(){
		return this.longitude;
	}
	
	public double getY(){
		return this.latitude;
	}
	
	public static double getDistance(Restaurant point, Neighborhood centroidPoint){
		return Math.sqrt(Math.pow((point.getX() - centroidPoint.getX()),2) + Math.pow((point.getY() - centroidPoint.getY()),2));
	}
	
	public void setCluster(int cluster){
		this.clusterGroup = cluster;
	}
	
	public int getCluster(){
		return this.clusterGroup;
	}
	
	public JSONObject getJSONRestaurant(){
		return this.restaurant;
	}
	
	public String getRestaurantString(){
		return this.restaurantString;
	}
	
	public Double getTypeValue(String ratingType){
		if (ratingType.equals("price"))
			return (double) this.getPrice();
		else if (ratingType.equals("longitude"))
			return this.getLongitude();
		else if (ratingType.equals("latitude"))
			return this.getLatitude();
		else if (ratingType.equals("stars"))
			return this.getStars();
		else
			return 0.0;
	}
	
	
}
