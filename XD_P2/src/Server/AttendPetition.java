package Server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class AttendPetition extends Thread{
	Socket connectionSocket;
	
	public AttendPetition(Socket s){
		this.connectionSocket=s;
	}
	
	public void run(){

		//ArrayList<String> clientSentence = new ArrayList<String>();
		BufferedReader inFromClient;
		//String line;
		
		try {
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			
			/*
			while( (line = inFromClient.readLine() ) != null) {
			    clientSentence.add(line);
			}
			
			int suma=0;
			for (String str : clientSentence)
		    {
				suma += Integer.parseInt(str);
		    }
			
			suma = suma / clientSentence.size();
			
			
			
			clientSentence = inFromClient.readLine();
			
			System.out.println("Received: " + clientSentence);
			
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			*/
			
			 mensaje = inFromClient.readLine();
			System.out.println("mensaje:" + mensaje);

			//outToClient.writeBytes(mensaje);
			
			this.connectionSocket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}