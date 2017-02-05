import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;

public class Server {
	
	public static void StartServeur() throws Exception
	{
		int noPort = 80;
		try
		{
			ServerSocket serverSocket = new ServerSocket(noPort);
			while(true) //infinite loops
			{
				Socket connexionSocket = serverSocket.accept();
				PrintWriter print  = new PrintWriter(new OutputStreamWriter(connexionSocket.getOutputStream()));
				BufferedReader br = new BufferedReader(new InputStreamReader(connexionSocket.getInputStream()));
				
				String st = br.readLine();
				while(st.hashCode() != 0) // Lecture tant qu'il reste des lignes dans la réception
				{
					System.out.println(st); // ligne pouvant être rajoutée pour le debug
					st = br.readLine();
				}
				
				System.out.println("Debug");
				try 
				{
					BufferedReader reader = new BufferedReader(new FileReader("Files\\index.html"));
					
					print.println("HTTP/1.0 200 OK");
					print.println("Date: Wed, 10 Sep 2015 14:20:21 GMT");
					print.println("Server: NCSA/1.5.2");
					print.println("Mime-Vesion: 1.0");
					print.println("Content-Type: text/html");
					print.println("Last-Modified: Wed, 10 Sep 2015 14:20:23 GMT");
					print.println("Content-Length: 205");
					print.println("");
					
				    String line = reader.readLine();
			
				    while (line != null) 
				    {
				        print.print(line);
				        System.out.println(line); // ligne pouvant être rajoutée pour le debug
				        line = reader.readLine();
			    	}
					reader.close();
				}
				catch(Exception e)
				{
				    JOptionPane.showMessageDialog(null, e.getMessage());
			    }
				print.flush();
				connexionSocket.close();
			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public static void main(String [] args)
	{
		try
		{
			StartServeur();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}
}
