package ca.ece.ubc.cpen221.mp5;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	
	
	
	public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		
		
		String restaurants = readCompleteFileString("data/" + restaurantJSONfilename);
		String reviews = readCompleteFileString("data/" + reviewsJSONfilename);
		String users = readCompleteFileString("data/" + usersJSONfilename);	
		
		List<String> restaurantID = new ArrayList<String>();
		List<String> reviewIDs = new ArrayList<String>();
		List<String> userIDs = new ArrayList<String>();
		
		String[] restaurantStrings = restaurants.split("\n");
		String[] reviewsStrings = reviews.split("\n");
		String[] usersStrings = users.split("\n");
		
		
		try{
			for (int i = 0; i< restaurantStrings.length; i++){
				JSONObject restaurant = (JSONObject) parser.parse(restaurantStrings[i]);
				String ID = (String) restaurant.get("business_id");
				restaurantID.add(ID);
				restaurantsJSONObjects.put(ID, restaurant);
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
	
	


}
