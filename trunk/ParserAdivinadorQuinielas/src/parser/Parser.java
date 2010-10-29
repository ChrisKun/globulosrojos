package parser;

import java.io.IOException;

import caseCreator.CaseCreator;

import parserClasificacion.ParserClasificacion;
import parserResultados.ParserResText;

public class Parser {

	public static void main(String[] args) {
		ParserClasificacion parserC = new ParserClasificacion();
		ParserResText p = new ParserResText();
		try {

			// Se parsean los datos de las clasificaciones
			for (int temporada = 107; temporada < 110; temporada++) {
				parserC.resetFile("Ficheros/ClasificacionesTemp" + temporada);
				for (int jornada = 1; jornada < 38; jornada++) {
					parserC.parse("http://www.lfp.es/?tabid=154&Controltype=detcla&g=1&t="+ temporada + "&j=" + jornada);
					parserC.arreglarTildes();
					parserC.writeInfo("Ficheros/ClasificacionesTemp"+ temporada);
					parserC.writeSeparator("Ficheros/ClasificacionesTemp"+ temporada);
				}
			}
			System.out.println("Clasificaciones parseadas");
			
			//Se parsean los datos de los resultados
			p.readInfo("Ficheros/Temporada0708.htm");
			p.writeInfo("Ficheros/ResultadosTemporada0708(F).txt");
			p.readInfo("Ficheros/Temporada0809.htm");
			p.writeInfo("Ficheros/ResultadosTemporada0809(F).txt");
			p.readInfo("Ficheros/Temporada0910.htm");
			p.writeInfo("Ficheros/ResultadosTemporada0910(F).txt");

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