package grupo14.states;

import teams.rolebased.WorldAPI;
import grupo14.players.MatchState;

/**
 * El contrario tiene el balon y esta en nuestro campo
 * El delantero tocapelotas de queda arriba
 * Va a la segunda fila de octantes y bloquea a alguien alli
 * El defensa bloquea al jugador que tenga el balon
 * EL mega defensa va a por el balon
 * @author markel
 *
 */
public class PosesionContrarioNuestroCampo  implements MatchState{

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
