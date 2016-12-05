package ca.ece.ubc.cpen221.mp5;

public class RestaurantDBServer {
	
	public static void main(String args[]){
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);
		System.out.println(args[3]);
		RestaurantDBSocketServer rdbss = new RestaurantDBSocketServer(Integer.parseInt(args[0]), args[1], args[2], args[3]); 
		rdbss.runDBServer();
	}

}
