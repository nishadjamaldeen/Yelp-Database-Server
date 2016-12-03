package ca.ece.ubc.cpen221.mp5;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class Users {
	
	private String ID;
	private RestaurantDB db;
	private JSONObject user;
	private JSONObject voteRating;
	private Map<String, Integer> votes = new HashMap<String, Integer>();
	
	public Users(String ID, RestaurantDB db){
		this.ID = ID;
		this.db = db;
		this.user = db.getUsers().get(this.ID);
		
		this.voteRating = (JSONObject) user.get("votes");
		votes.put("funny", Integer.parseInt(voteRating.get("funny").toString()));
		votes.put("useful", Integer.parseInt(voteRating.get("useful").toString()));
		votes.put("cool", Integer.parseInt(voteRating.get("cool").toString()));
	}
	
	public String getURL(){
		return user.get("url").toString();
	}
	
	public int getFunnyVotes(){
		return votes.get("funny");
	}
	
	public int getCoolVotes(){
		return votes.get("cool");
	}
	
	public int getUsefulVotes(){
		return votes.get("useful");
	}
	
	public int getReviewCount(){
		return Integer.parseInt(user.get("review_count").toString());
	}
	
	public String getType(){
		return user.get("type").toString();
	}
	
	public String getUserID(){
		return this.ID;
	}
	
	public String getName(){
		return user.get("name").toString();
	}
	
	public double getAverageStars(){
		return Double.parseDouble(user.get("average_stars").toString());
	}
}
