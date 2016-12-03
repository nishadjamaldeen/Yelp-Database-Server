package ca.ece.ubc.cpen221.mp5;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class Main {
	
	public static void main (String[] args) throws IOException, ParseException {
		System.out.println("1");
		RestaurantDB db = new RestaurantDB("restaurants.json", "reviews.json", "users.json");
		System.out.println("2");
		Restaurant test = new Restaurant("gclB3ED6uk6viWlolSb_uA", db);
		
		System.out.println(test.getNeighbourhoods()[0]);
		System.out.println(test.getNeighbourhoods()[1]);
		System.out.println(test.getNeighbourhoods().toString());
	}
	

}
