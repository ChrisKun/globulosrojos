package states;

import grupo14.players.*;

public class PosesionContrario implements MatchState{

	@Override
	public int accionARealizar() {
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
