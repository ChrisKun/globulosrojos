package grupo14.aprendizaje.CBR.caseComponents;

import jcolibri.cbrcore.Attribute;
import jcolibri.extensions.classification.ClassificationSolution;

/**
 * Clase que especifica lo que hay que hacer en cada caso, o lo que se supone que hay que hacer
 * @author markel
 *
 */
public class SolucionCaso implements ClassificationSolution{
	
	/**
	 * String que almacena el estado al que paso el equipo en el caso actual
	 */
	private String newState;
	
	public SolucionCaso()
	{}
	
	@Override
	public Attribute getIdAttribute() {
		return new Attribute("newState", this.getClass());
	}

	@Override
	public Object getClassification() {
		return this.newState;
	}

	public String getNewState() {
		return newState;
	}

	public void setNewState(String newState) {
		this.newState = newState;
	}

}
