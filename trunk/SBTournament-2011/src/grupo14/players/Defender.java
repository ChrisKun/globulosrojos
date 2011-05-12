package grupo14.players;

import grupo14.states.Catenaccio;
import grupo14.states.Heroica;
import grupo14.states.JuegoBrusco;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.states.PosesionContrarioNuestroCampo;
import grupo14.states.PosesionNuestraEnNuestroCampo;
import grupo14.states.PosesionNuestraEnSuCampo;
import grupo14.states.UltimoHombreContrario;
import grupo14.utils.CBRUtils;
import grupo14.utils.MatchStateUtils;
import grupo14.utils.fieldUtils;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;
import EDU.gatech.cc.is.util.Vec2;

public class Defender extends Role {

	public MatchStateUtils matchStateUtils;
	String role = "defensor";

	@Override
	public int configure() {
		// Para cuando se quiera ver el nombre de los jugadores en el simulador
		worldAPI.setDisplayString("Defensor");
		this.matchStateUtils = new MatchStateUtils();
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public int takeStep() {
		matchStateUtils.setMatchState(new PosesionContrarioEnSuCampo());
		matchStateUtils.getMatchState(this.worldAPI);
		matchStateUtils.matchState.accionARealizar(worldAPI, role);

		return WorldAPI.ROBOT_OK;
	}
}
