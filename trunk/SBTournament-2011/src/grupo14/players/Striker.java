package grupo14.players;

import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Striker extends Role {

	String role="delantero";
	public MatchStateUtils matchStateUtils;
	
	@Override
	public int configure() {
		worldAPI.setDisplayString("Delantero");
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
		matchStateUtils.matchState.accionARealizar(worldAPI,role);
		
		
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
