import java.net.*;

import javax.swing.*;
public class Client {

	public static void StartClient() throws Exception
	{
		int port = 80;
		String adresse = JOptionPane.showInputDialog("Entrer l'adresse web:");
		try
		{
			InetAddress aa = InetAddress.getByName(adresse);
			JOptionPane.showMessageDialog(null, aa.getHostAddress());
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Ecris un site web valide criss de retard");
		}
		Socket socket = new Socket(adresse, port);
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
