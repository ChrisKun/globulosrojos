package grupo14.aprendizaje.CBR;

import grupo14.players.PlayerActions;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

/**
 * Clase que especifica lo que hay que hacer en cada caso, o lo que se supone que hay que hacer
 * @author markel
 *
 */
public class SolucionCaso implements CaseComponent{

	private PlayerActions player1Action;
	private PlayerActions player2Action;
	private PlayerActions player3Action;
	private PlayerActions player4Action;
	private PlayerActions player5Action;
	
	@Override
	public Attribute getIdAttribute() {
		// TODO Falta implementarlo
		return new Attribute("", this.getClass());
	}

	public PlayerActions getPlayer1Action() {
		return player1Action;
	}

	public void setPlayer1Action(PlayerActions player1Action) {
		this.player1Action = player1Action;
	}

	public PlayerActions getPlayer2Action() {
		return player2Action;
	}

	public void setPlayer2Action(PlayerActions player2Action) {
		this.player2Action = player2Action;
	}

	public PlayerActions getPlayer3Action() {
		return player3Action;
	}

	public void setPlayer3Action(PlayerActions player3Action) {
		this.player3Action = player3Action;
	}

	public PlayerActions getPlayer4Action() {
		return player4Action;
	}

	public void setPlayer4Action(PlayerActions player4Action) {
		this.player4Action = player4Action;
	}

	public PlayerActions getPlayer5Action() {
		return player5Action;
	}

	public void setPlayer5Action(PlayerActions player5Action) {
		this.player5Action = player5Action;
	}
}
