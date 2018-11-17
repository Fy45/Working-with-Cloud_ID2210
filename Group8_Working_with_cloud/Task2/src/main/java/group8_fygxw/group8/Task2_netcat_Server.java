package group8_fygxw.group8;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Task2_netcat_Server {


	    private static Socket connection;
	    private static BufferedReader fromClient;

	    public static Scanner sc = new Scanner(System.in);
	    
		private static boolean Download() throws Exception {
	        DataOutputStream toClient = new DataOutputStream(connection.getOutputStream());
	        toClient.writeBytes(sc.useDelimiter("\n").next());
	        return true;
	    }

	
	    private static boolean Upload() throws Exception {
	        boolean complete = false;
	        if (fromClient == null) {
	            fromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        }
	        String line;
	        while ((line = fromClient.readLine()) != null) {
	            System.out.println(line);
	            complete = true;
	        }
	        return complete;
	    }

	  
	    public static void main(String[] args) throws Exception {
	    	 connection = null;
		        @SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket(8888);
		        boolean finish;
		        while (true) {
		            if (connection == null) {
		                connection = serverSocket.accept();
		            }
		            if (System.in.available() > 0) {
		                finish = Download();
		            } else {
		            	finish = Upload();
		            }
		            if (finish) {
		                break;
		            }
		        }
		        connection.close();
	    }
}
