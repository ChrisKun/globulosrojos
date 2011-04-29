package states;

import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.WorldAPI;
import grupo14.players.*;

public class PosesionContrarioConPeligro implements MatchState{

	@Override
	public int accionARealizar(WorldAPI worldAPI, String role) {
		if(role.equals("portero"))
		{
			Vec2 vector = worldAPI.getOurGoal();
			worldAPI.setSteerHeading(vector.t);
			worldAPI.setSpeed(1.0);
			Vec2 vector2 = worldAPI.getOpponentsGoal();
			worldAPI.setSteerHeading(vector2.t);
			//me adelanto un poquito (Se puede hacer esto?)
			worldAPI.setSpeed(0.2);
			
		}
		if(role.equals("defensa"))
		{
			Acciones.taparPorteria(worldAPI);
//			{
//				if(worldAPI.isBlocking(worldAPI.getPosition()))
//				{
//					worldAPI.avoidCollisions();
//				}
//				else{
//				Vec2 vector = worldAPI.getBall();
//				worldAPI.setSteerHeading(vector.t);
//				worldAPI.setSpeed(1.0);
//				}
//			}
		}
		if(role.equals("delantero"))
		{
			Acciones.correrADefensa(worldAPI);
		}
		return 0;
	}

	@Override
	public String getStateName() {
		return "PosesionContrarioConPeligro";
	}
}
