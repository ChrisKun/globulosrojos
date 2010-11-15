package gui;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class TablaKNNModel implements TableModel{

	private static final int NUMERO_CASO = 0;
	private static final int EQUIPO_LOCAL = 1;
	private static final int EQUIPO_VISITANTE = 2;
	private static final int PUNTOS_LOCAL = 3;
	private static final int PUNTOS_VISITANTE = 4;
	private static final int DIFERENCIA_PUNTOS = 5;
	
	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getColumnName(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
