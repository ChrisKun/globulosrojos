package states;

import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.WorldAPI;
import grupo14.players.Defender;
import grupo14.players.Goalkeeper;
import grupo14.players.MatchState;
import grupo14.players.Striker;

public class UltimoHombreContrario implements MatchState{

	@Override
	public int accionARealizar(WorldAPI worldAPI) {
		if(this.getClass().equals(Defender.class))
		{
			
		}
		if(this.getClass().equals(Goalkeeper.class))
		{
			Vec2 vector = worldAPI.getBall();
			worldAPI.setSteerHeading(vector.t);
			worldAPI.setSpeed(1.0);
		}
		if(this.getClass().equals(Striker.class))
		{
			
		}
		return 0;
	}

}
