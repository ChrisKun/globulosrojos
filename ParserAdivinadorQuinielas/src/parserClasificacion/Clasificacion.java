package parserClasificacion;

import java.util.ArrayList;
import java.util.Collection;

public class Clasificacion {

	private ArrayList<Posicion> posiciones;
	
	public Clasificacion() {
		posiciones = new ArrayList<Posicion>();
	}
	
	public void add(Posicion posicion) {
		this.posiciones.add(posicion);
	}
	
	public ArrayList<Posicion> getPosiciones()
	{
		return this.posiciones;
	}
	
	public Posicion getPosicionByName(String name)
	{
		for(Posicion posicion: this.posiciones)
			if(posicion.getEquipo().equals(name))
			{
				return posicion;
			}
		return null;
	}
}
