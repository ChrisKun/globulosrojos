package RecomendadorJuegos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class Perfil implements CaseComponent 
{
	public enum Gender{
        Male, Female
        };
    public enum Occupation {
            administrator, artist, doctor, educator, engineer, entertainment,
            executive, healthcare, homemaker, lawyer, librarian, marketing, none,
            other, programmer, retired, salesman, scientist, student, technician, writer
    	}
	String nickName;
	Integer id;
    Integer age;
    Gender gender;
    HashMap<Integer, Float> listaValoraciones = new HashMap<Integer, Float>();
    
	public HashMap<Integer, Float> getListaValoraciones() {
		return listaValoraciones;
	}

	public void setListaValoraciones(HashMap<Integer, Float> listaValoraciones) {
		this.listaValoraciones = listaValoraciones;
	}

	Occupation occupation;
    
	String fichero = "perfil.dat";
	
	public Attribute getIdAttribute() {
        return new Attribute("id", Perfil.class);
	}
	
	public String toString() {
        return nickName + "," + id + "," + age + "," + gender + "," + occupation;
	}
	
	public boolean Loguear(String nick)
	{
		if(isNickRegistered(nick))
		{
			nickName = nick;
			return true;
		}
		else return false;
	}
	public boolean Registrar(String nick)
	{
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(fichero, "rw");
//			while(file.readLine()!=null)
//			{}
			file.seek(file.length());
			file.writeBytes("\n");
			file.writeBytes(nick);
//			writer.println();
//			writer.print(nick);
//			writer.close();
//			fileW.close();
			file.close();
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
	/**
	 * Rregistra las 4 preguntas iniciales que se le hacen al usuario
	 * @param datos
	 * @return
	 */
	public boolean RegistrarDatosDelUsuario (ArrayList datos)
	{
		RandomAccessFile file;
//		FileWriter fileW;
//		PrintWriter writer;
//		int numLinea = 0;
		int i=0;
		try {
			//primero encontramos el nick
			file = new RandomAccessFile(fichero, "rw");
			//FileReader fileR = new FileReader (fichero);
			//BufferedReader reader = new BufferedReader (fileR);
			String linea = file.readLine();
			//numLinea++;
			while(linea != null)
			//while(!linea.equals(""))
			{
				String[] infoNick = linea.split(",");
				if(infoNick[0].equals(nickName))
				{
					while(datos.size()> i)
					{
						file.writeBytes(",");
						file.writeBytes(datos.get(i).toString());
						i++;
						//fileW = new FileWriter(fichero);
						//writer = new PrintWriter(fileW);
					}
					linea = file.readLine();
				}
				else
				{
					linea = file.readLine();
					//numLinea++;
				}
			}
//			writer.println();
//			writer.print(nickName);
//			writer.close();
//			fileW.close();
			file.close();
			return true;
		} catch (IOException e) {
			System.out.println("Error, no se pudo cargar el fichero: "+fichero);
			return false;
		} 
	}

    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}
	
}
