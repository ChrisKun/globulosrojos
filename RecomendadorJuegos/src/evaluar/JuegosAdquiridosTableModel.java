package evaluar;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JSlider;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import sistema.JuegosAdquiridos;

public class JuegosAdquiridosTableModel implements TableModel{

	private static final int NOMBRE = 0;
	private static final int PUNTUACION = 1;
	private String[] columnas = {"Nombre", "Puntuación"};
	
	private JuegosAdquiridos juegos;
	
	public JuegosAdquiridosTableModel(JuegosAdquiridos juegos) {
		this.juegos = juegos;			
	}
	
	@Override
	public void addTableModelListener(TableModelListener arg0) {}

	@Override
	public Class<?> getColumnClass(int arg0) {
		if (arg0 == NOMBRE) return String.class;
		else return JSlider.class;
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
		return juegos.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		Iterator<Entry<String, Float>> iterator = juegos.iterator();
		for (int i=0; i < arg0 && iterator.hasNext(); i++)
			iterator.next();
		
		if (iterator.hasNext()) {
			if (arg1 == NOMBRE)
			return  iterator.next().getKey();
			else if (arg1 == PUNTUACION)
			return iterator.next().getValue();
		}

		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		if (arg1 == PUNTUACION) return true;
		else return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		String nombre = (String) getValueAt(arg1, 0);
		float antiguaPuntuacion = juegos.get(nombre);
		juegos.remove(nombre);
		float nuevoValor = antiguaPuntuacion;
		try {
			nuevoValor = Float.parseFloat((String)arg0);
		}
		catch (NumberFormatException e) {}
		if (nuevoValor > 10) nuevoValor = 10;
		if (nuevoValor < 0) nuevoValor = 0;
		juegos.add(nombre, nuevoValor);		
	}

}

