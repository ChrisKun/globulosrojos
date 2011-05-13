package grupo14.players;

import grupo14.aprendizaje.CBR.caseComponents.SolucionCaso;
import grupo14.states.Catenaccio;
import grupo14.states.Heroica;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.states.UltimoHombreContrario;
import grupo14.utils.CBRUtils;
import grupo14.utils.MatchStateUtils;
import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.ExecutionException;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;
import EDU.gatech.cc.is.util.Vec2;

public class Goalkeeper extends Role{
	private final double KEEPER_DEFENSE_LINE = 0;
	
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
