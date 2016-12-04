package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class LongitudeFunction implements FeatureFunction {

	@Override
	public double getFeature(RestaurantDB rdb, String restaurant_id) {
		return (double) rdb.getRestaurantObjects().get(restaurant_id).getLongitude();
	}

}
