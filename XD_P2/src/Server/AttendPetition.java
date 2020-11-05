package Server;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;

public class AttendPetition extends Thread{
	Socket connectionSocket;
	BufferedWriter bufWriter;
	
	ArrayList<Integer> casos = new ArrayList<Integer>();
	ArrayList<Integer> muertos = new ArrayList<Integer>();
	ArrayList<Integer> uci = new ArrayList<Integer>();
	ArrayList<Integer> bajas = new ArrayList<Integer>();
	
	public AttendPetition(Socket s, BufferedWriter bufWriter){
		this.connectionSocket=s;
		this.bufWriter=bufWriter;
	}
	
	public void run(){

		ArrayList<String> clientSentence = new ArrayList<String>();

		BufferedReader inFromClient;
		String resultado, line;
		
		try {
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			

			//leemos las lineas que se nos envian hasta encontrar el centinela
			while((line = inFromClient.readLine())!=null && !line.equals("@")){
				bufWriter.write( Calendar.getInstance().getTime().toString() + "  Se estableción conexión con un cliente y obtuvo los datos: "+line+"\n");
			    clientSentence.add(line);
			    System.out.println(line);
			    
			    
			}
			
			separarDatos(clientSentence);
			
			resultado = obtenerResultado();
			bufWriter.write( Calendar.getInstance().getTime().toString() + "  El servidor calculo las medias: "+resultado+"\n");

			
			outToClient.writeBytes(resultado + '\n');
			
			bufWriter.write( Calendar.getInstance().getTime().toString() + "  El servidor envió las medias al cliente\n");

			
			
			this.connectionSocket.close();
			bufWriter.write( Calendar.getInstance().getTime().toString() + "  El servidor terminó la conexión con el cliente\n");
			bufWriter.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Ha saltado la excepcion");
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * recibe un ArrayList con los mensajes del cliente y lo separa por campos
	 * @param clientSentence 
	 */
	private void separarDatos(ArrayList<String> clientSentence) {

		String [] aux;
		
		for(String str: clientSentence) {
			aux = str.split(" ");
			this.casos.add(Integer.parseInt(aux[0]));
			this.muertos.add(Integer.parseInt(aux[1]));
			this.uci.add(Integer.parseInt(aux[2]));
			this.bajas.add(Integer.parseInt(aux[3]));
		}
	}
	
	
	/**
	 * procesa el mensaje que hay que devolver al cliente
	 * @return mensaje
	 */
	private String obtenerResultado() {
		String mensaje;
		
		mensaje = (calcularMedia(casos) + " " + calcularMedia(muertos) + " "  + calcularMedia(uci) + " "  + calcularMedia(bajas));
		
		return mensaje;	
	}
	
	
	/**
	 * calcula la media de un ArrayList <Integer>
	 * @param list
	 * @return average
	 */
	private String calcularMedia(ArrayList <Integer> list) {
	  Integer sum = 0;
	  
	  for (Integer elem : list) {
	      sum += elem;
	  }
	  
	  sum =  sum / list.size();
	  return Integer.toString(sum);
	}
}