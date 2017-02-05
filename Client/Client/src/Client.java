import java.net.*;
import java.io.*;
import javax.swing.*;
public class Client {

	static boolean debug_console = false;
	
	public static void StartClient() throws Exception // M�thode principale utilis�e par le programme pour retrouver une page web
	{
		if(debug_console) System.out.println("Entr�e dans StartClient() .");
		ObjectOutputStream out; // stream de sortie pour envoyer une requete
		PrintWriter print; // point de contact avec le serveur distant
		
		int port = 80; // port utilis�, 80 car requete html
		String adresse = JOptionPane.showInputDialog("Entrer l'adresse du site web ( Ex : www.uqac.ca )"); // Domaine recherch� par le dns
		String page = JOptionPane.showInputDialog("Entrez la page recherchee ( Ex : etudiants/"); // Emplacement de la page voulue sur le serveur
		try
		{
			if(debug_console) System.out.println("Recherche de l'adresse par serveur DNS.");
			InetAddress inetAdd = InetAddress.getByName(adresse); // Recherche de l'adresse IP par le serveur dns
			if(debug_console) System.out.println("IP : " + inetAdd.getHostAddress());
			Socket socket = new Socket(inetAdd, port); // Socket de connexion client
			
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			print = new PrintWriter(out);
			
			try
			{
				if(debug_console) System.out.println("Requ�te HTML :");
				// D�but de la requ�te html basique
				print.println("Get /" + page + " HTTP/1.1");  if(debug_console) System.out.println("Get /" + page + " HTTP/1.1");
				print.println("Host: " + adresse); if(debug_console) System.out.println("Host: " + adresse);
				print.println("Accept: text/plain , image/*"); if(debug_console) System.out.println("Accept: text/plain , image/*");
				print.println(""); if(debug_console) System.out.println("");
				print.flush();
				
				// Cr�ation du lecteur pour recevoir la page html
				if(debug_console) System.out.println("Cr�ation de page.html");
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String st;
				boolean html  = false; // variable utilis�e pour savoir si on a franchi le seuil d'affichage du html
				PrintWriter writer = new PrintWriter("page.html", "UTF-8"); // �criture de la page html recue

				while((st = br.readLine()) != null) // Lecture tant qu'il reste des lignes dans la r�ception
				{
					if(html)
						writer.println(st);  // �criture dans un fichier
					if(st.isEmpty()) // D�tecte qu'il y a une vide nulle et donc qu'on peut commencer a enregistrer
						html = true;
					if(debug_console) System.out.println(st);
				}
				br.close(); // fermeture du reader
				writer.close(); // fermeture de l'ecriture sur le fichier .html
				if(debug_console) System.out.println("Fermeture du socket et du programme");
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
		if(debug_console) System.out.println("D�marrage du programme.");
		try
		{
			StartClient();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		if(debug_console) System.out.println("Fin du main");
	}
}
