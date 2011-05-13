package grupo14.manager;

import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.ExecutionException;
import grupo14.aprendizaje.CBR.voting.Prediction;
import grupo14.players.Goalkeeper;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.utils.CBRUtils;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.TeamManager;
import teams.rolebased.WorldAPI;

public class Mourinho extends TeamManager {

	WorldAPI goalkeepersWorldAPI;
	CBRUtils CBR;
	public MatchStateUtils matchStateUtils;
	
	@Override
	public int configure() {
		//Start the CBR system
		this.CBR = new CBRUtils();
		//Start the match state utils class
		this.matchStateUtils = new MatchStateUtils();
		matchStateUtils.setMatchState(new PosesionContrarioEnSuCampo());
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public int takeStep() {
		//Esto se hace aqui porque en configure el portero todavia tiene su worldAPI a null
		if(this.goalkeepersWorldAPI == null)
			this.goalkeepersWorldAPI = ((Goalkeeper)super.robots[0]).getWorldApi();
		matchStateUtils.getMatchState(this.goalkeepersWorldAPI);
		//Si el sistema considera que debe crearse/leerse un nuevo caso
		if(this.CBR.needToCreateCase(this.goalkeepersWorldAPI, this.CBR.lastCase))
		{
			//Hay que crear un nuevo caso
			CBRCase caso = this.CBR.crearCaso(this.goalkeepersWorldAPI, this.matchStateUtils.matchState);
			//Se guarda el momento en que se ha leido el caso, para asegurar que no 
			//se cogen dos casos muy juntos
			this.CBR.lastCase = goalkeepersWorldAPI.getMatchTotalTime() - goalkeepersWorldAPI.getMatchRemainingTime();
			//Se hace la consulta a la base de casos para que esta nos devuelva el caso mejor
			//y tomar una decision
			try {
				//Recuperar un caso para aplicar. Obtendremos el estado al que pasar y una confianza
				Prediction prediccion = this.CBR.cbr.recuperarCaso(caso);
				//Se pasa al estado que dice el caso recuperado
				matchStateUtils.setMatchStateUsingName((String)prediccion.getClassification());
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return WorldAPI.ROBOT_OK;
	}

	
}
