package parserClasificacion;

public class Posicion {

	private String equipo;
	private int posicion;
	private int puntos;
	private int golesFavor;
	private int golesContra;
	private int partidosGanadosCasa;
	private int partidosGanadosFuera;
	private int partidosJugadosCasa;
	private int partidosJugadosFuera;
	
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public int getGolesFavor() {
		return golesFavor;
	}
	public void setGolesFavor(int golesFavor) {
		this.golesFavor = golesFavor;
	}
	public int getGolesContra() {
		return golesContra;
	}
	public void setGolesContra(int golesContra) {
		this.golesContra = golesContra;
	}
	public int getPartidosGanadosCasa() {
		return partidosGanadosCasa;
	}
	public void setPartidosGanadosCasa(int partidosGanadosCasa) {
		this.partidosGanadosCasa = partidosGanadosCasa;
	}
	public int getPartidosGanadosFuera() {
		return partidosGanadosFuera;
	}
	public void setPartidosGanadosFuera(int partidosGanadosFuera) {
		this.partidosGanadosFuera = partidosGanadosFuera;
	}
	public void setPartidosJugadosCasa(int partidos) {
		this.partidosJugadosCasa = partidos;
		
	}
	public void setPartidosJugadosFuera(int partidos) {
		this.partidosJugadosFuera = partidos;
		
	}
	public int getPartidosJugadosCasa() {
		return this.partidosJugadosCasa;
	}
	public int getPartidosJugadosFuera() {
		return this.partidosJugadosFuera;
	}
}
