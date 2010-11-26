package gui;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import cbr.Casos;
import cbr.Solucion;

import jcolibri.method.retrieve.RetrievalResult;

public class TablaKNNModel implements TableModel{

	private static final String[] columnas = {"Nº Caso", "Local", "Visitante", "P.L.", "P.V.", "Dif. puntos", "Pos. Local",
		"Pos. Visitante", "G.F.L.", "G.C.L.", "G.F.V.", "G.C.V.", "G.L.", "G.V.", "Resultado"};
	
	private static final int NUMERO_CASO = 0;
	private static final int EQUIPO_LOCAL = 1;
	private static final int EQUIPO_VISITANTE = 2;
	private static final int PUNTOS_LOCAL = 3;
	private static final int PUNTOS_VISITANTE = 4;
	private static final int DIFERENCIA_PUNTOS = 5;
	private static final int POSICION_LOCAL = 6;
	private static final int POSICION_VISITANTE = 7;
	private static final int GOLES_FAVOR_LOCAL = 8;
	private static final int GOLES_CONTRA_LOCAL = 9;
	private static final int GOLES_FAVOR_VISITANTE = 10;
	private static final int GOLES_CONTRA_VISITANTE = 11;
	private static final int RENDIMIENTO_LOCAL = 12;
	private static final int RENDIMIENTO_VISITANTE = 13;
	private static final int RESULTADO = 14;

	private Collection<RetrievalResult> knn;
	
	public TablaKNNModel() {
		this.knn = null;
	}
	
	public TablaKNNModel(Collection<RetrievalResult> knn) {
		this.knn = knn;
	}
	
	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex) {
		case NUMERO_CASO:
		case PUNTOS_LOCAL:
		case PUNTOS_VISITANTE:
		case DIFERENCIA_PUNTOS:
		case POSICION_LOCAL:
		case POSICION_VISITANTE:
		case GOLES_FAVOR_LOCAL:
		case GOLES_CONTRA_LOCAL:
		case GOLES_FAVOR_VISITANTE:
		case GOLES_CONTRA_VISITANTE:
			return Integer.class;
		case EQUIPO_LOCAL:
		case EQUIPO_VISITANTE:
		case RESULTADO:
			return String.class;
		case RENDIMIENTO_LOCAL:
		case RENDIMIENTO_VISITANTE:
			return Double.class;
		}
		return null;
	}

	@Override
	public int getColumnCount() {
		return 15;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnas[columnIndex];
	}

	@Override
	public int getRowCount() {
		if (knn != null)
			return knn.size();
		else return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (knn != null) {
			Iterator<RetrievalResult> iterator = knn.iterator();
			Casos candidatoDesc;
			Solucion candidatoSolucion;
			for (int i = 0; i < rowIndex; i++)
				iterator.next();
			
			RetrievalResult candidato = iterator.next();
			candidatoDesc = (Casos) candidato.get_case().getDescription();
			candidatoSolucion = (Solucion) candidato.get_case().getSolution();
			
			switch(columnIndex) {
			case NUMERO_CASO: return candidatoDesc.getCaseId();
			case PUNTOS_LOCAL: return candidatoDesc.getPuntosEquipoLocal();
			case PUNTOS_VISITANTE: return candidatoDesc.getPuntosEquipoVisitante();
			case DIFERENCIA_PUNTOS: return candidatoDesc.getDiferenciaPuntos();
			case POSICION_LOCAL: return candidatoDesc.getPosicionEquipoLocal();
			case POSICION_VISITANTE: return candidatoDesc.getPosicionEquipoVisitante();
			case GOLES_FAVOR_LOCAL: return candidatoDesc.getGolesAFavorEquipoLocal();
			case GOLES_CONTRA_LOCAL: return candidatoDesc.getGolesEnContraEquipoLocal();
			case GOLES_FAVOR_VISITANTE: return candidatoDesc.getGolesAFavorEquipoVisitante();
			case GOLES_CONTRA_VISITANTE: return candidatoDesc.getGolesEnContraEquipoVisitante();
			case EQUIPO_LOCAL: return candidatoDesc.getEquipoLocal();
			case EQUIPO_VISITANTE: return candidatoDesc.getEquipoVisitante();
			case RESULTADO: return candidatoSolucion.getClassification().toString();
			case RENDIMIENTO_LOCAL: return candidatoDesc.getPorcentajeGanagadosLocal();
			case RENDIMIENTO_VISITANTE: return candidatoDesc.getPorcentajeGanagadosVisitante();	
			}
			return null;
		}
		else return null;
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
