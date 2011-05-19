package grupo14.players;
import EDU.gatech.cc.is.util.Vec2;
import grupo14.aprendizaje.redNeuronal.players.UltraDefenderMLP;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.team.Ordenes;
import grupo14.utils.MatchStateUtils;
import teams.rolebased.WorldAPI;

public class MegaDefender extends X{

	String role="megaDefensor";
	UltraDefenderMLP mlp = null;
	Ordenes ordenesDeEquipo;
	
	Goalkeeper goalkeeper;
	boolean soyPortero;
	
	@Override
	public int configure() {
		//Para cuando se quiera ver el nombre de los jugadores en el simulador
		//worldAPI.setDisplayString(role);
		this.matchStateUtils = new MatchStateUtils();
		matchStateUtils.setMatchState(new PosesionContrarioEnSuCampo());
		ordenesDeEquipo = Ordenes.getInstance();
		soyPortero = false;
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public int takeStep() {
		if (soyPortero) {
			if (worldAPI.blocked()) {
				Acciones.desbloquearse(worldAPI);
				goalkeeper.setSoyPortero(true);
			}
			else
				accionPortero();
		}
		else {
			if (mlp != null)
				mlp.getNextMove(worldAPI);
			
			//Ver si hay nueva orden de la red neuronal
			if(this.ordenesDeEquipo.usarRN()) 
				//Codigo Para Sergio
				Acciones.realizaAccion(mlp.getLastMove().getAction(), worldAPI);
			//Ver si el entrenador ha ordenado un nuevo estado
			else if(this.ordenesDeEquipo.hayNuevoEstado())
				//Pasar al estado ordenado
				matchStateUtils.matchState = ordenesDeEquipo.pasarAEstado();
			else
			{
				matchStateUtils.getMatchState(this.worldAPI);
				matchStateUtils.matchState.accionARealizar(worldAPI, role);
			}
		}
		//worldAPI.setDisplayString(mlp.getLastMove().toString());
		
		return WorldAPI.ROBOT_OK;
	}
	
	public void setMLP(UltraDefenderMLP mlp) {
		this.mlp = mlp;
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

	public void setGoalkeeper(Goalkeeper goalkeeper) {
		this.goalkeeper = goalkeeper;
	}

	public void setSoyPortero(boolean soyPortero) {
		this.soyPortero = soyPortero;
	}

}
