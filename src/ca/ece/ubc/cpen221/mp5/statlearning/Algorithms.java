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

	/**
	 * Return a user rating prediction function
	 * 
	 * @param user_id
	 *            the id of the user we are interested in
	 * @param db
	 *            the database object that represents the yelp dataset
	 * @param featureFunction
	 *            that returns the feature we want to use in making predictions
	 * @return
	 */
	public static LeastSquaresRegression getPredictor(String user_id, RestaurantDB db,
			FeatureFunction featureFunction) {
		// TODO: Implement this method
		return null;
	}

	/**
	 * Returns the best predictor for a user's rating
	 * 
	 * @param user_id
	 *            the user id for the user we are interested in
	 * @param db
	 *            the database object that represents the yelp dataset
	 * @param featureFunctionList
	 *            is a list of feature functions from which the best is selected
	 * @return the best prediction function
	 */
	public static LeastSquaresRegression getBestPredictor(String user_id, RestaurantDB db,
			List<FeatureFunction> featureFunctionList) {
		// TODO: Implement this method
		return null;
	}
}
