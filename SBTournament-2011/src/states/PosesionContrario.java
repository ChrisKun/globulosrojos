package states;

import teams.rolebased.WorldAPI;
import grupo14.players.*;

public class PosesionContrario implements MatchState{

	@Override
	public int accionARealizar(WorldAPI worldAPI) {
		if(this.getClass().equals(Defender.class))
		{
			
		}
		if(this.getClass().equals(Goalkeeper.class))
		{
			
		}
		if(this.getClass().equals(Striker.class))
		{
			
		}
		return 0;
	}
}
