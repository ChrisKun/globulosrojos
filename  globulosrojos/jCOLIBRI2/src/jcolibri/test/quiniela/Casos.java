package jcolibri.test.quiniela;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;
import java.lang.Double;

public class Casos implements CaseComponent
{
	String caseId;
	String equipoLocal;
	String equipoVisitante;
	//el resultado estara en la solucion no?
	//String resultado;  
	Integer diferenciaPuntos;
	Integer posicionEquipoLocal;
	Integer posicionEquipoVisitante;
	Integer golesAFavor;
	Integer golesEnContra;
	// partidos_ganados/partidos_jugados del jugador 1 en casa
	Integer porcentajeGanagadosLocal;	
	// partidos_ganados/partidos_jugados del jugador 2 como visitante
	Integer porcentajeGanagadosVisitante;
	
	
	public Attribute getIdAttribute()
	{
		return new Attribute ("caseId", Casos.class);
	}
	
	public String toString()
	{
		return caseId+", "+equipoLocal+", "+equipoVisitante+", "+diferenciaPuntos+", "+posicionEquipoLocal+", "+posicionEquipoVisitante+", "+golesAFavor+", "+golesEnContra+", "+porcentajeGanagadosLocal+", "+porcentajeGanagadosVisitante;
	}

	public String getEquipoLocal() {
		return equipoLocal;
	}

	public void setEquipoLocal(String equipoLocal) {
		this.equipoLocal = equipoLocal;
	}

	public String getEquipoVisitante() {
		return equipoVisitante;
	}

	public void setEquipoVisitante(String equipoVisitante) {
		this.equipoVisitante = equipoVisitante;
	}

//	public String getResultado() {
//		return resultado;
//	}
//
//	public void setResultado(String resultado) {
//		this.resultado = resultado;
//	}

	public Integer getDiferenciaPuntos() {
		return diferenciaPuntos;
	}

	public void setDiferenciaPuntos(Integer diferenciaPuntos) {
		this.diferenciaPuntos = diferenciaPuntos;
	}

	public Integer getPosicionEquipoLocal() {
		return posicionEquipoLocal;
	}

	public void setPosicionEquipoLocal(Integer posicionEquipoLocal) {
		this.posicionEquipoLocal = posicionEquipoLocal;
	}

	public Integer getPosicionEquipoVisitante() {
		return posicionEquipoVisitante;
	}

	public void setPosicionEquipoVisitante(Integer posicionEquipoVisitante) {
		this.posicionEquipoVisitante = posicionEquipoVisitante;
	}

	public Integer getGolesAFavor() {
		return golesAFavor;
	}

	public void setGolesAFavor(Integer golesAFavor) {
		this.golesAFavor = golesAFavor;
	}

	public Integer getGolesEnContra() {
		return golesEnContra;
	}

	public void setGolesEnContra(Integer golesEnContra) {
		this.golesEnContra = golesEnContra;
	}

	public Integer getPorcentajeGanagadosLocal() {
		return porcentajeGanagadosLocal;
	}

	public void setPorcentajeGanagadosLocal(Integer porcentajeGanagadosLocal) {
		this.porcentajeGanagadosLocal = porcentajeGanagadosLocal;
	}

	public Integer getPorcentajeGanagadosVisitante() {
		return porcentajeGanagadosVisitante;
	}

	public void setPorcentajeGanagadosVisitante(Integer porcentajeGanagadosVisitante) {
		this.porcentajeGanagadosVisitante = porcentajeGanagadosVisitante;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	
}
