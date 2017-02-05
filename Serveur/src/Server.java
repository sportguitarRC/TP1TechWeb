import java.net.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

public class Server {

	static boolean debug_console = false;
	
	public static void StartServeur() throws Exception
	{
		if(debug_console) System.out.println("Entrée dans StartServeur()");
		int noPort = 80;
		try
		{
			ServerSocket serverSocket = new ServerSocket(noPort); // start socket that will accept connexions
			if(debug_console) System.out.println("Écoute sur port" + noPort);
			while(true) //infinite loops
			{
				Socket connexionSocket = serverSocket.accept(); // socket to communicate with one client
				if(debug_console) System.out.println("Connexion établie avec un client");
				PrintWriter print  = new PrintWriter(new OutputStreamWriter(connexionSocket.getOutputStream())); // send data to client
				BufferedReader br = new BufferedReader(new InputStreamReader(connexionSocket.getInputStream())); // receive data from client
				
				String st = br.readLine();  // read first line, with file name
				st = st.substring(5, st.length() - 9);
				String filename;
				if(st.hashCode() == 0 || st == "/" )
					filename = "index.html";
				else filename = st;
				while(st.hashCode() != 0) // listen as long as there are lines transiting
				{
					if(debug_console) System.out.println(st);
					st = br.readLine();
				}
				
				try 
				{
					BufferedReader reader;
					if (new File("Files\\" + filename).exists()) // check if file exists
						{  reader = new BufferedReader(new FileReader("Files\\" + filename));} // Open the file the client asked for
						else { reader = new BufferedReader(new FileReader("Files\\404.html"));} // Return a 404 error

					if(debug_console) System.out.println("Envoi de " + filename);
					
					// HTML PROTOCOL DATA 1.0 SENT TO CLIENT
					print.println("HTTP/1.0 200 OK"); if(debug_console) System.out.println("HTTP/1.0 200 OK");
					Calendar cal = Calendar.getInstance();
					String months[] = {"Jan","Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
					String days [] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
					print.println("Date: " + days[cal.get(Calendar.DAY_OF_WEEK) - 1] + ", " + cal.get(Calendar.DAY_OF_MONTH) + " " + months[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + " EST"); if(debug_console) System.out.println("Date: " + days[cal.get(Calendar.DAY_OF_WEEK) - 1] + ", " + cal.get(Calendar.DAY_OF_MONTH) + " " + months[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + " EST");
					print.println("Server: NCSA/1.5.2"); if(debug_console) System.out.println("Server: NCSA/1.5.2");
					print.println("Mime-Vesion: 1.0"); if(debug_console) System.out.println("Mime-Vesion: 1.0");
					print.println("Content-Type: text/html"); if(debug_console) System.out.println("Content-Type: text/html");
					print.println("Last-Modified: Sun, 5 Feb 2017 11:00:00 EST"); if(debug_console) System.out.println("Last-Modified: Sun, 5 Feb 2017 11:00:00 EST");
					print.println("");
					
					// Send HTML file line by line
				    String line = reader.readLine();
				    while (line != null) 
				    {
				        print.print(line);
				        if(debug_console) System.out.println(line);
				        line = reader.readLine();
			    	}
				    
					reader.close(); // close file reader
					if(debug_console) System.out.println("Fin de l'envoi");
				}
				catch(Exception e)
				{
				    JOptionPane.showMessageDialog(null, e.getMessage());
			    }
				print.flush();
				connexionSocket.close(); // close the specific client socket
				if(debug_console) System.out.println("Connexion avec le client terminée");
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public static void main(String [] args)
	{
		if(debug_console) System.out.println("Entrée dans le main.");
		try
		{
			StartServeur(); // Start the server loop
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		if(debug_console) System.out.println("Fin du main.");
	}
}
