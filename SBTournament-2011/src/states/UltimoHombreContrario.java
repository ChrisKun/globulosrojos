package states;

import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.WorldAPI;
import grupo14.players.Defender;
import grupo14.players.Goalkeeper;
import grupo14.players.MatchState;
import grupo14.players.Striker;

public class UltimoHombreContrario implements MatchState{

	@Override
	public int accionARealizar(WorldAPI worldAPI, String role) {
		System.out.println(role.getClass().toString());
		if(role.equals("defensor"))
		{
			{
				if(worldAPI.isBlocking(worldAPI.getPosition()))
				{
					worldAPI.avoidCollisions();
				}
				else{
				Vec2 vector = worldAPI.getBall();
				worldAPI.setSteerHeading(vector.t);
				worldAPI.setSpeed(0.2);
				}
			}
		}
		if(role.equals("portero"))
		{
			Vec2 vector = worldAPI.getBall();
			worldAPI.setSteerHeading(vector.t);
			worldAPI.setSpeed(1.0);
		}
		if(role.equals("delantero"))
		{
			Vec2 vector = new Vec2(0,0);
			worldAPI.setSteerHeading(vector.t);
			worldAPI.setSpeed(1.0);
		}
		return 0;
	}
	
	@Override
	public String getStateName(){
		return "UltimoHombreContrario";
	}

}
