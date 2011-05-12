package grupo14.players;

import grupo14.states.Heroica;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;
import EDU.gatech.cc.is.util.Vec2;

public class FuckingStriker extends Role {

	String role="delanteroTocapelotas";
	public MatchStateUtils matchStateUtils;
	@Override
	public int configure() {
		worldAPI.setDisplayString("Delantero");	
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
		matchStateUtils.matchState.accionARealizar(worldAPI,role);
		
		
		return WorldAPI.ROBOT_OK;
	}
}
