package ca.ece.ubc.cpen221.mp5;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;

public class Review {
	
	private String ID;
	private JSONObject review;
	private JSONObject voteCategories;
	private RestaurantDB db;
	private Map<String, Integer> votes = new HashMap<String, Integer>();
	
	public Review(String ID, RestaurantDB db){
		this.ID = ID;
		this.db = db;
		this.review = db.getReview().get(this.ID);
		
		this.voteCategories = (JSONObject) review.get("votes");
		votes.put("cool", Integer.parseInt(voteCategories.get("cool").toString()));
		votes.put("funny", Integer.parseInt(voteCategories.get("funny").toString()));
		votes.put("useful", Integer.parseInt(voteCategories.get("useful").toString()));
	}
	
	public String getType(){
		return review.get("type").toString();
	}
	
	public String getBusinessID(){
		return review.get("business_id").toString();
	}
	
	public Set<String> getVoteCategories(){
		return votes.keySet();
	}
	
	public int getCoolVotes(){
		return votes.get("cool");
	}
	
	public int getFunnyVotes(){
		return votes.get("funny");
	}
	
	public int getUsefulVotes(){
		return votes.get("useful");
	}
	
	public String getID(){
		return this.ID;
	}
	
	public String getTextReview(){
		return review.get("text").toString();
	}
	
	public int getStars(){
		return Integer.parseInt(review.get("stars").toString());
	}
	
	public String getUserID(){
		return review.get("user_id").toString();
	}
	
	public String getDate(){
		return review.get("date").toString();
	}

}
