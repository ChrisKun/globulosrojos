package parserResultados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

public class ParserResText {
	
	private BufferedReader reader = null;
	
	private Temporada temporada;
	
	public ParserResText() {
	}
	
	public void readInfo(String path) throws IOException {
		
		temporada = new Temporada();
		
		// Abrimos el fichero para lectura
		File file = new File(path);
		FileReader fr = new FileReader(file);
		reader = new BufferedReader(fr);
		
		String linea;
		int numPartido = 0;
		Partido p = null;
		Jornada j = null;
		System.out.println("Leyendo de "+ path);
		while ((linea = reader.readLine()) != null) {
			if (linea.indexOf("<tr class=\"FondoFilaCalendario\"") != -1) {
				p = new Partido();
				reader.readLine();
				p.setEquipoLocal(reader.readLine().trim());
				reader.readLine();
				reader.readLine();
				reader.readLine();
				reader.readLine();
				reader.readLine();
				p.setGolesLocal(Integer.parseInt(reader.readLine().trim()));
				reader.readLine();
				reader.readLine();
				p.setGolesVisitante(Integer.parseInt(reader.readLine().trim()));
				reader.readLine();
				reader.readLine();
				reader.readLine();
				reader.readLine();
				reader.readLine();
				p.setEquipoVisitante(reader.readLine().trim());
				
				if (numPartido == 0) j = new Jornada();
				j.add(p);
				
				numPartido++;
				if (numPartido == 10) {
					temporada.add(j);
					numPartido = 0;
				}
			}
		}
		
		fr.close();
	}
	
	public void writeInfo(String path) throws IOException {
		FileWriter file = new FileWriter(path);
        PrintWriter writer = new PrintWriter(file);

        Iterator<Jornada> itTemporada = temporada.iterator();
        
        System.out.println("Escribiendo a "+ path);
        
        while (itTemporada.hasNext()) {
        	Jornada jornada = itTemporada.next();
        	Collection<Partido> cJornada = jornada.values();
        	
        	Iterator<Partido> itPartido = cJornada.iterator();
        	while (itPartido.hasNext()) {
        		Partido partido = itPartido.next();
        		writer.print(partido.getEquipoLocal() + ", ");
        		writer.print(partido.getGolesLocal() + ", ");
        		writer.print(partido.getGolesVisitante() + ", ");
        		writer.print(partido.getEquipoVisitante());
        		writer.println();
        	}
        	writer.println("----------------------------");
        }
        writer.close();
        file.close();
	}
}
