package grupo;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import jcolibri.cbrcore.CBRCase;

import sistema.Sistema;

public class ListaUsuariosTableModel implements TableModel{

	private static final int SELECCION = 0;
	private static final int NOMBRE = 1;
	private String[] columnas = {"Checked", "Nombre"};
	
	private ArrayList<Boolean> seleccionados;
	private CBRCase usuarios[];
	
	public ListaUsuariosTableModel() {
		seleccionados = new ArrayList<Boolean>();
		usuarios = (CBRCase[])Sistema.getCBusuariosInstance().getCases().toArray();
	}
	
	@Override
	public void addTableModelListener(TableModelListener arg0) {		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == SELECCION) return Boolean.class;
		else return String.class;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnas[columnIndex];
	}

	@Override
	public int getRowCount() {
		return usuarios.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Queda transformar usuarios[rowIndex] al nick del usuario
		if (columnIndex == SELECCION)
			return seleccionados.get(rowIndex);
		else return usuarios[rowIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == SELECCION)
			return true;
		else return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == SELECCION &&	seleccionados != null)
			seleccionados.set(rowIndex, (Boolean) value);		
	}

}
