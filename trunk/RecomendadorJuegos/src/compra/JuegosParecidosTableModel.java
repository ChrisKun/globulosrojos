package compra;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import jcolibri.cbrcore.CBRCase;

import sistema.Game;
import sistema.Perfil;
import sistema.Sistema;

public class JuegosParecidosTableModel  implements TableModel{

	private static final int ID = 0;
	private static final int NOMBRE = 1;
	private String[] columnas = {"ID", "Nombre"};
	
	HashMap<Integer, String> juegosParecidos;
	
	public JuegosParecidosTableModel(Game juegoActual) {
		juegosParecidos = new HashMap<Integer, String>();
		HashMap<Integer, Integer> tablaTemp = new HashMap<Integer, Integer>();
		
		Collection<CBRCase> usuarios = Sistema.getCBusuariosInstance().getCases();
		for (CBRCase cbrCase : usuarios) {
			Perfil p = (Perfil) cbrCase.getDescription();
			// Si el usuario tiene una valoración buena para el juego seleccionado
			Float valoracion = p.getListaValoraciones().get(juegoActual.getgameId());
			if (valoracion != null && valoracion > 8.0) {
				/* Cojemos el primer juego bien valorado por ese usuario distinto al actual.
				 * Solo cojemos uno por usuario para tener diversidad.
				 */
				for (Entry<Integer, Float> entry : p.getListaValoraciones().entrySet()) {
					if (entry.getValue() > 9.0 && !entry.getKey().equals(juegoActual.getgameId())) {
						tablaTemp.put(entry.getKey(), null);
						break;
					}
				}
			}
			
			// Si hemos extraido suficientes juegos salimos
			if (tablaTemp.size() > 10)
				break;
		}
		
		/* Extraemos los nombres de los juegos parecidos extraidos.
		 * */
		for (CBRCase c : Sistema.getCBjuegosInstance().getCases()) {
			Game g = (Game)c.getDescription();
			if (tablaTemp.containsKey(g.getgameId())) {
				tablaTemp.remove(g.getgameId());
				juegosParecidos.put(g.getgameId(), g.getName());
				if (tablaTemp.size() <= 0)
					break;
			}
		}
	}
	
	@Override
	public void addTableModelListener(TableModelListener arg0) {	
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		if (arg0 == ID) return Integer.class;
		else return String.class;
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public String getColumnName(int arg0) {
		return columnas[arg0];
	}

	@Override
	public int getRowCount() {
		return juegosParecidos.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		Iterator<Entry<Integer, String>> iterator = juegosParecidos.entrySet().iterator();
		for (int i=0; i < arg0 && iterator.hasNext(); i++)
			iterator.next();
		
		if (iterator.hasNext()) {
			Entry<Integer, String> e = iterator.next();
			if (arg1 == ID)
			return  e.getKey();
			else if (arg1 == NOMBRE)
			return e.getValue();
		}

		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {		
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {		
	}

	public HashMap<Integer, String> getJuegos() {return juegosParecidos;}
}
