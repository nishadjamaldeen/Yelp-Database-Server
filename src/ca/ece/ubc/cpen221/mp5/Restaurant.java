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
		
	}
	
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
	
}
