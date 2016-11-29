package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;
import java.util.List;
import ca.ece.ubc.cpen221.mp5.*;

public class Algorithms {

	/**
	 * Use k-means clustering to compute k clusters for the restaurants in the
	 * database.
	 *
	 * @param db
	 * @return
	 */
	public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {
		// TODO: Implement this method
		return null;
	}

	public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
		// TODO: Implement this method
		return null;
	}

	// here we are abusing notation a bit when passing a function to getPredictor. This is a way to pass a function that obtains, say, latitude or longitude of a restaurant. You can improve on this is you like. It is the implementation of getBestPredictor that matters.
	public static LeastSquaresRegression getPredictor(User u, RestaurantDB db, FeatureFunction featureFunction) {
		// TODO: Implement this method
		return null;
	}

	public static LeastSquaresRegression getBestPredictor(User u, RestaurantDB db, List<FeatureFunction> featureFunctionList) {
		// TODO: Implement this method
		return null;
	}
}
