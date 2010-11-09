package parserClasificacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ParserClasificacion {

	String separator = System.getProperty("file.separator");
	private final String HTML_CODE_PATH = "Ficheros"+ separator +"codigoHTML";

	private BufferedReader reader = null;
	private Clasificacion jornada;

	public boolean parse(String page) throws NumberFormatException, IOException {

		// Se crea un objeto URL para pasarselo al WebUtils
		URL url = null;
		try {
			url = new URL(page);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// Booleano que indica si la operaci�n ha tenido �xito
		boolean exito = false;

		// Se descarga el codigo HTML de la pagina especificada
		String webPageCode = WebUtils.downloadURL(url);

		FileWriter fileWriter = new FileWriter(this.HTML_CODE_PATH);
		fileWriter.write(webPageCode);
		// Abrimos el fichero para lectura
		File file = new File(this.HTML_CODE_PATH);
		FileReader fr = null;
		fr = new FileReader(file);
		reader = new BufferedReader(fr);

		String linea;
		Posicion p = new Posicion();
		jornada = new Clasificacion();
		try {
			while ((linea = reader.readLine()) != null) {
				if (linea.indexOf("<td class=\"FondoGridPlantilla\" align=\"left\"") != -1) {
					exito = true;
					p = new Posicion();
					p.setEquipo(reader.readLine().trim());
					reader.readLine();
					p.setPuntos(Integer.parseInt(reader.readLine().trim()));
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					p.setGolesFavor(Integer.parseInt(reader.readLine().trim()));
					reader.readLine();
					p.setGolesContra(Integer.parseInt(reader.readLine().trim()));
					reader.readLine();
					reader.readLine();
					reader.readLine();
					p.setPartidosJugadosCasa(Integer.parseInt(reader.readLine().trim()));
					reader.readLine();
					p.setPartidosGanadosCasa(Integer.parseInt(reader.readLine().trim()));
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					reader.readLine();
					p.setPartidosJugadosFuera(Integer.parseInt(reader.readLine().trim()));
					reader.readLine();
					p.setPartidosGanadosFuera(Integer.parseInt(reader.readLine().trim()));
					jornada.add(p);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			exito = false;
		}
		fr.close();

		return exito;
	}

	public void writeInfo(PrintWriter writer){    	        
		for (Posicion posicion : jornada.getPosiciones()) {
			writer.print(posicion.getEquipo() + ", ");
			writer.print(posicion.getPuntos() + ", ");
			writer.print(posicion.getGolesFavor() + ", ");
			writer.print(posicion.getGolesContra() + ", ");
			writer.print(posicion.getPartidosJugadosCasa() + ", ");
			writer.print(posicion.getPartidosGanadosCasa() + ", ");
			writer.print(posicion.getPartidosJugadosFuera() + ", ");
			writer.print(posicion.getPartidosGanadosFuera());
			writer.println();
		} 
	}

	public void escribirPrimeraClasificacion(PrintWriter writer) {

		ArrayList<Posicion> clasificacion = jornada.getPosiciones();

		for(Posicion pos : clasificacion)
		{
			writer.println(pos.getEquipo() + " , 0, 0, 0, 0, 0, 0, 0");
		}
		writer.println("----------------------------------");
	}

	public void writeSeparator(PrintWriter writer) {
		writer.println("----------------------------------");
	}

	/**
	 * Sustituye las tildes de HTML por los caracteres con tilde. Ejemplo: sustituye &aacute por á
	 */
	public void arreglarTildes()
	{
		for (Posicion posicion : jornada.getPosiciones()) {
			if(posicion.getEquipo().contains("&aacute;"))
			{
				posicion.setEquipo(posicion.getEquipo().replace("&aacute;", "á"));
			}
			else if(posicion.getEquipo().contains("&eacute;"))
			{
				posicion.setEquipo(posicion.getEquipo().replace("&eacute;", "é"));
			}
			else if(posicion.getEquipo().contains("&iacute;"))
			{
				posicion.setEquipo(posicion.getEquipo().replace("&iacute;", "í"));
			}
			else if(posicion.getEquipo().contains("&oacute;"))
			{
				posicion.setEquipo(posicion.getEquipo().replace("&oacute;", "ó"));
			}
			else if(posicion.getEquipo().contains("&uacute;"))
			{
				posicion.setEquipo(posicion.getEquipo().replace("&uacute;", "ú"));
			}
		}
	}

	public void resetFile(PrintWriter writer) {
		writer.print("");
	}
}
