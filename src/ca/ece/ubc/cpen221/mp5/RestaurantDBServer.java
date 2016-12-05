package ca.ece.ubc.cpen221.mp5;

public class RestaurantDBServer {
	
	public static void Main(String args[]){
		RestaurantDBSocketServer rdbss = new RestaurantDBSocketServer(Integer.parseInt(args[0]), args[1], args[2], args[3]); 
		rdbss.runDBServer();
	}

}
