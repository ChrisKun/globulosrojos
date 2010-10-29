package parserResultados;

import java.util.ArrayList;
import java.util.Iterator;

public class Temporada {
	
	private ArrayList<Jornada> resultados; 
	
	public Temporada() {
		resultados = new ArrayList<Jornada>();
	}
	
	public void add(Jornada jornada) {
		resultados.add(jornada);
	}
	
	public Jornada get(int i) {
		return resultados.get(i);
	}
	
	public Iterator<Jornada> iterator() {return resultados.iterator();}
}
