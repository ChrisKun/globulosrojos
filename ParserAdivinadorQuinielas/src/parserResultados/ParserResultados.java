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

import utils.StringUtils;
import utils.WebUtils;

public class ParserResultados {
	
	String separator = System.getProperty("file.separator");
	
	private final String HTML_CODE_PATH = "Ficheros"+ separator +"codigoHTML";
	
	private BufferedReader reader = null;
	private Temporada temporada;
	
	public Temporada getTemporada() {
		return temporada;
	}

	public void parse(String page) throws IOException {

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
		try{
			while ((linea = reader.readLine()) != null) {
				if (linea.indexOf("<tr class=\"FondoFilaCalendario\"") != -1) {
					p = new Partido();
					reader.readLine();
					p.setEquipoLocal(StringUtils.arreglarTildes(reader.readLine().trim()));
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
					p.setEquipoVisitante(StringUtils.arreglarTildes(reader.readLine().trim()));
					
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
		}catch (NumberFormatException e)
		{
			//e.printStackTrace();
			//System.out.println("Se ha llegado al final de los resultados de la temporada actual");
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
	
	public void resetFile(PrintWriter writer) {
		writer.print("");
	}
}
