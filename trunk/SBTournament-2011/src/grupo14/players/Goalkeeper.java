package grupo14.players;

import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Goalkeeper extends Role{
	
	public MatchStateUtils matchStateUtils;
	String role="portero";

	@Override
	public int configure() {
		
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
		//System.out.println("Portero en estado= "+estado);
		matchStateUtils.getMatchState(worldAPI);
		matchStateUtils.matchState.accionARealizar(worldAPI,role);
		
		return WorldAPI.ROBOT_OK;
	}
	
	public WorldAPI getWorldApi()
	{
		return this.worldAPI;
	}
}
