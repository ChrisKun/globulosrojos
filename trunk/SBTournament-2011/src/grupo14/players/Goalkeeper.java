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
	CBRUtils CBR;
	String role="portero";

	@Override
	public int configure() {
		
		this.CBR = new CBRUtils();
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
		String estado = matchStateUtils.getMatchState(this.worldAPI);
		System.out.println("Portero en estado= "+estado);
		matchStateUtils.matchState.accionARealizar(worldAPI,role);
		
		//Si el sistema considera que debe leerse un nuevo caso
		if(this.CBR.needToCreateCase(this.worldAPI, this.CBR.lastCase))
		{
			//Hay que crear un nuevo caso
			CBRCase caso = this.CBR.crearCaso(this.worldAPI, this.matchStateUtils.matchState);
			//Se guarda el momento en que se ha leido el caso, para asegurar que no 
			//se cogen dos casos muy juntos
			this.CBR.lastCase = worldAPI.getMatchTotalTime() - worldAPI.getMatchRemainingTime();
			//Se hace la consulta a la base de casos para que esta nos devuelva el caso mejor
			//y tomar una decision
			try {
				String recoveredState = this.CBR.cbr.recuperarCaso(caso);
				matchStateUtils.setMatchStateUsingName(recoveredState);
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return WorldAPI.ROBOT_OK;
	}
}
