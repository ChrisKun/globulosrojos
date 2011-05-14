package grupo14.aprendizaje.redNeuronal.players;

import grupo14.players.Acciones.Accion;

public class MLPResult {
	
	private Accion action;
	private double confidence;
	
	public MLPResult(Accion action, double confidence) {
		this.action = action;
		this.confidence = confidence;
	}

	public Accion getAction() {
		return action;
	}

	public void setAction(Accion actionIndex) {
		this.action = actionIndex;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
	public String toString() {
		return action.toString() + ": " + confidence;
	}
	
	
}
