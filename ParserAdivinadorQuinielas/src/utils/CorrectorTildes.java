package utils;

import parserClasificacion.Clasificacion;
import parserClasificacion.Posicion;

public class CorrectorTildes {
	
	/**
	 * Corrige los caracteres con tilde
	 * @param nombre: String a inspeccionar en busca de tildes
	 * @return String con tildes corregidas
	 */
	public static String arreglarTildes(String s)
	{
		return s.replace("&aacute;", "á").
				replace("&eacute;", "é").
				replace("&iacute;", "í").
				replace("&oacute;", "ó").
				replace("&uacute;", "ú");
	}
	
	/**
	 * Sustituye las tildes de HTML por los caracteres con tilde. Ejemplo: sustituye &aacute por á
	 */
	public static Clasificacion arreglarTildes(Clasificacion jornada)
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
		return jornada;
	}
}
