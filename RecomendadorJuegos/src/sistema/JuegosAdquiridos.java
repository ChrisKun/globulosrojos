package sistema;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class JuegosAdquiridos {

	private HashMap<String, Integer> juegos;
	
	public JuegosAdquiridos() {
		juegos = new HashMap<String, Integer>();
	}
	
	public void add(String nombre) {
		juegos.put(nombre, 0);
	}
	
	public void add(String nombre, Integer puntuacion) {
		juegos.put(nombre, puntuacion);
	}
	
	public Integer get(String nombre) {
		return juegos.get(nombre);
	}
	
	public void remove(String nombre) {
		juegos.remove(nombre);
	}
	
	public Iterator<Entry<String, Integer>> iterator (){
		return juegos.entrySet().iterator();
	}
	
	public int size() {
		return juegos.size();
	}
}
