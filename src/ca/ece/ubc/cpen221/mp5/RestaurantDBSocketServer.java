package ca.ece.ubc.cpen221.mp5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RestaurantDBSocketServer {
	
	
	
	private int portNumber;
	private String restaurantJSONfile;
	private String reviewJSONfile;
	private String userJSONfile;
	
	ServerSocket server;
	
	public RestaurantDBSocketServer(int portNumber, String restaurantJSONfile, String reviewJSONfile, String userJSONfile){
		this.restaurantJSONfile = restaurantJSONfile;
		this.reviewJSONfile = reviewJSONfile;
		this.userJSONfile = userJSONfile;
		this.portNumber = portNumber;
	}
	
	public void runDBServer(){
		try {
			server = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		while (true){
			try {
				Socket client = server.accept();
				new Thread( new RestaurantDBRunnable(client, this.restaurantJSONfile, this.reviewJSONfile, this.userJSONfile)).start();
			} catch (IOException e) {
				System.out.println("Failed to establish connection with port " + this.portNumber);
			}
		}
	}
	

}
