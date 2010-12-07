package grupo;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import jcolibri.cbrcore.CBRCase;

import sistema.Perfil;
import sistema.Sistema;

public class ListaUsuariosTableModel implements TableModel{

	private static final int SELECCION = 0;
	private static final int NOMBRE = 1;
	private String[] columnas = {"Checked", "Nombre"};
	
	private ArrayList<Boolean> seleccionados;
	private ArrayList<CBRCase> usuarios;
	
	public ListaUsuariosTableModel() {
		seleccionados = new ArrayList<Boolean>();
		usuarios = new ArrayList<CBRCase>();
		Collection <CBRCase> c = Sistema.getCBusuariosInstance().getCases();
		for (CBRCase cbrCase : c) {
			seleccionados.add(false);
			usuarios.add(cbrCase);
		}
	}
	
	public ArrayList<CBRCase> getUsuarios() {
		return usuarios;
	}
	
	public ArrayList<Boolean> getSeleccionados() {
		return seleccionados;
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
		return usuarios.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Queda transformar usuarios[rowIndex] al nick del usuario
		if (columnIndex == SELECCION)
			return seleccionados.get(rowIndex);
		else return ((Perfil)usuarios.get(rowIndex).getDescription()).getNickName();
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
