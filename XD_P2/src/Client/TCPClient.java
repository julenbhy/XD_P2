package Client;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

class TCPClient {
	public static void main(String argv[]) throws Exception {

		ArrayList<String> hospitales;
		String answer, file;
		
		System.out.println("Que archivo quieres leer? ");
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		file = inFromUser.readLine();
		
		hospitales = readArrayList(file);
		//añadimos un centinela para que el servidor sepa cuando acaba el mensaje
		hospitales.add("@");
		
		Socket clientSocket = new Socket("localhost", 1234);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		//enviamos en ArrayList por lineas
		for (String str : hospitales)
	    {
			outToServer.writeBytes(str + '\n');
	    }
		
		
		answer = inFromServer.readLine();
		
		System.out.println("FROM SERVER: " + answer);
		clientSocket.close();
	}
	
	
	
	private static ArrayList<String> readArrayList(String file) throws FileNotFoundException
	{
		Scanner s = new Scanner(new File(file));
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()){
		    list.add(s.nextLine());
		}
		s.close();
		return list;
	}
}


