package parserCalendario;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import parserClasificacion.WebUtils;
import parserResultados.Jornada;
import parserResultados.Partido;

public class ParserCalendario {

	private final String page = "http://www.lfp.es/?tabid=154&Controltype=pj&idDivision=1";
	private final String ficheroUltimajornada = "ultimaJornada";
	
	/**
	 * Parsea la próxima jornada que se jugará en primera division, para la cual
	 * se pronosticaran los resultados
	 * @return Objeto Jornada con la lista de partidos
	 */
	public Jornada parseProximaJornada()
	{
		// Se crea un objeto URL para pasarselo al WebUtils
		URL url = null;
		try {
			url = new URL(page);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		// Se descarga el codigo HTML de la pagina especificada
		String webPageCode = WebUtils.downloadURL(url);
		
		try {
			FileWriter fileWriter = new FileWriter(this.ficheroUltimajornada);
			fileWriter.write(webPageCode);
			// Abrimos el fichero para lectura
			File file = new File(this.ficheroUltimajornada);
			FileReader fr = null;
			fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
			String linea;
			Jornada jornada = new Jornada();
			Partido partido;
			while ((linea = reader.readLine()) != null) {
				if (linea.indexOf("<td height=\"16\" width=\"95\"><font face=\"Arial\"") != -1) {
					String equipoLocal = reader.readLine().trim();
					reader.readLine();
					String equipoVisitante = reader.readLine().trim();
					reader.readLine();
					partido = new Partido();
					partido.setEquipoLocal(equipoLocal);
					partido.setEquipoVisitante(equipoVisitante);
					jornada.add(partido);
				}
			}
			for(Partido p: jornada.values())
			{
				p.setEquipoLocal(arreglarTildes(p.getEquipoLocal()));
				p.setEquipoVisitante(arreglarTildes(p.getEquipoVisitante()));
			}
			return jornada;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Sustituye las tildes de HTML por los caracteres con tilde. Ejemplo: sustituye &aacute por á
	 */
	public String arreglarTildes(String nombre)
	{
		if(nombre.contains("&aacute;"))
		{
			return nombre = nombre.replace("&aacute;", "á");
		}
		else if(nombre.contains("&eacute;"))
		{
			return nombre = nombre.replace("&eacute;", "é");
		}
		else if(nombre.contains("&iacute;"))
		{
			return nombre = nombre.replace("&iacute;", "í");
		}
		else if(nombre.contains("&oacute;"))
		{
			return nombre = nombre.replace("&oacute;", "ó");
		}
		else if(nombre.contains("&uacute;"))
		{
			return nombre = nombre.replace("&uacute;", "ú");
		}
		return nombre;
	}
	
	public static void main(String[] args)
	{
		ParserCalendario calendario = new ParserCalendario();
		calendario.parseProximaJornada();
	}
}
