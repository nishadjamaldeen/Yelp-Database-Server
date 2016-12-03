package ca.ece.ubc.cpen221.mp5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class Main {
	
	public static void main (String[] args) throws IOException, ParseException {
		System.out.println("1");
		RestaurantDB db = new RestaurantDB("restaurants.json", "reviews.json", "users.json");
		System.out.println("2");
		Restaurant test = new Restaurant("gclB3ED6uk6viWlolSb_uA", db);
		
		TreeSet<String> allN = new TreeSet<String>(db.getAllNeighborhoods());	
		
		List<String> allNs = new ArrayList<String>();
		allNs.addAll(allN);
		
		for(int i = 0; i<allNs.size(); i++)
			System.out.println(allNs.get(i));
		
		for(int i = 0; i<db.getAllNeighborhoodObjects().size(); i++){
			System.out.println(db.getAllNeighborhoodObjects().get(i).getX());
			System.out.println(db.getAllNeighborhoodObjects().get(i).getY());
		}

		
		
		
	}
	

}
