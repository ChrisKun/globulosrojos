package states;

import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.WorldAPI;
import grupo14.players.Defender;
import grupo14.players.Goalkeeper;
import grupo14.players.MatchState;
import grupo14.players.Striker;

public class IrAlAtaque implements MatchState{

	@Override
	public int accionARealizar(WorldAPI worldAPI, String role) {
		System.out.println(role.getClass().toString());
		if(role.equals("defensor"))
		{
			
		}
		if(role.equals("portero"))
		{
			Vec2 vector = worldAPI.getBall();
			worldAPI.setSteerHeading(vector.t);
			worldAPI.setSpeed(1.0);
		}
		if(role.equals("delantero"))
		{
			Vec2 vector = worldAPI.getBall();
			worldAPI.setSteerHeading(vector.t);
			worldAPI.setSpeed(1.0);
		}
		return 0;
	}

}