package grupo14.players;

import EDU.gatech.cc.is.util.Vec2;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.team.Ordenes;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Goalkeeper extends X{
	
	String role="portero";
	Ordenes ordenesDeEquipo;

	@Override
	public int configure() {
		
		this.matchStateUtils = new MatchStateUtils();
		matchStateUtils.setMatchState(new PosesionContrarioEnSuCampo());
		ordenesDeEquipo.getInstance();
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
	}

	@Override
	public int takeStep() {
		//accion normal, ni neuronal ni cbr
		accionPortero();
		
		//Ver si hay nueva orden de la red neuronal
		//if( HAY NUEVA ORDEN DE RED NEURONAL  )
			//Codigo Para Sergio
		//Ver si el entrenador ha ordenado un nuevo estado
		/*else*/if(this.ordenesDeEquipo.hayNuevoEstado())
			//Pasar al estado ordenado
			matchStateUtils.matchState = ordenesDeEquipo.pasarAEstado();
		else
		{
			matchStateUtils.getMatchState(this.worldAPI);
			matchStateUtils.matchState.accionARealizar(worldAPI, role);
		}
		
		return WorldAPI.ROBOT_OK;
	}
	
	public WorldAPI getWorldApi()
	{
		return this.worldAPI;
	}
	public void accionPortero ()
	{
		double ROBOT_RADIUS = 0.6;
		Vec2 move = new Vec2(0,0);
		// if the ball is behind me try to kick it out
		if( worldAPI.getBall().x * worldAPI.getFieldSide() > 0)
		{
			move.sett( worldAPI.getBall().t);
			move.setr( 1.0);
		}

		// if i'm outside the goal area go back toward the goal
		else if( (Math.abs(worldAPI.getOurGoal().x) > ROBOT_RADIUS * 1.4) ||
			 (Math.abs(worldAPI.getOurGoal().y) > ROBOT_RADIUS * 4.25) )

		{
			move.sett( worldAPI.getOurGoal().t);
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

			if( Math.abs( worldAPI.getBall().y) < ROBOT_RADIUS * 0.15)
				move.setr( 0.0);
			else
				move.setr( 1.0);
		}
		worldAPI.setSpeed(1.0);
	}
	
	
}
