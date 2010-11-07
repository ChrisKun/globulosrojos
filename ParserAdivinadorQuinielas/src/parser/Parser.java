package parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import caseCreator.CaseCreator;

import parserClasificacion.ParserClasificacion;
import parserResultados.ParserResultados;

public class Parser {

	private static final int TEMPORADA_ACTUAL = 110;
	private static final int ULTIMA_JORNADA = 10;
	
	public static void main(String[] args) {
		ParserClasificacion parserC = new ParserClasificacion();
		ParserResultados p = new ParserResultados();
		try {

			// Se parsean los datos de las clasificaciones
			for (int temporada = 107; temporada < 110; temporada++) {
				System.out.print("Extrayendo la información de la temporada " + temporada + " [Clasificaciones]: |");
				
				FileWriter file = new FileWriter("Ficheros/ClasificacionesTemp" + temporada, false);
		        PrintWriter writer = new PrintWriter(file);
		        
				parserC.resetFile(writer);
				
				parserC.parse("http://www.lfp.es/?tabid=154&Controltype=detcla&g=1&t=" + temporada + "&j=1");
				parserC.arreglarTildes();
				parserC.escribirPrimeraClasificacion(writer);
				for (int jornada = 1; jornada < 38; jornada++) {
					System.out.print("-");
					parserC.parse("http://www.lfp.es/?tabid=154&Controltype=detcla&g=1&t=" + temporada + "&j=" + jornada);
					parserC.arreglarTildes();
					parserC.writeInfo(writer);
					parserC.writeSeparator(writer);
				}
				
				System.out.println("|");
				
				writer.close();
				file.close();
			}
			System.out.println("Clasificaciones parseadas");
			
			//Se parsean los datos de los resultados
			for (int temporada = 107; temporada < 110; temporada++) {
				System.out.print("Extrayendo la información de la temporada " + temporada + " [Resultados]: |");
				
				FileWriter file = new FileWriter("Ficheros/ResultadosTemp" + temporada, false);
	        	PrintWriter writer = new PrintWriter(file);	
	        	
	        	p.parse("http://www.lfp.es/?tabid=154&Controltype=cal&g=1&t=" + temporada);
	        	p.writeInfo(writer);
	        	
	        	System.out.println('|');
	        	
	        	writer.close();
	        	file.close();
			}
			
			System.out.println("Resultados parseados");
			
			//TODO Temporada actual
			System.out.print("Extrayendo la información de la temporada " + TEMPORADA_ACTUAL + " [Clasificaciones]: |");
			
			FileWriter file = new FileWriter("Ficheros/ClasificacionesTemp" + TEMPORADA_ACTUAL, false);
	        PrintWriter writer = new PrintWriter(file);
	        
			parserC.resetFile(writer);
			
			parserC.parse("http://www.lfp.es/?tabid=154&Controltype=detcla&g=1&t=" + TEMPORADA_ACTUAL + "&j=1");
			parserC.arreglarTildes();
			parserC.escribirPrimeraClasificacion(writer);
			for (int jornada = 1; jornada < ULTIMA_JORNADA; jornada++) {
				System.out.print("-");
				parserC.parse("http://www.lfp.es/?tabid=154&Controltype=detcla&g=1&t=" + TEMPORADA_ACTUAL + "&j=" + jornada);
				parserC.arreglarTildes();
				parserC.writeInfo(writer);
				parserC.writeSeparator(writer);
			}
			
			System.out.println("|");
			
			writer.close();
			file.close();
			
			System.out.print("Extrayendo la información de la temporada " + TEMPORADA_ACTUAL + " [Resultados]: |");
			
			file = new FileWriter("Ficheros/ResultadosTemp" + TEMPORADA_ACTUAL, false);
        	writer = new PrintWriter(file);	
        	
        	p.parse("http://www.lfp.es/?tabid=154&Controltype=cal&g=1&t=" + TEMPORADA_ACTUAL);
        	p.writeInfo(writer);
        	
        	System.out.println('|');
        	
        	writer.close();
        	file.close();
			
			//Se crea el fichero de los casos juntando los parses de clasificaciones y resultados
			CaseCreator creator = new CaseCreator();
			creator.juntarInfo();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}