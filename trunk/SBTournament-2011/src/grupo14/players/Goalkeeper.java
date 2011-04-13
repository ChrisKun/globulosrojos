package grupo14.players;

import com.hp.hpl.jena.query.function.library.abs;

import grupo14.aprendizaje.CBR.AprendizajeCBR;
import grupo14.aprendizaje.CBR.OctantsState;
import EDU.gatech.cc.is.util.Vec2;
import states.PosesionContrario;
import states.UltimoHombreContrario;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Goalkeeper extends Role{
	private final double KEEPER_DEFENSE_LINE = 0;
	
	public MatchState matchState;
	
	AprendizajeCBR cbr;

	@Override
	public int configure() {
		//Para cuando se quiera ver el nombre de los jugadores en el simulador
		worldAPI.setDisplayString("Portero");
		//Para poder manipular el CBR
		AprendizajeCBR cbr = new AprendizajeCBR();
		return WorldAPI.ROBOT_OK;
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
	}

	@Override
	public int takeStep() {
		MatchState state = new PosesionContrario();
		matchState = state;
		getMatchState();
		matchState.accionARealizar(worldAPI,Goalkeeper.class);
		
		//double keeperPosY = calculateIntersection();
		/*double keeperPosY = calculateIntersection();
		Vec2 destPos = toEgocentricCoordinates(new Vec2(KEEPER_DEFENSE_LINE, keeperPosY));
		worldAPI.setSteerHeading(destPos.t);
		worldAPI.setSpeed(1);
		System.out.println(keeperPosY);*/
		//if(worldAPI.getBall().x - worldAPI.getPosition().x == 0)
			//El balon esta en el centro ---> Hay que crear un nuevo caso (en el futuro consultar para saber que hacer)
		//	crearCaso();
		//Acciones.correrAlAtaque(worldAPI);
		return WorldAPI.ROBOT_OK;
	}

	private void crearCaso() {
		//Get the position of all the players
		Vec2[] opponents = worldAPI.getOpponents();
		Vec2[] teammates = worldAPI.getTeammates();
		//Return the number of players in each octant
		OctantsState state = setOctantsState(teammates, opponents);
	}

	/**
	 * Fills the octants with the number of players located in each one.
	 * @param teammates: Array with the position of each team mate (egocentric respect goal keeper)
	 * @param opponents: Array with the position of each opponent (egocentric respect goal keeper)
	 * @return OctantsState object with the filled octants
	 */
	private OctantsState setOctantsState(Vec2[] teammates, Vec2[] opponents) {
		OctantsState state = new OctantsState();
		for(int i = 0; i < teammates.length; i++)
			//Adds 1 to the octant where the player is located
			state.octants.set(getPlayersOctant(teammates[i]),state.octants.get(getPlayersOctant(teammates[i])+1));
		for(int i = 0; i < opponents.length; i++)
			//Adds 1 to the octant where the player is located
			state.octants.set(getPlayersOctant(opponents[i]),state.octants.get(getPlayersOctant(opponents[i])+1));
		return new OctantsState();
	}

	/**
	 * Returns the octant number of the player
	 * @param vec2
	 * @return Integer value representing the octant number of the player
	 */
	private int getPlayersOctant(Vec2 vec2) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Vec2 calculatePosition(){
		//Recoger posicion del balon
		Vec2 ballPosition = worldAPI.getBall();
		
		return null;
	}
	
	/**
	 * Convierte un vector con coordenadas globales a uno con coordenadas egocentricas
	 * @param sourceCoord: Vector con coordenadas globales que se quiere convertir
	 * @return Objeto Vec2 con las coordenadas egocentricas del vector recibido
	 */
	private Vec2 toEgocentricCoordinates(Vec2 sourceCoord){
		Vec2 coordinates = (Vec2)sourceCoord.clone(); 
		coordinates.sub(worldAPI.getPosition());
		return coordinates;
	}
	
	/**
	 * Convierte un vector con coordenadas egocentricas a uno con coordenadas globales
	 * @param sourceCoord: Vector con coordenadas egocentricas que se quiere convertir
	 * @return Objeto Vec2 con las coordenadas globales del vector recibido
	 */
	private Vec2 toGlobalCoordinates(Vec2 sourceCoord){
		
		return null;
	}
	
	/**
	 * Devuelve la coordenada Y del punto en el que se cruzan las rectas A y B, siendo A
	 * la recta que pasa desde la posicion del balon y el centro de la
	 * porteria y B, la recta x = keeperPosition
	 * @param ballPosition: Posicion del balon para poder calcular la recta que une el balon con la porteria
	 * @return Coordenada Y del punto de interseccion
	 */
	private double calculateIntersection(){
		Vec2 goalPosition = worldAPI.getOurGoal();
		Vec2 ballPosition = worldAPI.getBall();
		double deltaY = (goalPosition.y - ballPosition.y);
		double deltaX = (goalPosition.x - ballPosition.x);
		return deltaY * (ballPosition.y/deltaY - ballPosition.x/deltaX + KEEPER_DEFENSE_LINE / deltaX);
	}
	public MatchState getState()
	{
		return this.matchState;
	}
	
	public void getMatchState()
	{
		int lado = worldAPI.getFieldSide(); //-1 en la derecha, 1 en la izquierda
		//si el balon lo tiene el contrario, estado=2, estado=3... 
		if(getRelativePosition(worldAPI.getBall()).x*lado<0)
		{
			System.out.println("el balon esta en tu campo");
			System.out.println(getRelativePosition(worldAPI.getBall()).x*lado);
			if(getRelativePosition(worldAPI.getClosestMate()).x > getRelativePosition(worldAPI.getClosestOpponent()).x)
			{
				//fuera de juego
				if(Math.abs((getRelativePosition(worldAPI.getBall()).x)-(getRelativePosition(worldAPI.getClosestMate()).x))>Math.abs((getRelativePosition(worldAPI.getBall()).x)-(getRelativePosition(worldAPI.getClosestOpponent()).x)))
				{
					//el balon esta mas cerca del oponente ke de ti (en termino de X, no de distancia
					MatchState state = new UltimoHombreContrario();
					matchState = state;
				}
			}
		}
		else
		{
			System.out.println("El balon esta en el campo contrario");
			MatchState state = new PosesionContrario();
			matchState = state;
		}
	}
	//devuelve la posicion de un item desde el centro del campo
	public Vec2 getRelativePosition(Vec2 position)
	{
		return new Vec2(worldAPI.getPosition().x+position.x,worldAPI.getPosition().y+position.y);
	}
	
}
