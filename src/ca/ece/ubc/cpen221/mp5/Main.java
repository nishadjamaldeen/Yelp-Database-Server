package ca.ece.ubc.cpen221.mp5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


import org.json.simple.parser.ParseException;

import ca.ece.ubc.cpen221.mp5.statlearning.Algorithms;
import ca.ece.ubc.cpen221.mp5.statlearning.FeatureFunction;
import ca.ece.ubc.cpen221.mp5.statlearning.PriceFunction;

public class Main {
	
	public static void main (String[] args) throws IOException, ParseException {
		System.out.println("1");
		RestaurantDB db = new RestaurantDB("restaurants.json", "reviews.json", "users.json");
		
		Restaurant test = new Restaurant("gclB3ED6uk6viWlolSb_uA", db);
		
		System.out.println(test.getBusinessID());
		

		
		
		
	}
	

}
