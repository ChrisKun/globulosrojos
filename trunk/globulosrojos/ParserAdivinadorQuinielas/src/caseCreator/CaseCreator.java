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
import utils.StringUtils;

public class CaseCreator {

	/**
	 * Junta en un unico fichero la informacion contenida en los ficheros de
	 * clasificaciones y resultados creando la base de casos definitiva para el
	 * sistema basado en casos
	 */
	public void juntarInfo() {
		
		String separator = System.getProperty("file.separator");
		
		ArrayList<ArrayList<Clasificacion>> temporadasClasificaciones = new ArrayList<ArrayList<Clasificacion>>();
		// Se leen las clasificaciones de las tres temporadas
		for (int temporada = 107; temporada < 111; temporada++) {
			temporadasClasificaciones.add(this
					.leerClasificacion("Ficheros"+ separator +"ClasificacionesTemp"
							+ temporada));
		}
		System.out.println("Clasificaciones leidas");

		// Se leen los resultados de las tres temporadas
		ArrayList<Temporada> temporadasResultados = new ArrayList<Temporada>();
		for (int temporada = 107; temporada < 111; temporada++)
		temporadasResultados
				.add(leerResultados("Ficheros"+ separator +"ResultadosTemp" + temporada));
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
	public static ArrayList<Clasificacion> leerClasificacion(String path) {
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
					posicion.setEquipo(infoPosicion[0].trim());
					posicion.setPuntos(Integer.parseInt(infoPosicion[1]));
					posicion.setPartidosJugadosCasa(Integer.parseInt(infoPosicion[4]));
					posicion.setPartidosGanadosCasa(Integer.parseInt(infoPosicion[5]));
					posicion.setPartidosJugadosFuera(Integer.parseInt(infoPosicion[6]));
					posicion.setPartidosGanadosFuera(Integer.parseInt(infoPosicion[7]));
					posicion.setGolesFavor(Integer.parseInt(infoPosicion[2]));
					posicion.setGolesContra(Integer.parseInt(infoPosicion[3]));
					jornada.add(posicion);
					counter++;
					linea = reader.readLine();
				} else {
					listaJornadas.add(jornada);
					jornada = new Clasificacion();
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
	 * Lee los resultados de la siguiente jornada
	 * 
	 * @param path
	 */
	public static Temporada leerResultados(String path) {
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
								.getPosicionByName(StringUtils.convertirNombresEquipos(partido
										.getEquipoLocal()).trim());
						Posicion posicionVisitante = clasiJornada
								.getPosicionByName(StringUtils.convertirNombresEquipos(partido
										.getEquipoVisitante()).trim());
						
						writer.print("Case " + caseID + ",");
						writer.print(partido.getEquipoLocal() + ",");
						writer.print(partido.getEquipoVisitante() + ",");
						writer.print(posicionLocal.getPuntos() + ",");
						writer.print(posicionVisitante.getPuntos() + ",");
						writer.print(posicionLocal.getPuntos()- posicionVisitante.getPuntos() + ",");
						
						writer.print(posicionLocal.getPosicion() + ",");
						writer.print(posicionVisitante.getPosicion() + ",");
						
						writer.print(posicionLocal.getGolesFavor() + ",");
						writer.print(posicionLocal.getGolesContra() + ",");
						writer.print(posicionVisitante.getGolesFavor() + ",");
						writer.print(posicionVisitante.getGolesContra() + ",");
						if(posicionLocal.getPartidosJugadosCasa() != 0)
							writer.print((posicionLocal.getPartidosGanadosCasa() / (float)posicionLocal.getPartidosJugadosCasa()) + ",");
						else
							writer.print("0.0,");
						if (posicionLocal.getPartidosJugadosFuera() != 0)
							writer.print(posicionLocal.getPartidosGanadosFuera() / (float)posicionLocal.getPartidosJugadosFuera() + ",");
						else
							writer.print("0.0,");
						writer.print(partido.getGolesLocal() + ",");
						writer.print(partido.getGolesVisitante() + ",");
						if (partido.getGolesLocal() > partido.getGolesVisitante())
							writer.print("1");
						else if (partido.getGolesLocal() < partido.getGolesVisitante())
							writer.print("2");
						else writer.print("X");
						
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
}
