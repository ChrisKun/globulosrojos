package grupo14.aprendizaje.CBR.caseComponents;

import jcolibri.cbrcore.CBRCase;

public class Caso extends CBRCase{

	private DescripcionCaso descripcion;
	private SolucionCaso solucion;
	private ResultadoCaso resultado;
	
	public DescripcionCaso getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(DescripcionCaso descripcion) {
		this.descripcion = descripcion;
	}
	public SolucionCaso getSolucion() {
		return solucion;
	}
	public void setSolucion(SolucionCaso solucion) {
		this.solucion = solucion;
	}
	public ResultadoCaso getResultado() {
		return resultado;
	}
	public void setResultado(ResultadoCaso resultado) {
		this.resultado = resultado;
	}
	public String toString(){
		return "Not yet Implemented";
	}
}
