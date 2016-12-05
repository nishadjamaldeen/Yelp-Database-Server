package ca.ece.ubc.cpen221.mp5;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	
	private Map<String, JSONObject> restaurantsJSONObjects = new HashMap<String, JSONObject>(); //key restaurantID's, value reviewJSONs 
	private HashMap<String, Restaurant> restaurantObjects = new HashMap<String, Restaurant>();	//key restaurantID's, value restaurantObject
	private ArrayList<String> restaurantIDstringList;											//All the resturantID's
	private HashMap<String, String> restaurantString = new HashMap<String, String>();			//key resturantID's, value string version of all resturantJSON's.
	private ArrayList<String> requiredFieldsRestaurants = new ArrayList<String>();
	
	private Map<String, JSONObject> reviewsJSONObjects = new HashMap<String, JSONObject>();		//key reviewID's, value reviewJSONs
	private HashMap<String, Review> allReviewObjects = new HashMap<String, Review>();			//key reviewID's, reviewObject
	private ArrayList<String> reviewIDstringList = new ArrayList<String>();
	private ArrayList<String> requiredFieldsReviews = new ArrayList<String>();
	private HashMap<String, String> reviewString = new HashMap<String, String>();
	private ArrayList<String> requiredVotesReviews = new ArrayList<String>();
	
	
	private Map<String, JSONObject> usersJSONObjects = new HashMap<String, JSONObject>();		//key userID's, value userJSONs
	private HashMap<String, Users> allUserObjects = new HashMap<String, Users>();				//key userID's, value userObject
	private ArrayList<String> userIDstringList = new ArrayList<String>();
	private ArrayList<String> requiredFieldsUsers = new ArrayList<String>();
	private HashMap<String, String> userString = new HashMap<String, String>();
	
	private Set<String> neighborhoods = new HashSet<String>();									// All names of the neighborhods in the database;
	private ArrayList<String> neighborhoodStrings = new ArrayList<String>();					//ArrayList form of neighborhoods set
	private ArrayList<Neighborhood> neighborhoodObjects = new ArrayList<Neighborhood>();		//Contains all neighborhoodObjects in the database.
	
	
	private static double MAX_X = Double.NEGATIVE_INFINITY;										//Bounds for the clusters.
	private static double MIN_X =  Double.POSITIVE_INFINITY;
	private static double MAX_Y = Double.NEGATIVE_INFINITY;
	private static double MIN_Y = Double.POSITIVE_INFINITY;
	

	/**
	 * Constructs a Restaurant DataBase - RestaurantDB Object.
	 * This database contains information about all resturants, users and reviews.
	 * Business, user and review ID's can be used to access information about each individual restaurant, user and review respectively.
	 * 
	 * @param restaurantJSONfilename - location of the restaurant JSON file
	 * @param reviewsJSONfilename - location of the reviews JSON file
	 * @param usersJSONfilename	- location of the users JSON files
	 * @throws IOException
	 * @throws ParseException
	 */
	public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		
		String restaurantJSONDirectoryFilename = restaurantJSONfilename.substring(2);
		String reviewsJSONDirectoryFilename = reviewsJSONfilename.substring(2);
		String usersJSONDirectoryFilename = usersJSONfilename.substring(2);
		
		
		
		String restaurants = readCompleteFileString(restaurantJSONDirectoryFilename);  //converts each file to a string.
		String reviews = readCompleteFileString(reviewsJSONDirectoryFilename);			//throws IOException.
		String users = readCompleteFileString(usersJSONDirectoryFilename);	
		
		String[] restaurantStrings = restaurants.split("\n");							//creates String Arrays that contain each individual JSON as a String per element of the array.
		String[] reviewsStrings = reviews.split("\n");
		String[] usersStrings = users.split("\n");
		
		ArrayList<String> restaurantID = new ArrayList<String>();						//Lists of all ID's
		ArrayList<String> reviewIDs = new ArrayList<String>();
		ArrayList<String> userIDs = new ArrayList<String>();
		
																						//Checks for a parsing error.
		try{
			for (int i = 0; i< restaurantStrings.length; i++){
				JSONObject restaurant = (JSONObject) parser.parse(restaurantStrings[i]);		//Parses each JSON string into a JSONObject for all resturants.
				
				if (Double.parseDouble(restaurant.get("longitude").toString()) > this.MAX_X) 	//Finds the max and min longitude and latitudes (x,y) values among all restuarants.
					this.MAX_X = Double.parseDouble(restaurant.get("longitude").toString());
		
				if (Double.parseDouble(restaurant.get("longitude").toString()) < this.MIN_X) 
					this.MIN_X = Double.parseDouble(restaurant.get("longitude").toString());
				
				if (Double.parseDouble(restaurant.get("latitude").toString()) > this.MAX_Y) 
					this.MAX_Y = Double.parseDouble(restaurant.get("latitude").toString());
				
				if (Double.parseDouble(restaurant.get("latitude").toString()) < this.MIN_Y) 
					this.MIN_Y = Double.parseDouble(restaurant.get("latitude").toString());
				
				String ID = (String) restaurant.get("business_id");
				
				JSONArray neighbourhoods =  (JSONArray) restaurant.get("neighborhoods");		//Creates a JSONArray that contains all the neighborhoods for each resturant.
				for (int j = 0; j< neighbourhoods.size(); j++){
					this.neighborhoods.add(neighbourhoods.get(j).toString());
				}
				
				restaurantID.add(ID);															//Adds resturant information to data structures.
				restaurantsJSONObjects.put(ID, restaurant);
				this.restaurantString.put(ID, restaurantStrings[i]);
			}
			
			this.restaurantIDstringList = restaurantID;
			
			this.neighborhoodStrings.addAll(neighborhoods);
			
			for(int j = 0; j<neighborhoodStrings.size(); j++) 									//Fills neighborhoodObjects with all the found neighborhoods.
				this.neighborhoodObjects.add(new Neighborhood(neighborhoodStrings.get(j)));
	
			for (int i = 0; i<restaurantID.size(); i++){										//Fills restuarantObjects with all the found resturants.
				restaurantObjects.put(restaurantID.get(i), new Restaurant(restaurantID.get(i), this));
			}
			
			
			double xSum = 0.0;
			double ySum = 0.0;
			int count = 0;
			
			for (int i = 0; i<neighborhoodObjects.size(); i++){									//Finds the mean longitude and latitude (x,y) for all resturants in a particulat neighborhood.
				for (int j = 0; j<restaurantID.size(); j++){
					JSONArray neighborhoodJSONArrayList = (JSONArray) restaurantsJSONObjects.get(restaurantID.get(j)).get("neighborhoods");
					
					for (int k = 0; k < neighborhoodJSONArrayList.size(); k++){
						if(neighborhoodJSONArrayList.get(k).toString().equals(neighborhoodStrings.get(i))){
							xSum += restaurantObjects.get(restaurantID.get(j)).getLongitude();	//Sum of all the longitudes.
							ySum += restaurantObjects.get(restaurantID.get(j)).getLatitude();	//Sum of all the latitudes.
							count++;															//Sum of the resturants in the neighborhood(i).
						}
					}
				}					
				neighborhoodObjects.get(i).setX(xSum/count); 									//Sets the longitude of the neighborhood(i) as the mean longitude.
				neighborhoodObjects.get(i).setY(ySum/count);									//Sets the latidue of the neighborhood(i) as the mean latitude.
			
				count = 0;																		//Reset to 0 for next neighborhood.
				ySum = 0.0;
				xSum = 0.0;
			}
			
																								//Finds and adds any and all review information to the data base.
			for (int i = 0; i<reviewsStrings.length; i++){
				JSONObject review = (JSONObject) parser.parse(reviewsStrings[i]);
				String ID = (String) review.get("review_id");
				reviewIDs.add(ID);
				reviewsJSONObjects.put(ID, review);
				this.allReviewObjects.put(ID, new Review(ID, this));
				this.reviewString.put(ID, reviewsStrings[i]);
			}
			
			this.reviewIDstringList = reviewIDs;
			
																								//Finds and adds any and all user information to the data base.
			for (int i = 0; i<usersStrings.length; i++){
				JSONObject user = (JSONObject) parser.parse(usersStrings[i]);
				String ID = user.get("user_id").toString();
				userIDs.add(ID);
				usersJSONObjects.put(ID, user);
				this.allUserObjects.put(ID, new Users(ID, this));
				this.userString.put(ID, usersStrings[i]);
			}
			
			this.userIDstringList = userIDs;
		
		} catch (ParseException e) {															//JSON must be formatted correctly.
			e.printStackTrace();
		}
		
		
		this.requiredFieldsRestaurants.add("open");
		this.requiredFieldsRestaurants.add("url");
		this.requiredFieldsRestaurants.add("longitude");
		this.requiredFieldsRestaurants.add("neighborhoods");
		this.requiredFieldsRestaurants.add("name");
		this.requiredFieldsRestaurants.add("categories");
		this.requiredFieldsRestaurants.add("state");
		this.requiredFieldsRestaurants.add("type");
		this.requiredFieldsRestaurants.add("city");
		this.requiredFieldsRestaurants.add("full_address");
		this.requiredFieldsRestaurants.add("review_count");
		this.requiredFieldsRestaurants.add("photo_url");
		this.requiredFieldsRestaurants.add("schools");
		this.requiredFieldsRestaurants.add("latitude");
		this.requiredFieldsRestaurants.add("price");
		
		this.requiredFieldsUsers.add("name");
		
		this.requiredFieldsReviews.add("type");
		this.requiredFieldsReviews.add("business_id");
		this.requiredFieldsReviews.add("votes");
		this.requiredFieldsReviews.add("text");
		this.requiredFieldsReviews.add("stars");
		this.requiredFieldsReviews.add("user_id");
		this.requiredFieldsReviews.add("date");
		
		this.requiredVotesReviews.add("cool");
		this.requiredVotesReviews.add("useful");
		this.requiredVotesReviews.add("funny");
	}
	

	/**
	 * Converts a given file into its string representation.
	 * @param address- location of the file to be read.
	 * @return
	 * @throws IOException
	 */
	private static String readCompleteFileString (String address) throws IOException {
	    InputStream stream = new FileInputStream(address);   									// opens file
	    																						// reads each letter into a buffer
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
	 * @return a new business ID not in the database.
	 */
	private String randomIDGeneratorRestaurants() {
		
		Random r = new Random();
		int Low = 10000000;
		int High = 99999999;
		int Result = r.nextInt(High-Low) + Low;
		
		for(int i = 0; i < this.restaurantIDstringList.size(); i++){
			if(String.valueOf(Result).equals(restaurantIDstringList.get(i))){
				i = 0;
				Result = new Random().nextInt(High-Low) + Low;
			}	
		}
		
		return String.valueOf(Result);
	}
	
	/**
	 * 
	 * @return a new user ID not in the database.
	 */
	private String randomIDGeneratorUsers(){
		
		Random r = new Random();
		int Low = 10000000;
		int High = 99999999;
		int Result = r.nextInt(High-Low) + Low;
		
		for(int i = 0; i < this.userIDstringList.size(); i++){
			if(String.valueOf(Result).equals(userIDstringList.get(i))){
				i = 0;
				Result = new Random().nextInt(High-Low) + Low;
			}	
		}
		
		return String.valueOf(Result);
		
	}
	/**
	 * 
	 * @return a new review ID not in the database.
	 */
	private String randomIDGeneratorReviews(){
		
		Random r = new Random();
		int Low = 10000000;
		int High = 999999999;
		int Result = r.nextInt(High-Low) + Low;
		
		for(int i = 0; i < this.reviewIDstringList.size(); i++){
			if(String.valueOf(Result).equals(reviewIDstringList.get(i))){
				i = 0;
				Result = new Random().nextInt(High-Low) + Low;
			}	
		}
		
		return String.valueOf(Result);
	}
	
	private String generateRandomURL(String id) {
		
		String url;
		StringBuilder urlBuild = new StringBuilder();
		
		urlBuild.append("http://www.yelp.com/user_details?userid=");
		urlBuild.append(id);
		
		url = urlBuild.toString();
		
		return url;
	}
	
	/**
	 * keys - business ID's
	 * @return Map with values of all restaurant data in JSON format from the database.
	 */
	public Map<String, JSONObject> getRestaurants(){
		return restaurantsJSONObjects;
	}
	/**
	 * keys - review ID's
	 * @return Map with values of all review data in JSON format from the database.
	 */
	public Map<String, JSONObject> getReview(){
		return reviewsJSONObjects;
	}
	/**
	 * keys - user ID's
	 * @return Map with values of all user data in JSON format from the database.
	 */
	public Map<String, JSONObject> getUsers(){
		return usersJSONObjects;
	}
	/**
	 * 
	 * @return all neighborhood's names from the database.
	 */
	public Set<String> getAllNeighborhoods() {
		return this.neighborhoods;
	}
	/**
	 * 
	 * @return all neighborhood's names from the database.
	 */
	public ArrayList<String> getAllNeighborhoodStrings() {
		return this.neighborhoodStrings;
	}
	/**
	 * 
	 * @return all neighborhood objects from the database.
	 */
	public ArrayList<Neighborhood> getAllNeighborhoodObjects() {
		return this.neighborhoodObjects;
	}
	/**
	 * 
	 * @return the minimum longitide(x) of a resturant in the database.
	 */
	public double getMinX(){
		return this.MIN_X;
	}
	/**
	 * 
	 * @return the minimum latitude(y) of a resturant in the database.
	 */
	public double getMinY(){
		return this.MIN_Y;
	}
	/**
	 * 
	 * @return the maximum longitude(x) of a restaurant in the database.
	 */
	public double getMaxX(){
		return this.MAX_X;
	}
	/**
	 * 
	 * @return the maximum latitude(y) of a restaurant in the database.
	 */
	public double getMaxY(){
		return this.MAX_Y;
	}
	/**
	 * 
	 * @return all the resturantID's in the databse.
	 */
	public ArrayList<String> getRestaurantIDs(){
		return this.restaurantIDstringList;
	}
	/**
	 * 
	 * @return all the resturant objects in the database.
	 */
	public HashMap<String, Restaurant> getRestaurantObjects(){
		return this.restaurantObjects;
	}
	/**
	 * key - User ID's
	 * @return Map with values as all the user objects in the database.
	 */
	public HashMap<String, Users> getUsersObjects(){
		return this.allUserObjects;
	}
	/**
	 * key - review ID's
	 * @return Map with values as all the review objects in the database.
	 */
	public HashMap<String, Review> getReviewObjects() {
		return this.allReviewObjects;
	}
	/**
	 * key - business ID's
	 * @return Map with values as all the resturant objects in the database.
	 */
	public HashMap<String, String> getRestaurantStrings(){
		return this.restaurantString;
	}
	
	public ArrayList<String> getRequiredRestaurantFields() {
		return this.requiredFieldsRestaurants;
	}
	
	/**
	 * Add's a resturant to the database.
	 * r should be in JSON format else a error message is printed.
	 * @param r 
	 * @throws ParseException 
	 */
	public String addRestaurant(String r) throws ParseException{
		JSONParser parser = new JSONParser();
																	//Check to see if r is in JSON format
		try{
			JSONObject restaurant = (JSONObject) parser.parse(r);
		} catch (ParseException e) {
			return "ERR: INVALID_RESTAURANT_STRING";
		}
		
		
		JSONObject restaurant = (JSONObject) parser.parse(r);
																		//Check to see if missing fields.
		
		for(int i = 0; i < this.getRequiredRestaurantFields().size(); i++) {
				if(restaurant.get(this.getRequiredRestaurantFields().get(i)) == null )
					return "ERR: INVALID_RESTAURANT_STRING";
			} 
		
		ArrayList<String> IDs = this.getRestaurantIDs();
		HashMap<String, Restaurant> allRestaurants = this.getRestaurantObjects();
		double x =  Double.parseDouble(restaurant.get("longitude").toString());
		double y = Double.parseDouble(restaurant.get("latitude").toString());
		double xi = 0.0;
		double yi = 0.0;
		
		
		for(int i = 0; i < allRestaurants.size(); i++) {
			xi = allRestaurants.get(IDs.get(i)).getLongitude();
			yi = allRestaurants.get(IDs.get(i)).getLatitude();
			
			if(xi == x && yi == y)
				return "ERR: DUPLICATE_RESTAURANT";
		}
		
		
		
		
		String newID = this.randomIDGeneratorRestaurants();
		
		StringBuilder addFields = new StringBuilder();
		
		addFields.append(r);
		addFields.deleteCharAt(addFields.length()-1);
		addFields.append(", \"stars\": 0.0, ");
		addFields.append("\"business_id\": ");
		addFields.append("\"");
		addFields.append(newID);
		addFields.append("\"");
		addFields.append("}");
		
		String newR = addFields.toString();
		JSONObject newRestaurant = (JSONObject) parser.parse(newR);
		
		
		
		this.restaurantIDstringList.add(newID);
		
		this.restaurantString.put(newID, newR);
		
		this.restaurantsJSONObjects.put(newID, newRestaurant);
		Restaurant res = new Restaurant(newID, this);
		this.restaurantObjects.put(newID, res);
		
		
		
		
		return addFields.toString();
	}
	
	/**
	 * Adds a new User to the data base. A name must be given in JSON format.
	 * @param u
	 * @return
	 * @throws ParseException
	 */
	public String addUser(String u) throws ParseException {
		
		JSONParser parser = new JSONParser();
																				//Check to see if r is in JSON format
		try{
			JSONObject restaurant = (JSONObject) parser.parse(u);
		} catch (ParseException e) {
			return "ERR: INVALID_USER_STRING";
		}


		JSONObject user = (JSONObject) parser.parse(u);
																				//Check to see if missing fields.

		for(int i = 0; i < this.getRequiredUserFields().size(); i++) {
			if(user.get(this.getRequiredUserFields().get(i)) == null )
				return "ERR: INVALID_USER_STRING";
			}
		
		
		String newID = this.randomIDGeneratorUsers();
		String newURL = this.generateRandomURL(newID);
		
		String newUserString = this.fillNewUser(u, newID, newURL);
		JSONObject newUser = (JSONObject) parser.parse(newUserString);
		
		this.userIDstringList.add(newID);
		
		this.userString.put(newID, newUserString);
		
		this.usersJSONObjects.put(newID, newUser);
		Users us = new Users(newID, this);
		this.allUserObjects.put(newID, us);
		
		return newUserString;

	}
	
	/**
	 * Adds a review to the database.
	 * @param r must be in JSON Format.
	 * @return
	 * @throws ParseException
	 */
	public String addReview(String r) throws ParseException {
		
		JSONParser parser = new JSONParser();
																						//Check to see if r is in JSON format
		try{
			JSONObject review = (JSONObject) parser.parse(r);
		} catch (ParseException e) {
			return "ERR: INVALID_REVIEW_STRING";
		}


		JSONObject review = (JSONObject) parser.parse(r);
																						//Check to see if missing fields.

		for(int i = 0; i < this.requiredFieldsReviews.size(); i++) {
			if(review.get(this.requiredFieldsReviews.get(i)) == null )
				return "ERR: INVALID_REVIEW_STRING";
		} 
		
		JSONObject votes = (JSONObject) review.get("votes");
		
		for(int i = 0; i < this.requiredVotesReviews.size(); i++) {
			if( votes.get(this.requiredVotesReviews.get(i)) == null )
				return "ERR: INVALID_REVIEW_STRING";
		} 
		
		boolean isArestaurant = false;
		
		for(int i = 0; i < this.restaurantIDstringList.size(); i++) {
			if(review.get("business_id").toString().equals(this.restaurantIDstringList.get(i)))
				isArestaurant = true;
		}
		
		if(!isArestaurant)
			return "ERR: NO_SUCH_RESTAURANT";
		
		boolean isAUser = false;
		
		for(int i = 0; i < this.userIDstringList.size(); i++) {
			if(review.get("user_id").toString().equals(this.userIDstringList.get(i)))
				isAUser = true;
		}
		
		if(!isAUser)
			return "ERR: NO_SUCH_USER";
		
		
		String ID = this.randomIDGeneratorReviews();
		
		StringBuilder addFields = new StringBuilder();
		
		addFields.append(r);
		addFields.deleteCharAt(addFields.length()-1);
		addFields.append(", \"review_id\": ");
		addFields.append("\"");
		addFields.append(ID);
		addFields.append("\"");
		addFields.append("}");
		
		String newR = addFields.toString();
		JSONObject newReview = (JSONObject) parser.parse(newR);
		
		
		this.reviewIDstringList.add(ID);
		
		this.reviewString.put(ID, newR);
		
		this.reviewsJSONObjects.put(ID, newReview);
		Review rev = new Review(ID, this);
		this.allReviewObjects.put(ID, rev);
		
		return newR;
	}
	

	/**
	 * 
	 * @return the required fields in a JSON to create a new user in the database.
	 */
	private ArrayList<String> getRequiredUserFields() {
		return this.requiredFieldsUsers;
	}
	
	
	private String fillNewUser(String u, String id, String url) {
		String end;
		
		StringBuilder addToU = new StringBuilder();
		
		addToU.append(u);
		addToU.deleteCharAt(addToU.length()-1);
		addToU.append(", \"user_id\": ");
		addToU.append("\"");
		addToU.append(id);
		addToU.append("\"");
		addToU.append(", \"url\": ");
		addToU.append("\"");
		addToU.append(url);
		addToU.append("\"");
		addToU.append(", \"votes\": {\"funny\": 0, \"useful\": 0, \"cool\": 0}, \"review_count\": 0, \"type\": \"user\", \"average_stars\": 0}");
		
		end = addToU.toString();
		return end;
	}
}
