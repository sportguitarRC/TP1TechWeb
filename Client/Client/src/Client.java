import java.net.*;
import java.io.*;
import javax.swing.*;
public class Client {

	static boolean debug_console = false;
	
	public static void StartClient() throws Exception // Méthode principale utilisée par le programme pour retrouver une page web
	{
		if(debug_console) System.out.println("Entrée dans StartClient() .");
		ObjectOutputStream out; // stream de sortie pour envoyer une requete
		PrintWriter print; // point de contact avec le serveur distant
		
		int port = 80; // port utilisé, 80 car requete html
		String adresse = JOptionPane.showInputDialog("Entrer l'adresse du site web ( Ex : www.uqac.ca )"); // Domaine recherché par le dns
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
				if(debug_console) System.out.println("Requête HTML :");
				// Début de la requête html basique
				print.println("Get /" + page + " HTTP/1.1");  if(debug_console) System.out.println("Get /" + page + " HTTP/1.1");
				print.println("Host: " + adresse); if(debug_console) System.out.println("Host: " + adresse);
				print.println("Accept: text/plain , image/*"); if(debug_console) System.out.println("Accept: text/plain , image/*");
				print.println(""); if(debug_console) System.out.println("");
				print.flush();
				
				// Création du lecteur pour recevoir la page html
				if(debug_console) System.out.println("Création de page.html");
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String st;
				boolean html  = false; // variable utilisée pour savoir si on a franchi le seuil d'affichage du html
				PrintWriter writer = new PrintWriter("page.html", "UTF-8"); // écriture de la page html recue

				while((st = br.readLine()) != null) // Lecture tant qu'il reste des lignes dans la réception
				{
					if(html)
						writer.println(st);  // Écriture dans un fichier
					if(st.isEmpty()) // Détecte qu'il y a une vide nulle et donc qu'on peut commencer a enregistrer
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
		if(debug_console) System.out.println("Démarrage du programme.");
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
