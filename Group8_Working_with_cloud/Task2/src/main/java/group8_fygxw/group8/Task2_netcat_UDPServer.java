package group8_fygxw.group8;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class Task2_netcat_UDPServer {

    private static DatagramSocket serverSocket;
    private static DatagramPacket pingPacket;
    private static boolean downloadMode;
    private static boolean pinged;

   
    private static void download() throws Exception {
        if (!pinged) {
            byte[] receiveData = new byte[1024];
            pingPacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(pingPacket);
            pinged = true;
        }
        @SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            byte[] sendData = input.nextLine().getBytes();
            serverSocket.send(new DatagramPacket(sendData, sendData.length, pingPacket.getAddress(), pingPacket.getPort()));
        }
    }

   
    private static void upload() throws Exception {
        byte[] receiveData = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        System.out.println(new String(receivePacket.getData()).trim());
    }

   
    public static void main(String[] args) throws Exception {
    	 serverSocket = new DatagramSocket(9999);
         if (System.in.available() > 0) {
             downloadMode = true;
         }
         while (true) {
             if (downloadMode) {
                 download();
             } else {
                 upload();
             }
         }

    }
}
