package states;

import teams.rolebased.WorldAPI;
import grupo14.players.MatchState;

/**
 * Tenemos el balon en nuestro campo
 * El portero esta en su posicion
 * El mega defensa si no tiene el balon se coloca un poco por detras, en caso contrario chuta hacia delante
 * El defensa si tiene el balon chuta hacia delante y si no que se ponga a la par del mega defensa
 * El delantero si tiene el balon chuta hacia delante y si no que se coloque en el centro del campo
 * El delantero tocapelotas si tiene el balon chuta hacia delante y sino que suba al ataque
 * @author markel
 *
 */
public class PosesionNuestraEnNuestroCampo implements MatchState {

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
