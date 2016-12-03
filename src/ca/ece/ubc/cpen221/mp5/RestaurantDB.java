package ca.ece.ubc.cpen221.mp5;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// TODO: This class represents the Restaurant Database.
// Define the internal representation and
// state the rep invariant and the abstraction function.

public class RestaurantDB {

	/**
	 * Create a database from the Yelp dataset given the names of three files:
	 * <ul>
	 * <li>One that contains data about the restaurants;</li>
	 * <li>One that contains reviews of the restaurants;</li>
	 * <li>One that contains information about the users that submitted reviews.
	 * </li>
	 * </ul>
	 * The files contain data in JSON format.
	 *
	 * @param restaurantJSONfilename
	 *            the filename for the restaurant data
	 * @param reviewsJSONfilename
	 *            the filename for the reviews
	 * @param usersJSONfilename
	 *            the filename for the users
	 * @throws JSONException 
	 * @throws ParseException 
	 * @throws IOException 
	 */
	
	
	private Map<String, JSONObject> restaurantsJSONObjects = new HashMap<String, JSONObject>();
	private Map<String, JSONObject> reviewsJSONObjects = new HashMap<String, JSONObject>();
	private Map<String, JSONObject> usersJSONObjects = new HashMap<String, JSONObject>();
	private Map<String, Restaurant> restaurantObjects = new HashMap<String, Restaurant>();
	private Set<String> neighborhoods;
	private ArrayList<String> neighborhoodStrings = new ArrayList<String>();
	private ArrayList<Neighborhood> neighborhoodObjects = new ArrayList<Neighborhood>();
	private static double MAX_X = Double.MIN_VALUE;
	private static double MIN_X =  Double.MAX_VALUE;
	private static double MAX_Y = Double.MIN_VALUE;
	private static double MIN_Y = Double.MAX_VALUE;
	
	
	
	public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		
		
		String restaurants = readCompleteFileString("data/" + restaurantJSONfilename);
		String reviews = readCompleteFileString("data/" + reviewsJSONfilename);
		String users = readCompleteFileString("data/" + usersJSONfilename);	
		
		List<String> restaurantID = new ArrayList<String>();
		List<String> reviewIDs = new ArrayList<String>();
		List<String> userIDs = new ArrayList<String>();
		this.neighborhoods = new HashSet<String>();
		
		String[] restaurantStrings = restaurants.split("\n");
		String[] reviewsStrings = reviews.split("\n");
		String[] usersStrings = users.split("\n");
		
		
		try{
			for (int i = 0; i< restaurantStrings.length; i++){
				JSONObject restaurant = (JSONObject) parser.parse(restaurantStrings[i]);
				if (Double.parseDouble(restaurant.get("longitude").toString()) > this.MAX_X) 
					this.MAX_X = Double.parseDouble(restaurant.get("longitude").toString());
				if (Double.parseDouble(restaurant.get("longitude").toString()) < this.MIN_X) 
					this.MIN_X = Double.parseDouble(restaurant.get("longitude").toString());
				if (Double.parseDouble(restaurant.get("latitude").toString()) > this.MAX_Y) 
					this.MAX_Y = Double.parseDouble(restaurant.get("latitude").toString());
				if (Double.parseDouble(restaurant.get("latitude").toString()) < this.MIN_Y) 
					this.MIN_Y = Double.parseDouble(restaurant.get("latitude").toString());
				
				
				String ID = (String) restaurant.get("business_id");
				
				JSONArray neighbourhoods =  (JSONArray) restaurant.get("neighborhoods");
				for (int j = 0; j< neighbourhoods.size(); j++){
					this.neighborhoods.add(neighbourhoods.get(j).toString());
				}
				
				
				restaurantID.add(ID);
				restaurantsJSONObjects.put(ID, restaurant);
			}
			
			this.neighborhoodStrings.addAll(neighborhoods);
			
			for(int j = 0; j<neighborhoodStrings.size(); j++) 
				this.neighborhoodObjects.add(new Neighborhood(neighborhoodStrings.get(j)));
	
			for (int i = 0; i<restaurantID.size(); i++){
				restaurantObjects.put(restaurantID.get(i), new Restaurant(restaurantID.get(i), this));
			}
			
			
			
			double xSum = 0;
			double ySum = 0;
			int count = 0;
			for (int i = 0; i<neighborhoodObjects.size(); i++){
				for (int j = 0; j<restaurantID.size(); j++){
					JSONArray neighborhoodJSONArrayList = (JSONArray) restaurantsJSONObjects.get(restaurantID.get(j)).get("neighborhoods");
					
					for (int k = 0; k < neighborhoodJSONArrayList.size(); k++){
						if(neighborhoodJSONArrayList.get(k).toString().equals(neighborhoodStrings.get(i))){
							xSum += restaurantObjects.get(restaurantID.get(j)).getLongitude();
							ySum += restaurantObjects.get(restaurantID.get(j)).getLatitude();
							count++;
						}
					}
				}					
				neighborhoodObjects.get(i).setX(xSum/count);
				neighborhoodObjects.get(i).setY(ySum/count);
			
				count = 0;
				ySum = 0;
				xSum = 0;
			}
			
			for (int i = 0; i<reviewsStrings.length; i++){
				JSONObject review = (JSONObject) parser.parse(reviewsStrings[i]);
				String ID = (String) review.get("review_id");
				reviewIDs.add(ID);
				restaurantsJSONObjects.put(ID, review);
			}
			
			for (int i = 0; i<usersStrings.length; i++){
				JSONObject user = (JSONObject) parser.parse(usersStrings[i]);
				String ID = (String) user.get("user_id");
				userIDs.add(ID);
				restaurantsJSONObjects.put(ID, user);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	

	
	private static String readCompleteFileString (String address) throws IOException {
	    InputStream stream = new FileInputStream(address);   // open file

	    // read each letter into a buffer
	    StringBuffer buffer = new StringBuffer();
	    while (true) {
	        int ch = stream.read();
	        if (ch < 0) {
	            break;
	        }

	        buffer.append((char) ch);
	    }
	    System.out.println("JOOOOOOOOOOOHN CEEEEENNNNNNNAAA");
	    return buffer.toString();
	    }
	/**
	 * 
	 * @return Map of restaurant data in JSON format
	 */
	public Map<String, JSONObject> getRestaurants(){
		return restaurantsJSONObjects;
	}
	/**
	 * 
	 * @return Map of review data in JSON format
	 */
	public Map<String, JSONObject> getReview(){
		return reviewsJSONObjects;
	}
	/**
	 * 
	 * @return Map of user data in JSON format
	 */
	public Map<String, JSONObject> getUsers(){
		return usersJSONObjects;
	}
	
	public Set<String> getAllNeighborhoods() {
		return this.neighborhoods;
	}
	
	public ArrayList<String> getAllNeighborhoodStrings() {
		return this.neighborhoodStrings;
	}
	
	public ArrayList<Neighborhood> getAllNeighborhoodObjects() {
		return this.neighborhoodObjects;
	}
	
	public double getMinX(){
		return this.MIN_X;
	}
	
	public double getMinY(){
		return this.MIN_Y;
	}
	
	public double getMaxX(){
		return this.MAX_X;
	}
	
	public double getMaxY(){
		return this.MAX_Y;
	}
}
