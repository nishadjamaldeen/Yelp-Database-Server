package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.parser.ParseException;

public class RestaurantDBRunnable implements Runnable {

	protected Socket clientSocket;
	private String restaurantJSONfile;
	private String reviewsJSONfile;
	private String userJSONfile;
	private RestaurantDB restaurantDB;
	
	public RestaurantDBRunnable(Socket clientSocket, String restaurantJSONfile, String reviewsJSONfile, String userJSONfile){
		this.clientSocket = clientSocket;
		this.userJSONfile = userJSONfile;
		this.restaurantJSONfile = restaurantJSONfile;
		this.reviewsJSONfile = reviewsJSONfile;
	}
	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
			restaurantDB = new RestaurantDB(this.restaurantJSONfile, this.reviewsJSONfile, this.userJSONfile);
			
			while(clientSocket.isConnected()){
				String request = input.readLine();
				String characteristic;
				
				if (request.startsWith("RANDOMREVIEW"))
					characteristic = request.substring(request.indexOf('<')+1, request.lastIndexOf('>'));
				else if (request.startsWith("GETRESTAURANT"))
					characteristic = request.substring(request.indexOf('<')+1, request.lastIndexOf('>'));
				else if (request.startsWith("ADDUSER"))
					characteristic = request.substring(request.indexOf('<')+1, request.lastIndexOf('>'));
				else if (request.startsWith("ADDRESTAURANT"))
					characteristic = request.substring(request.indexOf('<')+1, request.lastIndexOf('>'));
				else if (request.startsWith("ADDREVIEW"))
					characteristic = request.substring(request.indexOf('<')+1, request.lastIndexOf('>'));	
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
