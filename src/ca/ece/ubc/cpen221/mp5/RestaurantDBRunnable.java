package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

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
			
			while (clientSocket.isConnected()){
				String request = input.readLine();
				String characteristic;
				
				System.out.println(request);
				
				if (request.startsWith("RANDOMREVIEW")){
					characteristic = request.substring(13);
					output.println(this.restaurantDB.getRandomReview(characteristic));
				}
				else if (request.startsWith("GETRESTAURANT")){
					characteristic = request.substring(14);
					System.out.println(this.restaurantDB.getRestaurant(characteristic));
					output.println(this.restaurantDB.getRestaurant(characteristic));
				}
				else if (request.startsWith("ADDUSER")){
					characteristic = request.substring(8);
					output.println(this.restaurantDB.addUser(characteristic));
				}
				else if (request.startsWith("ADDRESTAURANT")){
					characteristic = request.substring(14);
					output.println(this.restaurantDB.addRestaurant(characteristic));
				}
				else if (request.startsWith("ADDREVIEW")){
					characteristic = request.substring(10);
					output.println(this.restaurantDB.addReview(characteristic));
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
