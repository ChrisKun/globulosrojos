package sistema;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class JuegosAdquiridos {

	private HashMap<String, Float> juegos;
	
	public JuegosAdquiridos() {
		juegos = new HashMap<String, Float>();
	}
	
	public void add(String nombre) {
		juegos.put(nombre, (float)0);
	}
	
	public void add(String nombre, Float puntuacion) {
		juegos.put(nombre, puntuacion);
	}
	
	public Float get(String nombre) {
		return juegos.get(nombre);
	}
	
	public void remove(String nombre) {
		juegos.remove(nombre);
	}
	
	public Iterator<Entry<String, Float>> iterator (){
		return juegos.entrySet().iterator();
	}
	
	public int size() {
		return juegos.size();
	}
}
