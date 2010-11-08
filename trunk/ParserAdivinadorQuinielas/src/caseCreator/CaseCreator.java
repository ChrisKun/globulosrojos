package caseCreator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import parserClasificacion.Posicion;
import parserClasificacion.Clasificacion;
import parserResultados.Jornada;
import parserResultados.Partido;
import parserResultados.Temporada;

public class CaseCreator {

	/**
	 * Junta en un unico fichero la informacion contenida en los ficheros de
	 * clasificaciones y resultados creando la base de casos definitiva para el
	 * sistema basado en casos
	 */
	public void juntarInfo() {
		ArrayList<ArrayList<Clasificacion>> temporadasClasificaciones = new ArrayList<ArrayList<Clasificacion>>();
		// Se leen las clasificaciones de las tres temporadas
		for (int temporada = 107; temporada < 111; temporada++) {
			temporadasClasificaciones.add(this
					.leerClasificacion("Ficheros/ClasificacionesTemp"
							+ temporada));
		}
		System.out.println("Clasificaciones leidas");

		// Se leen los resultados de las tres temporadas
		ArrayList<Temporada> temporadasResultados = new ArrayList<Temporada>();
		for (int temporada = 107; temporada < 111; temporada++)
		temporadasResultados
				.add(leerResultados("Ficheros/ResultadosTemp" + temporada));
		System.out.println("Resultados leidos");
		System.out.println("Creando fichero de casos...");
		
		// Se escribe toda la informacion en un unico fichero
		writeInfo(temporadasClasificaciones, temporadasResultados);

		System.out.println("Fichero de casos creado");
	}

	/**
	 * Lee la clasificacion de la siguiente jornada
	 * 
	 * @param path
	 */
	private ArrayList<Clasificacion> leerClasificacion(String path) {
		FileReader file;
		ArrayList<Clasificacion> listaJornadas = new ArrayList<Clasificacion>();
		try {
			file = new FileReader(path);
			BufferedReader reader = new BufferedReader(file);
			int counter = 0;
			Clasificacion jornada = new Clasificacion();
			String linea = reader.readLine();
			while (linea != null) {
				if (!linea.startsWith("--")) {
					String[] infoPosicion = linea.split(", ");
					Posicion posicion = new Posicion();
					posicion.setPosicion(counter + 1);
					posicion.setEquipo(infoPosicion[0]);
					posicion.setPuntos(Integer.parseInt(infoPosicion[1]));
					posicion.setPartidosGanadosCasa(Integer
							.parseInt(infoPosicion[4]));
					posicion.setPartidosGanadosFuera(Integer
							.parseInt(infoPosicion[5]));
					posicion.setGolesFavor(Integer.parseInt(infoPosicion[2]));
					posicion.setGolesContra(Integer.parseInt(infoPosicion[3]));
					jornada.add(posicion);
					counter++;
					linea = reader.readLine();
				} else {
					listaJornadas.add(jornada);
					counter = 0;
					linea = reader.readLine();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return listaJornadas;
	}

	/**
	 * Lee la clasificacion de la siguiente jornada
	 * 
	 * @param path
	 */
	private Temporada leerResultados(String path) {
		FileReader file;
		Temporada temporada = new Temporada();
		try {
			file = new FileReader(path);
			BufferedReader reader = new BufferedReader(file);
			Jornada jornada = new Jornada();
			String linea = reader.readLine();
			while (linea != null) {
				if (!linea.startsWith("--")) {
					String[] infoPartido = linea.split(", ");
					Partido partido = new Partido();
					partido.setEquipoLocal(infoPartido[0]);
					partido.setGolesLocal(Integer.parseInt(infoPartido[1]));
					partido.setEquipoVisitante(infoPartido[3]);
					partido.setGolesVisitante(Integer.parseInt(infoPartido[2]));
					jornada.add(partido);
					linea = reader.readLine();
				} else {
					temporada.add(jornada);
					jornada = new Jornada();
					linea = reader.readLine();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return temporada;
	}

	private void writeInfo(ArrayList<ArrayList<Clasificacion>> clasificaciones,
			ArrayList<Temporada> resultados) {
		
		FileWriter file = null;
		PrintWriter writer = null;
		int caseID = 1;
		
		try {
			file = new FileWriter("Casos/casos.txt");
			writer = new PrintWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Por cada temporada
		for (int counter = 0; counter < clasificaciones.size(); counter++) {
			ArrayList<Clasificacion> clasificacion = clasificaciones
					.get(counter);
			Temporada temporada = resultados.get(counter);
			// Por cada jornada
			for (int i = 0; i < clasificacion.size(); i++) {
				Clasificacion clasiJornada = clasificacion.get(i);
				
				try{//Para capturar la excepcion en la ultima vuelta (la de la temporada actual)
					Jornada jornada = temporada.get(i);
					Collection<Partido> partidosJornada = jornada.values();
					Iterator<Partido> itPartido = partidosJornada.iterator();
					while (itPartido.hasNext()) {
						Partido partido = itPartido.next();
						Posicion posicionLocal = clasiJornada
								.getPosicionByName(convertirNombresEquipos(partido
										.getEquipoLocal()));
						Posicion posicionVisitante = clasiJornada
								.getPosicionByName(convertirNombresEquipos(partido
										.getEquipoVisitante()));
						writer.print(caseID + ", ");
						writer.print(partido.getEquipoLocal() + ", ");
						writer.print(partido.getEquipoVisitante() + ", ");
						writer.print(posicionLocal.getPuntos() + ", ");
						writer.print(posicionVisitante.getPuntos() + ", ");
						writer.print(posicionLocal.getPuntos()- posicionVisitante.getPuntos() + ", ");
						
						writer.print(posicionLocal.getPosicion() + ", ");
						writer.print(posicionVisitante.getPosicion() + ", ");
						
						writer.print(posicionLocal.getGolesFavor() + ", ");
						writer.print(posicionLocal.getGolesContra() + ", ");
						writer.print(posicionVisitante.getGolesFavor() + ", ");
						writer.print(posicionVisitante.getGolesContra() + ", ");
						//Creo que hay que ponerlo en modo porcentaje
						if(posicionLocal.getPartidosJugadosCasa() != 0)
							writer.print(posicionLocal.getPartidosGanadosCasa() / posicionLocal.getPartidosJugadosCasa() + ", ");
						else
							writer.print("0, ");
						if (posicionLocal.getPartidosJugadosFuera() != 0)
							writer.print(posicionLocal.getPartidosGanadosFuera() / posicionLocal.getPartidosJugadosFuera() + ", ");
						else
							writer.print("0, ");
						writer.print(partido.getGolesLocal() + ", ");
						writer.print(partido.getGolesVisitante());
						caseID++;
						writer.println();
					}
				}catch(IndexOutOfBoundsException e)
				{
					System.out.println("Parseada temporada actual");
				}
			}
		}
		try {
			writer.close();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String convertirNombresEquipos(String nombre) {

		if (nombre.equals("Sevilla"))
			return "Sevilla F.C.";
		if (nombre.equals("Getafe"))
			return "Getafe C.F.";
		if (nombre.equals("Athletic"))
			return "Athletic Club";
		if (nombre.equals("Osasuna"))
			return "C. At. Osasuna";
		if (nombre.equals("Deportivo"))
			return "R.C. Deportivo";
		if (nombre.equals("Almería"))
			return "U.D. Almería";
		if (nombre.equals("Racing"))
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
		if (nombre.equals("R. Madrid"))
			return "Real Madrid C.F.";
		if (nombre.equals("At. Madrid"))
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
		if (nombre.equals("Zaragoza"))
			return "Real Zaragoza CD";
		if (nombre.equals("Numancia"))
			return "C.D. Numancia";
		if (nombre.equals("Sporting"))
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
		return null;
	}
}
