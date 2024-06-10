package App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner; 

public class AdminFunctionsClient {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in; 
	private String storeName; 
	
	public static void main(String[] args) throws IOException, InterruptedException{
		AdminFunctionsClient client = new AdminFunctionsClient();
		client.start("127.0.0.1", 12346);
		Scanner scanner = new Scanner(System.in);
		String message;
		boolean keepRunning = true;
		
		while(keepRunning) {
		System.out.println("U: Update products from file");
		System.out.println("R: Retrieve products into file");
		System.out.println("Q: Quit");
		message = scanner.next();
		scanner.nextLine();
			if(message.equals("U")) {
				System.out.print("Enter the name of the file you would like to send: ");
				String fileName = scanner.nextLine();
				message += ":" + fileName;
				client.sendMessage(message);
			}else if (message.equals("R")){
				System.out.print("Enter the name of the file you would like the inventory to be added to: ");
				String fileName = scanner.nextLine();
				message += ":" + fileName;
				client.sendMessage(message);
				
			}else if(message.equals("Q")) {
				client.sendMessage("Q");
				keepRunning = false; 
			}
		}
		
		client.cleanUp();
	
	}
	
	/**
	 * Start the client and server 
	 * 
	 * @param ip
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	
	public void start(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip, port);
		
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		storeName = in.readLine();
        System.out.println("Welcome to the Administration Application for: " + storeName);
	}
	
	/**
	 * Sends message to storefront server
	 * @param msg
	 * @throws IOException
	 */
	public void sendMessage(String msg) throws IOException{
		out.println(msg);
	}
	
	/**
	 * Cleans and closes the client
	 * @throws IOException
	 */
	public void cleanUp() throws IOException{
		in.close();
		out.close();
		clientSocket.close();
	}

}
