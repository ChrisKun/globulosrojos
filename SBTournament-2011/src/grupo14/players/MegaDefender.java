package grupo14.players;
import EDU.gatech.cc.is.util.Vec2;
import jcolibri.exception.ExecutionException;
import grupo14.aprendizaje.CBR.AprendizajeCBR;
import grupo14.manager.*;

import states.PosesionContrarioConPeligro;
import states.PosesionContrarioSinPeligro;
import states.UltimoHombreContrario;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class MegaDefender  extends Role{

	public MatchState matchState;
	String role="Mega Defensor";
	
	@Override
	public int configure() {
		//Para cuando se quiera ver el nombre de los jugadores en el simulador
		worldAPI.setDisplayString(role);		
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public int takeStep() {
		MatchState state = new PosesionContrarioSinPeligro();
		matchState = state;
		getMatchState();
		matchState.accionARealizar(worldAPI,role);
		
		
		return WorldAPI.ROBOT_OK;
	}

	private void getMatchState() {
		int lado = worldAPI.getFieldSide(); //-1 en la derecha, 1 en la izquierda
		//si el balon lo tiene el contrario, estado=2, estado=3... 
		if(getRelativePosition(worldAPI.getBall()).x*lado<0)
		{
			//el balon esta en tu campo
			if(getRelativePosition(worldAPI.getClosestMate()).x > getRelativePosition(worldAPI.getClosestOpponent()).x)
			{
				//fuera de juego
				if(Math.abs((getRelativePosition(worldAPI.getBall()).x)-(getRelativePosition(worldAPI.getClosestMate()).x))>Math.abs((getRelativePosition(worldAPI.getBall()).x)-(getRelativePosition(worldAPI.getClosestOpponent()).x)))
				{
					//el balon esta mas cerca del oponente ke de ti (en termino de X, no de distancia
					MatchState state = new UltimoHombreContrario();
					matchState = state;
				}
				else{
					MatchState state = new PosesionContrarioConPeligro();
					matchState = state;
				}
			}
		}
		else
		{
			MatchState state = new PosesionContrarioConPeligro();
			matchState = state;
		}
	}
	//devuelve la posicion de un item desde el centro del campo
	public Vec2 getRelativePosition(Vec2 position)
	{
		return new Vec2(worldAPI.getPosition().x+position.x,worldAPI.getPosition().y+position.y);
	}
}
