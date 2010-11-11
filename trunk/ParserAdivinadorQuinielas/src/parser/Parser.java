package parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import caseCreator.CaseCreator;

import parserClasificacion.ParserClasificacion;
import parserResultados.ParserResultados;
import utils.CorrectorTildes;

public class Parser {

	public static void main(String[] args) {

		parsearClasificaciones();

		parsearResultados();

		// Se crea el fichero de los casos juntando los parses de
		// clasificaciones y resultados
		CaseCreator creator = new CaseCreator();
		creator.juntarInfo();
	}

	private static void parsearClasificaciones() {
		// Se cogue el separador dependiendo del sistema en el que estemos
		// Si es Windows '\' y si es Linux '/'
		// TODO En Linux funciona, falta probar en windows
		String separator = System.getProperty("file.separator");

		try {
			ParserClasificacion parserC = new ParserClasificacion();
			// Se parsean los datos de las clasificaciones
			for (int temporada = 107; temporada < 111; temporada++) {
				System.out.print("Extrayendo la información de la temporada "
						+ temporada + " [Clasificaciones]: |");

				FileWriter file = new FileWriter("Ficheros" + separator
						+ "ClasificacionesTemp" + temporada, false);
				PrintWriter writer = new PrintWriter(file);

				parserC.resetFile(writer);

				parserC
						.parse("http://www.lfp.es/?tabid=154&Controltype=detcla&g=1&t="
								+ temporada + "&j=1");
				parserC.setJornada(CorrectorTildes.arreglarTildes(parserC.getJornada()));
				parserC.escribirPrimeraClasificacion(writer);

				int jornada = 1;
				// Mientras haya exito parseando las clasificaciones almacenamos
				// y guardamos en el fichero.
				while (parserC
						.parse("http://www.lfp.es/?tabid=154&Controltype=detcla&g=1&t="
								+ temporada + "&j=" + jornada)
						&& jornada < 38) {
					System.out.print("-");
					parserC.setJornada(CorrectorTildes.arreglarTildes(parserC.getJornada()));
					parserC.writeInfo(writer);
					parserC.writeSeparator(writer);
					jornada++;
				}

				System.out.println("|");

				writer.close();
				file.close();
			}
			System.out.println("Clasificaciones parseadas");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void parsearResultados() {
		// Se cogue el separador dependiendo del sistema en el que estemos
		// Si es Windows '\' y si es Linux '/'
		String separator = System.getProperty("file.separator");

		ParserResultados p = new ParserResultados();
		try {
			// Se parsean los datos de los resultados
			for (int temporada = 107; temporada < 111; temporada++) {
				System.out.print("Extrayendo la información de la temporada "
						+ temporada + " [Resultados]: |");

				FileWriter file = new FileWriter("Ficheros" + separator
						+ "ResultadosTemp" + temporada, false);
				PrintWriter writer = new PrintWriter(file);

				p.parse("http://www.lfp.es/?tabid=154&Controltype=cal&g=1&t="
						+ temporada);
				p.writeInfo(writer);

				System.out.println('|');

				writer.close();
				file.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Resultados parseados");
	}
}