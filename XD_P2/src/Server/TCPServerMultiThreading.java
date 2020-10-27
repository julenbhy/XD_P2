package Server;

import java.net.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

class TCPServerMultiThreading {
	public static void main(String argv[]) throws Exception {
		
		
		ServerSocket welcomeSocket = new ServerSocket(1234);
		
		//creo el bufferedWriter para el fichero log
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("log.txt"));
		BufferedWriter bufWriter = new BufferedWriter(writer);
		
		//obtengo la fecha y hora actual
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now(); 
	    
		bufWriter.write("Se ha abierto el servidor a las " + dtf.format(now));
		
		bufWriter.close();

		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			new AttendPetition(connectionSocket).start();
		}
	}
}