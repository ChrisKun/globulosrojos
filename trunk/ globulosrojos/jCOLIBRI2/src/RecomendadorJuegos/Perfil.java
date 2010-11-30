package RecomendadorJuegos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class Perfil 
{
	String nickName;
	String fichero = "perfil.txt";
	
	public boolean Loguear(String nick)
	{
		if(isNickRegistered(nick))
		{
			nickName = nick;
			return true;
		}
		else return false;
	}
	public boolean Registra(String nick)
	{
		try {
			FileWriter fileW = new FileWriter(fichero);
			PrintWriter writer = new PrintWriter(fileW);
			writer.println();
			writer.print(nick);
			writer.close();
			fileW.close();
			return true;
		} catch (IOException e) {
			System.out.println("Error, no se pudo cargar el fichero: "+fichero);
			return false;
		}
		
        
	}
	public boolean isNickRegistered (String nick)
	{
		try {
			FileReader fileR = new FileReader (fichero);
			BufferedReader reader = new BufferedReader (fileR);
			String linea = reader.readLine();
			while(linea != null)
			{
				String[] infoNick = linea.split(",");
				if(infoNick[0].equals(nick))
					return true;
				else
					linea = reader.readLine();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Error, no se pudo cargar el fichero: "+fichero);
			return false;
		} catch (IOException e) {
			System.out.println("Error, al leer el fichero: "+fichero);
			return false;
		}
		return false;
		
	}
	
}
