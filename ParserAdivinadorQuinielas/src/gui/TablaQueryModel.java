package gui;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import cbr.Casos;

public class TablaQueryModel  implements TableModel{

	private Casos q;
	
	private static final int CAMPO = 0;
	private static final int VALOR = 1;
	
	private static final String[] campos = {"Equipo Local", "Equipo Visitante", "Puntos Local", "Puntos Visitante", "Diferencia Puntos",
		"Posicion Local", "Posicion Visitante", "Goles Favor Local", "Goles Contra Local", "Goles Favor Visitante", "Goles Contra Visitante",
		"Rendimiento Local", "Rendimiento Visitante"};
	
	public TablaQueryModel(Casos q) {
		this.q = q;
	}
	
	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case CAMPO: return "Campo";
		case VALOR: return "Valor";
		default: return null;
	}
	}

	@Override
	public int getRowCount() {
		if (q != null) return 13;
		else return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == CAMPO) return campos[rowIndex];
		else if (q != null){
			switch(rowIndex) {
			case 0: return q.getEquipoLocal();
			case 1: return q.getEquipoVisitante();
			case 2: return q.getPuntosEquipoLocal();
			case 3: return q.getPuntosEquipoVisitante();
			case 4: return q.getDiferenciaPuntos();
			case 5: return q.getPosicionEquipoLocal();
			case 6: return q.getPosicionEquipoVisitante();
			case 7: return q.getGolesAFavorEquipoLocal();
			case 8: return q.getGolesEnContraEquipoLocal();
			case 9: return q.getGolesAFavorEquipoVisitante();
			case 10: return q.getGolesEnContraEquipoVisitante();
			case 11: return q.getPorcentajeGanagadosLocal();
			case 12: return q.getPorcentajeGanagadosVisitante();
			}
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
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
