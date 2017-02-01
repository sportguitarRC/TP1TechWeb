import java.net.*;
import java.io.*;
import javax.swing.*;
public class Client {

	public static void StartClient() throws Exception
	{
		ObjectOutputStream out;
		ObjectInputStream in;
		
		int port = 80;
		String message;
		String adresse = JOptionPane.showInputDialog("Entrer l'adresse web:");
		try
		{
			InetAddress inetAdd = InetAddress.getByName(adresse);
			JOptionPane.showMessageDialog(null, inetAdd.getHostAddress());
			
			Socket socket = new Socket(inetAdd, port);
			
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			
			try
			{
				in = new ObjectInputStream(socket.getInputStream());
				message = (String)in.readObject();
				
				message = "GET HTTP/1.0\n" +
						"Accept: text/plain , image/*\n" +
						"If-Modified-Since: Wed, 10 Sep 2015 14:20:21 GMT\n" +
						"Referer: http://www.uqac.ca/index.html\n" +
						"User-Agent: Chrome/55.0.2883.87\n\n";
				out.writeObject(message);
				out.flush();
				
				
				JOptionPane.showMessageDialog(null, message);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
			socket.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Ecris un site web valide.");
		}
	}
	
	public static void main(String [] args)
	{
		try
		{
			StartClient();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
