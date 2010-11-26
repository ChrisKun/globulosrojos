package gui;

import java.util.Iterator;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import parserResultados.Jornada;
import parserResultados.Partido;
import utils.StringUtils;

public class TablaPartidosPredModel implements TableModel{

	private static final int SELECCION = 0;
	private static final int LOCAL = 1;
	private static final int VISITANTE = 2;
	
	private Jornada jornada;
	private Boolean seleccionados [];
	
	public TablaPartidosPredModel() {
		this.jornada = null;
		this.seleccionados = null;
	}
	
	public TablaPartidosPredModel(Jornada jornada) {
		this.jornada = jornada;
		this.seleccionados = new Boolean[10];
		for (int i=0; i < 10; i++) seleccionados[i] = new Boolean(false);
	}
	
	public boolean isEmpty() {return jornada == null || seleccionados == null;}
	
	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == SELECCION) return Boolean.class;
		else return String.class;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case SELECCION: return "Seleccion";
			case LOCAL: return "Local";
			case VISITANTE: return "Visitante";
			default: return null;
		}
	}

	@Override
	public int getRowCount() {
		if (jornada != null)
			return jornada.values().size();
		else return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex != SELECCION && jornada != null) {
			Iterator <Partido> it = jornada.values().iterator();
			int i = 0;
			for (; i < rowIndex; i++)
				it.next();
			
			Partido partido = it.next();
			if (columnIndex == LOCAL) return StringUtils.arreglarTildes(partido.getEquipoLocal());
			else return StringUtils.arreglarTildes(partido.getEquipoVisitante());
		}
		else if (seleccionados != null) return seleccionados[rowIndex];
		else return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == SELECCION) return true;
		else return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == SELECCION &&
				seleccionados != null) seleccionados[rowIndex] = (Boolean) value;
		
	}

}
