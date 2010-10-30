package parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import caseCreator.CaseCreator;

import parserClasificacion.ParserClasificacion;
import parserResultados.ParserResultados;

public class Parser {

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