package cbr;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;
import java.lang.Double;

public class Casos implements CaseComponent
{
	String caseId;
	String equipoLocal;
	String equipoVisitante;
	Integer puntosEquipoLocal;
	Integer puntosEquipoVisitante;
	Integer diferenciaPuntos;
	Integer posicionEquipoLocal;
	Integer posicionEquipoVisitante;
	Integer golesAFavorEquipoLocal;
	Integer golesEnContraEquipoLocal;
	Integer golesAFavorEquipoVisitante;
	Integer golesEnContraEquipoVisitante;
	// partidos_ganados/partidos_jugados del jugador 1 en casa
	Double porcentajeGanagadosLocal;	
	// partidos_ganados/partidos_jugados del jugador 2 como visitante
	Double porcentajeGanagadosVisitante;
	Integer resultadoLocal;
	Integer resultadoVisitante;
	
	public Integer getPuntosEquipoLocal() {
		return puntosEquipoLocal;
	}

	public void setPuntosEquipoLocal(Integer puntosEquipoLocal) {
		this.puntosEquipoLocal = puntosEquipoLocal;
	}

	public Integer getPuntosEquipoVisitante() {
		return puntosEquipoVisitante;
	}

	public void setPuntosEquipoVisitante(Integer puntosEquipoVisitante) {
		this.puntosEquipoVisitante = puntosEquipoVisitante;
	}

	public Integer getGolesAFavorEquipoLocal() {
		return golesAFavorEquipoLocal;
	}

	public void setGolesAFavorEquipoLocal(Integer golesAFavorEquipoLocal) {
		this.golesAFavorEquipoLocal = golesAFavorEquipoLocal;
	}

	public Integer getGolesEnContraEquipoLocal() {
		return golesEnContraEquipoLocal;
	}

	public void setGolesEnContraEquipoLocal(Integer golesEnContraEquipoLocal) {
		this.golesEnContraEquipoLocal = golesEnContraEquipoLocal;
	}

	public Integer getGolesAFavorEquipoVisitante() {
		return golesAFavorEquipoVisitante;
	}

	public void setGolesAFavorEquipoVisitante(Integer golesAFavorEquipoVisitante) {
		this.golesAFavorEquipoVisitante = golesAFavorEquipoVisitante;
	}

	public Integer getGolesEnContraEquipoVisitante() {
		return golesEnContraEquipoVisitante;
	}

	public void setGolesEnContraEquipoVisitante(Integer golesEnContraEquipoVisitante) {
		this.golesEnContraEquipoVisitante = golesEnContraEquipoVisitante;
	}

	public Integer getResultadoLocal() {
		return resultadoLocal;
	}

	public void setResultadoLocal(Integer resultadoLocal) {
		this.resultadoLocal = resultadoLocal;
	}

	public Integer getResultadoVisitante() {
		return resultadoVisitante;
	}

	public void setResultadoVisitante(Integer resultadoVisitante) {
		this.resultadoVisitante = resultadoVisitante;
	}

	public Attribute getIdAttribute()
	{
		return new Attribute ("caseId", Casos.class);
	}
	
	public String toString()
	{
		return caseId+", "+equipoLocal+", "+equipoVisitante+", "+puntosEquipoLocal+", "+puntosEquipoVisitante+", "+
		diferenciaPuntos+", "+posicionEquipoLocal+", "+posicionEquipoVisitante+", "+golesAFavorEquipoLocal+", "+
		golesEnContraEquipoLocal+", "+golesAFavorEquipoVisitante+", "+
		golesEnContraEquipoVisitante+", "+porcentajeGanagadosLocal+", "+
		porcentajeGanagadosVisitante+", "+resultadoLocal+", "+resultadoVisitante;
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

	public Double getPorcentajeGanagadosLocal() {
		return porcentajeGanagadosLocal;
	}

	public void setPorcentajeGanagadosLocal(Double porcentajeGanagadosLocal) {
		this.porcentajeGanagadosLocal = porcentajeGanagadosLocal;
	}

	public Double getPorcentajeGanagadosVisitante() {
		return porcentajeGanagadosVisitante;
	}

	public void setPorcentajeGanagadosVisitante(Double porcentajeGanagadosVisitante) {
		this.porcentajeGanagadosVisitante = porcentajeGanagadosVisitante;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
	
}
