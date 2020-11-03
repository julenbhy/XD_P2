package Server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class AttendPetition extends Thread{
	Socket connectionSocket;
	
	public AttendPetition(Socket s){
		this.connectionSocket=s;
	}
	
	public void run(){
		ArrayList<String> clientSentence;
		BufferedReader inFromClient;
		
		try {
			inFromClient = new BufferedReader(
				new InputStreamReader(
						connectionSocket.getInputStream()
					)
			);
			DataOutputStream outToClient = 
					new DataOutputStream(
							connectionSocket.getOutputStream()
						);
			
			clientSentence = inFromClient.readLine();
			
			System.out.println("Received: " + clientSentence);
			
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			
			outToClient.writeBytes(capitalizedSentence);
			
			this.connectionSocket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}