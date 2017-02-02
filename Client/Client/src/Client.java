import java.net.*;
import java.io.*;
import javax.swing.*;
public class Client {

	public static void StartClient() throws Exception // Méthode principale utilisée par le programme pour retrouver une page web
	{
		ObjectOutputStream out; // stream de sortie pour envoyer une requete
		PrintWriter print; // point de contact avec le serveur distant
		
		int port = 80; // port utilisél, 80 car requete html
		String adresse = JOptionPane.showInputDialog("Entrer l'adresse du site web ( Ex : www.uqac.ca )"); // Domaine recherché par le dns
		String page = JOptionPane.showInputDialog("Entrez la page recherchee ( Ex : etudiants/"); // Emplacement de la page voulue sur le serveur
		try
		{
			InetAddress inetAdd = InetAddress.getByName(adresse); // Recherche de l'adresse IP par le serveur dns
			Socket socket = new Socket(inetAdd, port); // Socket de connexion client
			
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			print = new PrintWriter(out);
			
			try
			{
				// Début de la requête html basique
				print.println("Get /" + page + " HTTP/1.1");
				print.println("Host: " + adresse);
				print.println("Accept: text/plain , image/*");
				print.println("");
				print.flush();
				
				// Création du lecteur pour recevoir la page html
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String st;
				boolean html  = false; // variable utilisée pour savoir si on a franchi le seuil d'affichage du html
				PrintWriter writer = new PrintWriter("page.html", "UTF-8"); // écriture de la page html recue

				while((st = br.readLine()) != null) // Lecture tant qu'il reste des lignes dans la réception
				{
					if(html)
						writer.println(st); // Écriture dans un fichier
					if(st.isEmpty()) // Détecte qu'il y a une vide nulle et donc qu'on peut commencer a enregistrer
						html = true;
					//System.out.println(st); // ligne pouvant être rajoutée pour le debug
				}
				br.close(); // fermeture du reader
				writer.close(); // fermeture de l'ecriture sur le fichier .html
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
