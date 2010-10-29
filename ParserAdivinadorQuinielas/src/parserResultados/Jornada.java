package parserResultados;

import java.util.Collection;
import java.util.HashMap;

public class Jornada {

	private HashMap<String, Partido> tablaLocal;
	private HashMap<String, Partido> tablaVisitante;
	
	public Jornada() {
		tablaLocal = new HashMap<String, Partido>();
		tablaVisitante = new HashMap<String, Partido>();
	}
	
	public void add(Partido resultado) {
		tablaLocal.put(resultado.getEquipoLocal(), resultado);
		tablaVisitante.put(resultado.getEquipoVisitante(), resultado);
	}
	
	public Partido getResultadoPorEquipoLocal(String equipoLocal) {
		return tablaLocal.get(equipoLocal);
	}
	
	public Partido getResultadoPorEquipoVisitante(String equipoVisitante) {
		return tablaVisitante.get(equipoVisitante);
	}
	
	public Collection<Partido> values() {return tablaLocal.values();}
}
