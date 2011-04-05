package grupo14.manager;

import grupo14.players.MatchState;
import states.PosesionContrario;
import teams.rolebased.TeamManager;
import teams.rolebased.WorldAPI;

public class Mourinho extends TeamManager {

	public static MatchState matchState;
	@Override
	public int configure() {
		// TODO Auto-generated method stub
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public int takeStep() {
		//si el balon lo tiene el contrario, estado=2, estado=3... 
		MatchState state = new PosesionContrario();
		matchState = state;
		return WorldAPI.ROBOT_OK;
	}
	public static MatchState getState()
	{
		return matchState;
	}
	
	
}
