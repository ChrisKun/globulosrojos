package grupo14.states;

import teams.rolebased.WorldAPI;
import grupo14.players.MatchState;

/**
 * Tenemos el balon en su campo
 * El portero se queda en su posicion
 * El mega defensa se queda al final de nuestro campo o un poco mas atras
 * El defensa pasa al ataque
 * El delantero que no tiene el balon bloquea al portero
 * El que tiene el balon que chite si puede o si no que vaya a por el balon 
 * @author markel
 *
 */
public class PosesionNuestraEnSuCampo implements MatchState {

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
