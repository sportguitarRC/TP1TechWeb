import java.net.*;
import java.io.*;
import javax.swing.*;
public class Client {

	public static void StartClient() throws Exception
	{
		ObjectOutputStream out;
		ObjectInputStream in;
		PrintWriter print;
		
		int port = 80;
		String adresse = JOptionPane.showInputDialog("Entrer l'adresse web:");
		try
		{
			InetAddress inetAdd = InetAddress.getByName(adresse);
			Socket socket = new Socket(inetAdd, port);
			
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			print = new PrintWriter(out);
			
			try
			{
				print.println("Get / HTTP/1.1");
				print.println("Host: " + adresse);
				print.println("Accept: text/plain , image/*");
				print.println("");
				print.flush();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String st;
				System.out.println(br.readLine());
				while((st = br.readLine()) != null)
					System.out.println(st);
				br.close();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			socket.close();
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
			StartClient();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
