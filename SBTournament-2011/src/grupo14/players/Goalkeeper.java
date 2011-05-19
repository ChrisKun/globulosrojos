package grupo14.players;

import EDU.gatech.cc.is.util.Vec2;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.team.Ordenes;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.WorldAPI;

public class Goalkeeper extends X{
	
	String role="portero";
	Ordenes ordenesDeEquipo;
	
	MegaDefender defender;
	boolean soyPortero;

	@Override
	public int configure() {
		
		this.matchStateUtils = new MatchStateUtils();
		matchStateUtils.setMatchState(new PosesionContrarioEnSuCampo());
		soyPortero = true;
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
	}

	@Override
	public int takeStep() {
		//accion normal, ni neuronal ni cbr
		if (worldAPI.blocked()) {
			defender.setSoyPortero(true);
			//Acciones.evitarBloqueos(worldAPI);
		}
		else {
			defender.setSoyPortero(false);
			accionPortero();
		}

		
		worldAPI.setDisplayString("Portero");
		return WorldAPI.ROBOT_OK;
	}
	
	public WorldAPI getWorldApi()
	{
		return this.worldAPI;
	}
	
	public void accionPortero ()
	{
		Vec2 move = new Vec2(0,0);
		// if i'm outside the goal area go back toward the goal
		if(worldAPI.getOurGoal().r > 0.2)
		{
			move.sett( worldAPI.getOurGoal().t);
			move.setr( 1.0);
		}
		// if the ball is behind me try to kick it out
		else if( worldAPI.getBall().x * worldAPI.getFieldSide() > 0)
		{
			move.sett( worldAPI.getBall().t);
			move.setr( 1.0);
		}

		// stay between the ball and the goal
		else
		{
			if( worldAPI.getBall().y > 0)
				move.sety( 7);
			else
				move.sety( -7);

			move.setx( (double)worldAPI.getFieldSide());

			if( Math.abs( worldAPI.getBall().y) < 0.05)
				move.setr( 0.0);
			else
				move.setr( 1.0);
		}
		
		worldAPI.setSteerHeading(move.t);
		worldAPI.setSpeed(1.0);
	}

	public void setDefender(MegaDefender defender) {
		this.defender = defender;
	}

	public void setSoyPortero(boolean soyPortero) {
		this.soyPortero = soyPortero;
	}
	
	
}
