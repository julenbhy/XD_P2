package Client;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

class TCPClient {
	public static void main(String argv[]) throws Exception {

		ArrayList<String> hospitales;
		String answer, file;
		
		System.out.println("Que región quieres leer? ");
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		file = inFromUser.readLine();
		
		if(createFile(file)) createRegion(file);
		
		
		hospitales = readArrayList(file);
		//aÃ±adimos un centinela para que el servidor sepa cuando acaba el mensaje
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
	
	private static void writeArrayList(String file, ArrayList<String> arr) throws IOException
	{
		FileWriter writer = new FileWriter(file); 
		for(String str: arr) {
		  writer.write(str + System.lineSeparator());
		}
		writer.close();
	}
	
	//crea un fichero en el caso de que no existe y devuelve verdadero, si no es necesario devuelve falso
	private static boolean createFile(String file) throws IOException
	{
		File myObj = new File(file);
		if (myObj.createNewFile())return true;
		else return false;
	}
	
	private static void createRegion(String file) throws IOException
	{
		System.out.println("Esta región no existia, introduzca los datos de un hospital separados por espacios ");
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String data = inFromUser.readLine();
		ArrayList<String> list = new ArrayList<String>();
		list=addHospital(data, list);
		
		boolean mas=true;
		while(mas) {
			System.out.println("Desea introducir datos de otro hospital");
			inFromUser = new BufferedReader(new InputStreamReader(System.in));
			String resp = inFromUser.readLine().toLowerCase();
			if(resp.equals("si")) {
				System.out.println("Introduzca los datos de un hospital separados por espacios ");
				inFromUser = new BufferedReader(new InputStreamReader(System.in));
				data = inFromUser.readLine();
				list=addHospital(data, list);
			}
			else mas=false;
		}
		writeArrayList(file, list);
	}
	
	private static ArrayList<String> addHospital(String data, ArrayList<String> list)  
	{
		list.add(data);
		return list;
	}

		      
	
}


