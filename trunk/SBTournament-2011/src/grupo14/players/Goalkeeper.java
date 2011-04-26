package grupo14.players;

import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.ExecutionException;

import com.hp.hpl.jena.query.function.library.abs;

import grupo14.aprendizaje.CBR.AprendizajeCBR;
import grupo14.aprendizaje.CBR.OctantsState;
import grupo14.aprendizaje.CBR.caseComponents.DescripcionCaso;
import EDU.gatech.cc.is.util.Vec2;
import states.PosesionContrarioConPeligro;
import states.PosesionContrarioSinPeligro;
import states.UltimoHombreContrario;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Goalkeeper extends Role{
	private final double KEEPER_DEFENSE_LINE = 0;
	
	public MatchState matchState;
	
	AprendizajeCBR cbr;
	long lastCase;
	String role="portero";

	@Override
	public int configure() {
		//Para cuando se quiera ver el nombre de los jugadores en el simulador
		worldAPI.setDisplayString("Portero");
		//Para poder manipular el CBR
		this.cbr = new AprendizajeCBR();
		try {
			this.cbr.configure();
			this.cbr.preCycle();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.lastCase = 0;
		
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
		
		//Si el balon pasa por el centro y han pasado mas de 15 segundos desde la ultima lectura
		if(ballIsInTheMiddle() && (worldAPI.getMatchTotalTime() - this.lastCase > 15 ) )
		{
			//Hay que crear un nuevo caso (en el futuro en vez de crear un caso,
			//también se consultara la base de casos para saber que hacer)
			crearCaso();
			//Se guarda el momento en que se ha leido el caso, para asegurar que no 
			//se cogen dos casos muy juntos
			this.lastCase = worldAPI.getMatchTotalTime();
		}
		return WorldAPI.ROBOT_OK;
	}
	
	/**
	 * Devuelve si el balon esta en el centro del campo o no
	 * @return true si el balon esta en el centro del campo, false en caso contrario
	 */
	private boolean ballIsInTheMiddle(){
		double ball = worldAPI.getBall().x;
		double pos = worldAPI.getPosition().x;
		if( Math.abs(ball) - Math.abs(pos) == 0)
			return true;
		else
			return false;
	}

	private void crearCaso() {
		//Get the position of all the players
		Vec2[] opponents = worldAPI.getOpponents();
		Vec2[] teammates = worldAPI.getTeammates();
		//Return the number of players in each octant
		OctantsState state = setOctantsState(teammates, opponents);
		int ballPos = getPlayersOctant(worldAPI.getBall());
		int ourGoals = worldAPI.getMyScore();
		int theirGoals = worldAPI.getOpponentScore();
		long time = worldAPI.getMatchTotalTime();
		DescripcionCaso descripcion = new DescripcionCaso();
		descripcion.setBallPosition(ballPos);
		descripcion.setNumPlayersEachOctant(state);
		descripcion.setOurGoals(ourGoals);
		descripcion.setTheirGoals(theirGoals);
		descripcion.setTime(time);
		CBRCase caso = new CBRCase();
		caso.setDescription(descripcion);
		this.cbr.guardarCaso(caso);
	}

	/**
	 * Fills the octants with the number of players located in each one.
	 * @param teammates: Array with the position of each team mate (egocentric respect goal keeper)
	 * @param opponents: Array with the position of each opponent (egocentric respect goal keeper)
	 * @return OctantsState object with the filled octants
	 */
	private OctantsState setOctantsState(Vec2[] teammates, Vec2[] opponents) {
		OctantsState state = new OctantsState();
		for(int i = 0; i < 8; i++)
			state.octants.add(new Integer(0));
		for(int i = 0; i < teammates.length; i++)
		{
			//Adds 1 to the octant where the player is located
			int octant = getPlayersOctant(teammates[i])-1;
			int playersInOctant = state.octants.get(octant).intValue();
			state.octants.set(octant, playersInOctant+1);
		}
		for(int i = 0; i < opponents.length; i++)
		{
			//Adds 1 to the octant where the player is located
			int octant = getPlayersOctant(opponents[i])-1;
			int playersInOctant = state.octants.get(octant).intValue()+1;
			state.octants.set(octant, playersInOctant);
		}
		return state;
	}

	/**
	 * Returns the octant number of the player
	 * @param vec2
	 * @return Integer value representing the octant number of the player or -1 if an error occurs
	 */
	private int getPlayersOctant(Vec2 playersPosition) {
		
		Vec2 globalPos = toGlobalCoordinates(playersPosition);
		if(globalPos.y >= 0)
		{
			if(globalPos.x < -0.685)
				return 1;
			else if(globalPos.x >= -0.685 && globalPos.x < 0)
				return 2;
			else if(globalPos.x >= 0 && globalPos.x < 0.685)
				return 3;
			else if(globalPos.x >= 0.685)
				return 4;
		}
		else
		{
			if(globalPos.x < -0.685)
				return 5;
			else if(globalPos.x >= -0.685 && globalPos.x < 0)
				return 6;
			else if(globalPos.x >= 0 && globalPos.x < 0.685)
				return 7;
			else if(globalPos.x >= 0.685)
				return 8;
		}
		return -1;
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
	private Vec2 toGlobalCoordinates(Vec2 sourceCoord)
	{
		Vec2 coordinates = (Vec2)sourceCoord.clone(); 
		coordinates.add(worldAPI.getPosition());
		return coordinates;
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
				else{
					System.out.println("Hay peligro, adelantarse un poquitin");
					MatchState state = new PosesionContrarioConPeligro();
					matchState = state;
				}
			}
		}
		else
		{
			System.out.println("El balon esta en el campo contrario");
			MatchState state = new PosesionContrarioSinPeligro();
			matchState = state;
		}
	}
	//devuelve la posicion de un item desde el centro del campo
	public Vec2 getRelativePosition(Vec2 position)
	{
		return new Vec2(worldAPI.getPosition().x+position.x,worldAPI.getPosition().y+position.y);
	}
	
}
