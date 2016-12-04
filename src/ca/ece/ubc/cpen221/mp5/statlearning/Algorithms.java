package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ca.ece.ubc.cpen221.mp5.*;

public class Algorithms {
	
	private static FeatureFunction f;
	private static double aVal;
	private static double bVal;
	private static double r_squaredVal;

	/**
	 * Use k-means clustering to compute k clusters for the restaurants in the
	 * database.
	 *
	 * @param db
	 * @return
	 */
	public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {
		ArrayList<Neighborhood> clusters = new ArrayList<Neighborhood>();
		Random randFactor = new Random();
		
		for (int i = 0; i<k; i++){
			
			double x = db.getMinX() + (db.getMaxX()-db.getMinX())*randFactor.nextDouble();
			double y = db.getMinY() + (db.getMaxY()-db.getMinY())*randFactor.nextDouble();
			
			clusters.add(new Neighborhood(x, y, i+1));
		}
		
		ArrayList<String> restaurantIDs = db.getRestaurantIDs();
		HashMap<String, Restaurant> restaurantObjects = db.getRestaurantObjects();
		double minDistanceToCluster;
		double xSum = 0;
		double ySum = 0;
		int count = 0;
		
		boolean notEquilibrium = true;
		while (notEquilibrium){
			notEquilibrium = false;
			for (int i = 0; i<restaurantIDs.size(); i++){
				minDistanceToCluster = Double.MAX_VALUE;
				for (int j = 0; j<clusters.size(); j++){
					if(Restaurant.getDistance(restaurantObjects.get(restaurantIDs.get(i)), clusters.get(j)) < minDistanceToCluster){
						minDistanceToCluster = Restaurant.getDistance(restaurantObjects.get(restaurantIDs.get(i)), clusters.get(j));
						restaurantObjects.get(restaurantIDs.get(i)).setCluster(clusters.get(j).getClusterNum());	
					}		
				}
			}
			
			for (int i = 0; i<clusters.size(); i++){
				for (int j = 0; j<restaurantObjects.size(); j++){
					if (restaurantObjects.get(restaurantIDs.get(j)).getCluster() == clusters.get(i).getClusterNum()){
						xSum += restaurantObjects.get(restaurantIDs.get(j)).getX();
						ySum += restaurantObjects.get(restaurantIDs.get(j)).getY();
						count++;
					}
				}
				
				if(Math.abs(xSum/count - clusters.get(i).getX()) > 0.001 || Math.abs(ySum/count - clusters.get(i).getY())>0.001)
					notEquilibrium = true;
				
				
				clusters.get(i).setX(xSum/count);
				clusters.get(i).setY(ySum/count);
				
				xSum = 0;
				ySum = 0;
				count = 0;
			}
			
		}
		List<Set<Restaurant>> kMeansClusters = new ArrayList<Set<Restaurant>>();
		for(int h = 0; h<clusters.size(); h++){
			Set<Restaurant> nthCluster = new HashSet<Restaurant>();
			for (int i = 0; i<restaurantIDs.size(); i++){
				
				if(restaurantObjects.get(restaurantIDs.get(i)).getCluster() == clusters.get(h).getClusterNum())
					nthCluster.add(restaurantObjects.get(restaurantIDs.get(i)));
			}
			kMeansClusters.add(nthCluster);
		}
		
		return kMeansClusters;
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
		HashMap<Double, Double> Ratings = new HashMap<Double, Double>();
		String ratingType = new String();
		
		if(featureFunction.getClass().getSimpleName().equals("PriceFunction"))
			ratingType = "price";
		if(featureFunction.getClass().getSimpleName().equals("RatingFunction"))
			ratingType = "stars";
		if(featureFunction.getClass().getSimpleName().equals("LongitudeFunction"))
			ratingType = "longitude";
		if(featureFunction.getClass().getSimpleName().equals("LatitudeFunction"))
			ratingType = "latitude";
		
		ArrayList<Review> allReviews = new ArrayList<Review>(db.getReviewObjects().values());
		ArrayList<Review> reviewsByThisUser = new ArrayList<Review>();
		
		for(int i = 0; i < allReviews.size(); i++) {
			if(allReviews.get(i).getUserID().equals(user_id))
				reviewsByThisUser.add(allReviews.get(i));
		}
		
		ArrayList<Double> allXs = new ArrayList<Double>();
		ArrayList<Double> allYs = new ArrayList<Double>();
		HashMap<String, Restaurant> allRestaurantObjects = db.getRestaurantObjects();
		
		for(int i = 0; i< reviewsByThisUser.size(); i++) {
			String b_ID = reviewsByThisUser.get(i).getBusinessID();
			allXs.add(allRestaurantObjects.get(b_ID).getTypeValue(ratingType));
			allYs.add((double) reviewsByThisUser.get(i).getStars());
			
		}
		
		double xMean = 0;
		double yMean = 0;
		int count = 0;
		
		
		for (int i = 0; i<allXs.size(); i++){
			xMean += allXs.get(i);
			yMean += allYs.get(i);
			count++;
		}
		
		yMean = yMean/count;
		xMean = xMean/count;
		
		double Sxx = 0;
		double Syy = 0;
		double Sxy = 0;
		double b;
		double a;
		double r_squared;
		
		for (int i = 0; i<allXs.size(); i++){
			Sxx += Math.pow((allXs.get(i) - xMean),2);
			Syy += Math.pow((allYs.get(i) - yMean),2);
			Sxy += (allXs.get(i) - xMean)*(allYs.get(i) - yMean);
		}
		b = Sxy/Sxx;
		a = yMean-b*xMean;
		r_squared = Math.pow(Sxy, 2)/(Sxx*Syy);
		

		
		
		aVal = a;
		bVal = b;
		r_squaredVal = r_squared;
		f = featureFunction;
		
		
		return new LeastSquaresRegressionImp();
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
		
		ArrayList<FeatureFunction> featureFunctionArray = new ArrayList<FeatureFunction>(featureFunctionList);
		ArrayList<Double> rSquaredValueList = new ArrayList<Double>();
		
		for (int i = 0; i< featureFunctionArray.size(); i++){
			Algorithms.getPredictor(user_id, db, featureFunctionArray.get(i));
			rSquaredValueList.add(Algorithms.getRsqauredVal());
		}
		
		double bestRValue = 0;
		
		for (int i = 0; i< rSquaredValueList.size(); i++){
			if(Math.abs(rSquaredValueList.get(i) - 1) < Math.abs(bestRValue - 1))
				bestRValue = rSquaredValueList.get(i);
		}
		
		int indexOfBestRVal = rSquaredValueList.indexOf(bestRValue);
		
		Algorithms.getPredictor(user_id, db, featureFunctionArray.get(indexOfBestRVal));
		
		return new LeastSquaresRegressionImp();
	}
	
	public static double getAVal(){
		return aVal;
	}
	
	public static double getBVal(){
		return bVal;
	}
	
	public static double getRsqauredVal(){
		return r_squaredVal;
	}
	
	public static FeatureFunction getFeatureFunction(){
		return f;
	}
}
