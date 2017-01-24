import java.net.*;

import javax.swing.JOptionPane;

import java.io.*;

public class Server {
	
	public static void StartServeur() throws Exception
	{
		int noPort = 80;
		
		ServerSocket serverSocket = new ServerSocket(noPort);
		while(true) //infinite loops
		{
			Socket connexionSocket = serverSocket.accept();
			
			connexionSocket.close();
			break;
		}
		
		serverSocket.close();
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
