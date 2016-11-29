package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public interface LeastSquaresRegression {

	/**
	 * Compute a predicted rating given user and restaurant information
	 *
	 * @param db
	 *            the database for the yelp dataset
	 * @param user_id
	 *            the user for whom we are making predictions
	 * @param restaurant_id
	 *            the restaurant for which we would like a predicted rating
	 * @return the predicted rating
	 */
	public double lsrf(RestaurantDB db, String user_id, String restaurant_id);
}
