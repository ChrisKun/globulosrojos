package sistema;

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
import java.util.Random;

import com.sun.org.apache.bcel.internal.generic.Select;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;



public class Perfil implements CaseComponent 
{
	public enum Gender{
        Male, Female
        };
    public enum FormaDeSer{
            agresivo, solitario, friki, cotilla, extrovetido, clasico, trabajador, calculador, aventurero //me los invento yo
            };
	String nickName;
	Integer id;
    Integer age;
    Gender gender;
    HashMap<Integer, Float> listaValoraciones = new HashMap<Integer, Float>();
    Boolean tieneBuenaMemoria;
    FormaDeSer formaDeSer;
    Boolean tienePaciencia;
    
	String fichero = "perfil.dat";
	
    public String toString() {
        return nickName + "," + id + "," + age + "," + gender+ "," + tieneBuenaMemoria+ "," + formaDeSer+ "," + tienePaciencia+ "," + listaValoraciones;
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
