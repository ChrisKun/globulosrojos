package grupo14.players;

import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Defender extends Role {

	public MatchStateUtils matchStateUtils;
	String role = "defensor";

	@Override
	public int configure() {
		//Para cuando se quiera ver el nombre de los jugadores en el simulador
		worldAPI.setDisplayString("Defensor");
		this.matchStateUtils = new MatchStateUtils();
		matchStateUtils.setMatchState(new PosesionContrarioEnSuCampo());
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public int takeStep() {
		matchStateUtils.getMatchState(this.worldAPI);
		matchStateUtils.matchState.accionARealizar(worldAPI, role);

		return WorldAPI.ROBOT_OK;
	}
	
	/**
	 * Pone al jugador en el estado que se le pasa como parametro
	 * @param estado: Estado al que debe pasar el jugador
	 */
	public void pasaAEstado(MatchState estado){
		matchStateUtils.setMatchState(estado);
	}
	
	/**
	 * Ordena al jugador que realice la accion recibida como parámetro
	 * @param accion: Accion que debe realizar el jugador
	 */
	public void realizaAccion(String accion){
		
	}
}
