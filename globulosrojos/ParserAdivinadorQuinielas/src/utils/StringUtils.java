package utils;

import parserClasificacion.Clasificacion;
import parserClasificacion.Posicion;

public class StringUtils {
	
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
	
	public static String convertirNombresEquipos(String nombre) {
		if (nombre.equals("Sevilla"))
			return "Sevilla F.C.";
		if (nombre.equals("Getafe"))
			return "Getafe C.F.";
		if (nombre.equals("Athletic"))
			return "Athletic Club";
		if (nombre.equals("Osasuna") || nombre.equals("At. Osasuna"))
			return "C. At. Osasuna";
		if (nombre.equals("Deportivo"))
			return "R.C. Deportivo";
		if (nombre.equals("Almería"))
			return "U.D. Almería";
		if (nombre.equals("Racing") || nombre.equals("R.Racing C."))
			return "Real Racing Club";
		if (nombre.equals("Barcelona"))
			return "F.C. Barcelona";
		if (nombre.equals("Mallorca"))
			return "R.C.D. Mallorca";
		if (nombre.equals("Levante"))
			return "Levante U.D.";
		if (nombre.equals("Espanyol"))
			return "R.C.D. Espanyol";
		if (nombre.equals("Valladolid"))
			return "Real Valladolid";
		if (nombre.equals("R. Madrid") || nombre.equals("Real Madrid"))
			return "Real Madrid C.F.";
		if (nombre.equals("At. Madrid") || nombre.equals("Atlético Madrid"))
			return "At. de Madrid";
		if (nombre.equals("Recreativo"))
			return "R.C.R. de Huelva";
		if (nombre.equals("Betis"))
			return "Real Betis B. S.";
		if (nombre.equals("Valencia"))
			return "Valencia C.F.";
		if (nombre.equals("Villarreal"))
			return "Villarreal C.F.";
		if (nombre.equals("Murcia"))
			return "Real Murcia C.F.";
		if (nombre.equals("Zaragoza") || nombre.equals("R. Zaragoza"))
			return "Real Zaragoza CD";
		if (nombre.equals("Numancia"))
			return "C.D. Numancia";
		if (nombre.equals("Sporting") || nombre.equals("R. Sporting"))
			return "Real S. de Gijón";
		if (nombre.equals("Málaga"))
			return "Málaga C.F.";
		if (nombre.equals("Xerez"))
			return "Xerez C.D.";
		if (nombre.equals("Tenerife"))
			return "C.D. Tenerife";
		if (nombre.equals("R. Sociedad"))
			return "Real Sociedad";
		if (nombre.equals("Hércules"))
			return "Hércules C.F.";
		return nombre;
	}
}
