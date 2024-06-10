package App;

import java.io.IOException;
import java.net.SocketException;

public class AdminFunctionsThread extends Thread{
	private int port;
	private StoreFront threadStore;
	
	public AdminFunctionsThread(int port, StoreFront threadStore) {
		this.port = port;
		this.threadStore = threadStore;
	}
		/**
		 * Runs the thread to connect to the storefront server
		 */
	public void run() {
		try {
			this.threadStore.initServer();
		}catch(SocketException s) {
		
		}catch (IOException e){
			e.printStackTrace();
		}
	}

}
