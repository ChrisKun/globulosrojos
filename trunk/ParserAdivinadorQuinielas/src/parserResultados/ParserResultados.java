package parserResultados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import parserClasificacion.WebUtils;

public class ParserResultados {
	private final String HTML_CODE_PATH = "Ficheros/codigoHTML";
	
	private BufferedReader reader = null;
	private Temporada temporada;
	
	public void parse(String page) throws NumberFormatException, IOException {

		temporada = new Temporada();
		
		// Se crea un objeto URL para pasarselo al WebUtils
		URL url = null;
		try{
			url = new URL(page);
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		
		// Se descarga el codigo HTML de la pagina especificada
		WebUtils.downloadURL(url, this.HTML_CODE_PATH);
		
		// Abrimos el fichero para lectura
		File file = new File(this.HTML_CODE_PATH);
		FileReader fr = null;
		fr = new FileReader(file);
		reader = new BufferedReader(fr);

		String linea;
		int numPartido = 0;
		Partido p = null;
		Jornada j = null;
		while ((linea = reader.readLine()) != null) {
			if (linea.indexOf("<tr class=\"FondoFilaCalendario\"") != -1) {
				p = new Partido();
				reader.readLine();
				p.setEquipoLocal(arreglarTildes(reader.readLine().trim()));
				reader.readLine();
				reader.readLine();
				reader.readLine();
				reader.readLine();
				reader.readLine();
				p.setGolesLocal(Integer.parseInt(reader.readLine().trim()));
				reader.readLine();
				reader.readLine();
				p.setGolesVisitante(Integer.parseInt(reader.readLine().trim()));
				reader.readLine();
				reader.readLine();
				reader.readLine();
				reader.readLine();
				reader.readLine();
				p.setEquipoVisitante(arreglarTildes(reader.readLine().trim()));
				
				if (numPartido == 0) j = new Jornada();
				j.add(p);
				
				numPartido++;
				if (numPartido == 10) {
					System.out.print('-');
					temporada.add(j);
					numPartido = 0;
				}
			}
		}
		
		fr.close();
	}

	public void writeInfo(PrintWriter writer){    	        

        Iterator<Jornada> itTemporada = temporada.iterator();
        
        while (itTemporada.hasNext()) {
        	Jornada jornada = itTemporada.next();
        	Collection<Partido> cJornada = jornada.values();
        	
        	Iterator<Partido> itPartido = cJornada.iterator();
        	while (itPartido.hasNext()) {
        		Partido partido = itPartido.next();
        		writer.print(partido.getEquipoLocal() + ", ");
        		writer.print(partido.getGolesLocal() + ", ");
        		writer.print(partido.getGolesVisitante() + ", ");
        		writer.print(partido.getEquipoVisitante());
        		writer.println();
        	}
        	writer.println("----------------------------");
        }
	}
	
	
	/**
	 * Sustituye las tildes de la cadena por los caracteres con tilde. Ejemplo: sustituye &aacute por á
	 */
	public String arreglarTildes(String s)
	{
		return s.replace("&aacute;", "á").
				replace("&eacute;", "é").
				replace("&iacute;", "í").
				replace("&oacute;", "ó").
				replace("&uacute;", "ú");
	}
	
	public void resetFile(PrintWriter writer) {
		writer.print("");
	}
}
