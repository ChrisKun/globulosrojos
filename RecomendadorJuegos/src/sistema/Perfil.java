package sistema;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import com.sun.org.apache.bcel.internal.generic.Select;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CaseComponent;
import logIn.InterfazLogIn;



public class Perfil implements CaseComponent 
{
	public enum Gender{
        Male, Female
        };
    public enum FormaDeSer{
            agresivo, solitario, friki, cotilla, extrovetido, clasico, trabajador, calculador, aventurero //me los invento yo
            };
	private String nickName;
	private Integer id;
	private Integer age;
	private Gender gender;
	private HashMap<Integer, Float> listaValoraciones = new HashMap<Integer, Float>();
	private Boolean tieneBuenaMemoria;
	private FormaDeSer formaDeSer;
	private Boolean tienePaciencia;
    
	String fichero = "perfil.dat";
	
	public Perfil() {}
	
	// Método para crear el perfil de un grupo de usuarios
	public Perfil(ArrayList<Perfil> listaUsuarios) {
		this.nickName = "";
		//this.id = ?;
		int age = 0;
		int contMale = 0;
		int contBuenaMemoria = 0;
		int contPaciencia = 0;
		int [] fds = new int[9]; int formaDeSer = 0;
		for (int i = 0; i < fds.length; i++)
			fds[i] = 0;
		// < GameID - <NumValoraciones - ValoracionMedia>>
		HashMap<Integer, Pair<Integer, Float>> tablaVal = new HashMap<Integer, Pair<Integer, Float>>();
		for (Perfil perfil : listaUsuarios) {
			age += perfil.age;
			if (perfil.gender == Gender.Male) contMale++;
			// Lista de valoraciones
			for(Entry<Integer, Float> e : perfil.listaValoraciones.entrySet()) {
				if (!tablaVal.containsKey(e.getKey()))
					tablaVal.put(e.getKey(), new Pair<Integer, Float>(1, e.getValue()));
				else {
					Pair <Integer, Float> par = tablaVal.get(e.getKey());
					par.elem1 = ((par.elem1 * par.elem0) +
							e.getValue()) / (par.elem0 + 1); 
					par.elem0 = par.elem0 + 1;
					tablaVal.remove(e.getKey());
					tablaVal.put(e.getKey(), par);
				}
			}
			if (perfil.tieneBuenaMemoria) contBuenaMemoria++;
			if (perfil.tienePaciencia) contPaciencia++;
			fds[perfil.formaDeSer.ordinal()]++;
			if (fds[perfil.formaDeSer.ordinal()] > fds[formaDeSer])
				formaDeSer = perfil.formaDeSer.ordinal();
		}
		
		this.age = age / listaUsuarios.size();
		this.gender = (contMale > listaUsuarios.size() / 2) ? Gender.Male : Gender.Female;
		this.listaValoraciones = new HashMap<Integer, Float>();
		for (Entry<Integer, Pair<Integer, Float>> e : tablaVal.entrySet()) {
			this.listaValoraciones.put(e.getKey(), e.getValue().elem1);
		}
		this.tieneBuenaMemoria = (contBuenaMemoria > listaUsuarios.size() / 2) ? true : false;
		this.tienePaciencia = (contPaciencia > listaUsuarios.size() / 2) ? true : false;
		this.formaDeSer = FormaDeSer.values()[formaDeSer];
	}
	
    public String toString() {
        return nickName + "-" + id + "-" + age + "-" + gender+ "-" + tieneBuenaMemoria+ "-" + formaDeSer+ "-" + tienePaciencia+ "-" + listaValoraciones;
	}
	
	public Boolean getTieneBuenaMemoria() {
		return tieneBuenaMemoria;
	}

	public void setTieneBuenaMemoria(Boolean tieneBuenaMemoria) {
		this.tieneBuenaMemoria = tieneBuenaMemoria;
	}

	public FormaDeSer getFormaDeSer() {
		return formaDeSer;
	}

	public void setFormaDeSer(FormaDeSer formaDeSer) {
		this.formaDeSer = formaDeSer;
	}

	public Boolean getTienePaciencia() {
		return tienePaciencia;
	}

	public void setTienePaciencia(Boolean tienePaciencia) {
		this.tienePaciencia = tienePaciencia;
	}

	public HashMap<Integer, Float> getListaValoraciones() {
		return listaValoraciones;
	}

	public void setListaValoraciones(HashMap<Integer, Float> listaValoraciones) {
		this.listaValoraciones = listaValoraciones;
	}

	
	public Attribute getIdAttribute() {
        return new Attribute("id", Perfil.class);
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
		if(!isNickRegistered(nick))
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
				Sistema.sumUserToCounter();//Suma 1 al numero de usuarios registrados
				actualizarNumUsersConfigFile();
				return true;
			} catch (IOException e) {
				System.out.println("Error, no se pudo cargar el fichero: "+fichero);
				return false;
			} 
		}
		else
		{
			return false;
		}
	}
	private void actualizarNumUsersConfigFile() {
		
		File file = new File("src/sistema/ficheros/numUsers");
		try {
			
			FileWriter writer = new FileWriter(file);
			writer.write(Sistema.getNumOfusers());
			writer.close();
		
		} catch (IOException e) {
			e.printStackTrace();
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
	 * Registra las preguntas opcionales que se le hacen al usuario
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
				String[] infoNick = linea.split("-");
				if(infoNick[0].equals(nickName))
				{
					while(datos.size()> i)
					{
						file.writeBytes("-");
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
	//FORMATO DEL FICHERO
	/*
	 * NICK, EDAD, SEXO, FORMA DE SER, TIENE MEMORIA, PACIENCIA, LISTA DE VALORACIONES, ID
	 */
	public static boolean UserCBRcasesToFile(Collection<CBRCase> userCases)
	{
		try {
			RandomAccessFile file = new RandomAccessFile("perfil.dat", "rw");
			file.seek(0);
			Iterator it = userCases.iterator();
			String finalFile = "";
			while(it.hasNext())
			{
				CBRCase caso = (CBRCase)it.next();
				Perfil usuario = (Perfil)caso.getDescription();
				//Ahora tienes en usuario la info del perfil. Haz lo que tengas que hacer
				finalFile = finalFile + usuario.getNickName()
				+ "," + usuario.getAge()
				 + "," + usuario.getGender()
				  + "," + usuario.getFormaDeSer()
				   + "," + usuario.getTieneBuenaMemoria()
				    + "," + usuario.getTienePaciencia()
				     + "," + usuario.getListaValoraciones()
				      + "," + usuario.getId()
				      + "\n";
//				usuario.Registrar(usuario.getNickName());
//				usuario.Loguear(usuario.getNickName());
//				ArrayList lista = new ArrayList();
//				lista.add(usuario.getAge());
//				lista.add(usuario.getGender());
//				lista.add(usuario.getFormaDeSer());
//				lista.add(usuario.getTieneBuenaMemoria());
//				lista.add(usuario.getTienePaciencia());
//				lista.add(usuario.getListaValoraciones());
//				lista.add(usuario.getId());
//				usuario.RegistrarDatosDelUsuario(lista);
			}
			finalFile = finalFile.substring(0, finalFile.length()-1);
			file.writeBytes(finalFile);
			return true;
		} catch (Exception e) {
			System.out.println("Error al cargar el archivo");
			System.out.print(e);
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

	public void GenerateRandomProfiles() {
		Random random = new Random();
		//edad
		setAge(random.nextInt(100));
		//genero
		if(random.nextBoolean())
			setGender(Gender.Male);
		else
			setGender(Gender.Female);
		//id
		setId(prueba.acumulador++);
		//tiene memoria?
		if(random.nextBoolean())
			setTieneBuenaMemoria(true);
		else
			setTieneBuenaMemoria(false);
		//tiene paciencia?
		if(random.nextBoolean())
			setTienePaciencia(true);
		else
			setTienePaciencia(false);
		//forma de ser
		switch (random.nextInt(8))
		{
			case 0: setFormaDeSer(formaDeSer.agresivo);
			break;
			case 1: setFormaDeSer(formaDeSer.aventurero);
			break;
			case 2: setFormaDeSer(formaDeSer.calculador);
			break;
			case 3: setFormaDeSer(formaDeSer.clasico);
			break;
			case 4: setFormaDeSer(formaDeSer.cotilla);
			break;
			case 5: setFormaDeSer(formaDeSer.extrovetido);
			break;
			case 6: setFormaDeSer(formaDeSer.friki);
			break;
			case 7: setFormaDeSer(formaDeSer.solitario);
			break;
			case 8: setFormaDeSer(formaDeSer.trabajador);
			break;
		}
	}
	
}

class Pair<T1, T2> {
	public T1 elem0;
	public T2 elem1;
	
	public Pair() {}
	
	public Pair(T1 elem0, T2 elem1) {
		this.elem0 = elem0;
		this.elem1 = elem1;
	}
	
	public String toString() {
		return elem0.toString() + " - " + elem1.toString();
	}
}