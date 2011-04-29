package states;

import teams.rolebased.WorldAPI;
import grupo14.players.MatchState;

/**
 * El contrario tiene el balon en su campo
 * Todos se colocan en su posicion menos el delantero tocapelotas que va hacia el balon todo el rato
 * @author markel
 *
 */
public class PosesionContrarioEnSuCampo implements MatchState{

	@Override
	public int accionARealizar(WorldAPI worldAPI, String role) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStateName() {
		// TODO Auto-generated method stub
		return null;
	}

}
