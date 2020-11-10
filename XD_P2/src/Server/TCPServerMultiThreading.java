package Server;

import java.net.*;
import java.io.*;
import java.util.Calendar;

class TCPServerMultiThreading {
	public static void main(String argv[]) throws Exception {
		
		
		ServerSocket welcomeSocket = new ServerSocket(1234);
		
		//creo el bufferedWriter para el fichero log
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("log.txt"));
		BufferedWriter bufWriter = new BufferedWriter(writer);
		bufWriter.write( Calendar.getInstance().getTime().toString() + "  Se estableción conexión con el servidor\n");
		 
		 
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			new AttendPetition(connectionSocket, bufWriter).start();
		}
	}
}