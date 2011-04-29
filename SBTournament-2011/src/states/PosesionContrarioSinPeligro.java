package states;

import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.WorldAPI;
import grupo14.players.*;

public class PosesionContrarioSinPeligro implements MatchState{

	@Override
	public int accionARealizar(WorldAPI worldAPI, String role) {
		if(role.equals("portero"))
		{
			Vec2 vector = worldAPI.getOurGoal();
			worldAPI.setSteerHeading(vector.t);
			worldAPI.setSpeed(1.0);
			Vec2 vector2 = worldAPI.getOpponentsGoal();
			worldAPI.setSteerHeading(vector2.t);
		}
		if(role.equals("defensa"))
		{
//			if(worldAPI.blocked())
//			{
//				worldAPI.avoidCollisions();
//			}
//			else{
//			Vec2 vector = worldAPI.getBall();
//			worldAPI.setSteerHeading(vector.t);
//			worldAPI.setSpeed(1.0);
//			}
			Acciones.bloquearContrario(worldAPI);
		}
		if(role.equals("delantero"))
		{
//			Vec2 vector = worldAPI.getBall();
//			worldAPI.setSteerHeading(vector.t);
//			worldAPI.setSpeed(1.0);
			Acciones.desmarcarse(worldAPI);
		}
		return 0;
	}

	@Override
	public String getStateName() {
		return "PosesionContrarioSinPeligro";
	}
}
