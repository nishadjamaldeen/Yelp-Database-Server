package ca.ece.ubc.cpen221.mp5.statlearning;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public class LeastSquaresRegressionImp implements LeastSquaresRegression {

	@Override
	public double lsrf(RestaurantDB db, String restaurant_id) {
		double a = Algorithms.getAVal();
		double b = Algorithms.getBVal();
		double r_squared = Algorithms.getRsqauredVal();
		double featureValue = Algorithms.getFeatureFunction().getFeature(db, restaurant_id);
		
		double y = a + b*featureValue;
		
		return y;
	}

}
