package principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import sistema.Sistema;
import utils.ColaPrioridad;
import sistema.Game;

import jcolibri.cbrcore.CBRCase;

public class MejoresJuegos {
	
	// Tabla de valoraciones media
	private static HashMap<Integer, Pair<Integer, Float>> tvm = null;
	
	private MejoresJuegos() {}
	
	public static HashMap<Integer, Pair<Integer, Float>> getInstance() {
		if (tvm == null)
			tvm = new HashMap<Integer, Pair<Integer, Float>>();
		return tvm;
	}
	
	public static void addValoracion(Integer gameId, Float valoracion) {
		/* Construimos una tabla con la valoración media de cada juego
		 * Key : Integer - El identificador del juego)
		 * Value : Pair<Integer, Float> - Un par que indica el 	A) el número de usuarios que han valorado este juego
		 * 														B) la valoración media
		 */
		
		// Si el juego no existe en la tabla de valoraciones media (TVM) se introduce
		if (!tvm.containsKey(gameId))
			tvm.put(gameId, new Pair<Integer, Float>(1, valoracion));
		else {
			/* Recalculamos la valoración media para el juego dado
			 * newV = ((oldV * n) + v) / (n+1)
			*/
			Pair<Integer, Float> entry = tvm.get(gameId);
			entry.elem1 = ((entry.elem1 * entry.elem0) +
							valoracion) / (entry.elem0 + 1); 
			entry.elem0 = entry.elem0 + 1;
			
			// Borramos la entrada de la tabla de la antigua valoración media
			tvm.remove(gameId);
			// Introducimos el nuevo par con la valoración actualizada
			tvm.put(gameId, entry);
		}
	}
	
	public static ArrayList<CBRCase> getMejoresJuegos(int n) {				
		// Construimos una cola de prioridad para sacar los n mejores juegos
		ColaPrioridad<ComparablePair<Float, Integer>> colaMJ = new ColaPrioridad<ComparablePair<Float, Integer>>(n);
		
		Set<Entry<Integer, Pair<Integer, Float>>> tabla = tvm.entrySet();
		/* Por cada entrada de la tabla de valoraciones media introducimos el par <Valoracion, gameID>
		 * en la cola, ésta se encarga de mirar si es realmente necesario introducir el par o no. 
		 */
		for (Entry<Integer, Pair<Integer, Float>> entry : tabla) {
			ComparablePair<Float, Integer> cp = new ComparablePair<Float, Integer>(entry.getValue().elem1, entry.getValue().elem0);
			colaMJ.add(cp);
		}
		
		// Convertimos la cola de prioridad en un ArrayList de <Float, Integer>
		ArrayList<ComparablePair<Float, Integer>> listaMJ = colaMJ.toArrayList();
		
		// Extraemos la información de los juegos dada la lista de los mejores
		ArrayList<CBRCase> mejoresJuegos = new ArrayList<CBRCase>();
		for (CBRCase c : Sistema.getCBjuegosInstance().getCases()) {
			// Si el identificador coincide con alguno de los del array de mejores juegos lo metemos
			if (listaMJ.contains(((Game)c.getDescription()).getgameId()))
				mejoresJuegos.add(c);
		}

 		return mejoresJuegos;
	}
}

class Pair<T1, T2> {
	public T1 elem0;
	public T2 elem1;
	
	public Pair() {}
	
	public Pair(T1 elem0, T2 elem1) {
		this.elem0 = elem0;
		this.elem1 = elem1;
	}
}

class ComparablePair<T1 extends Comparable, T2> extends Pair<T1, T2> implements Comparable{
	
	public ComparablePair(T1 elem0, T2 elem1) {
		this.elem0 = elem0;
		this.elem1 = elem1;
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof ComparablePair)
			return this.elem0.compareTo(((ComparablePair)o).elem0);
		else return 0;		
	}
}
