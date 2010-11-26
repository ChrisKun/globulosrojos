package gui;

public class Opciones {
	
	public enum votacion {BASICA, PONDERADA}; 
	
	public static votacion opcionVotacion = votacion.PONDERADA;
	public static int opcionKNN = 3;
	public static int ultimaJornada = 12;
}
