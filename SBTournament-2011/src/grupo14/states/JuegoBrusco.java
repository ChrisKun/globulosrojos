package grupo14.states;

import teams.rolebased.WorldAPI;
import grupo14.players.Acciones;
import grupo14.players.MatchState;

/**
 * Todos los jugadores bloquean a todos los contrarios todo el rato
 * El delantero Tocapelotas va a por el balon
 * @author markel
 *
 */
public class JuegoBrusco implements MatchState {

	@Override
	public int accionARealizar(WorldAPI worldAPI, String role) {
		System.out.println(role.getClass().toString());
		if(role.equals("delanteroTocapelotas"))
		{
			Acciones.controlarLaPelota(worldAPI);
		}
		else
		{
			Acciones.bloquearContrario(worldAPI);
		}
		return 0;
	}

	@Override
	public String getStateName() {
		return "JuegoBrusco";
	}

}
