package grupo14.aprendizaje.CBR.caseComponents;

import java.util.ArrayList;

import grupo14.aprendizaje.CBR.TeamActions;
import grupo14.players.PlayerActions;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;
import jcolibri.extensions.classification.ClassificationSolution;

/**
 * Clase que especifica lo que hay que hacer en cada caso, o lo que se supone que hay que hacer
 * @author markel
 *
 */
public class SolucionCaso implements ClassificationSolution{
	
	/**
	 * ArrayList que almacena la acción a tomar por los cinco jugadores (por lo tengo el ArrayList tendra 5 posiciones)
	 */
	private TeamActions playerActions;
	
	public SolucionCaso()
	{
		this.playerActions = new TeamActions();
	}
	
	@Override
	public Attribute getIdAttribute() {
		return new Attribute("playerActions", this.getClass());
	}

	@Override
	public Object getClassification() {
		return this.playerActions;
	}

	public ArrayList<PlayerActions> getPlayerActions() {
		return playerActions.getPlayerActions();
	}

	public void setPlayerActions(ArrayList<PlayerActions> playerActions) {
		this.playerActions.setPlayerActions(playerActions);
	}
	
	
}
