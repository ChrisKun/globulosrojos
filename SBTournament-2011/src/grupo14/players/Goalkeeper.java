package grupo14.players;

import java.util.ArrayList;

import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.ExecutionException;

import com.hp.hpl.jena.query.core.RecursiveVisitor;
import com.hp.hpl.jena.query.function.library.abs;

import grupo14.aprendizaje.CBR.AprendizajeCBR;
import grupo14.aprendizaje.CBR.OctantsState;
import grupo14.aprendizaje.CBR.caseComponents.DescripcionCaso;
import grupo14.aprendizaje.CBR.caseComponents.ResultadoCaso;
import grupo14.aprendizaje.CBR.caseComponents.SolucionCaso;
import EDU.gatech.cc.is.util.Vec2;
import states.Catenaccio;
import states.Heroica;
import states.IrAlAtaque;
import states.JuegoBrusco;
import states.PosesionContrarioConPeligro;
import states.PosesionContrarioEnSuCampo;
import states.PosesionContrarioNuestroCampo;
import states.PosesionContrarioSinPeligro;
import states.PosesionNuestraEnNuestroCampo;
import states.PosesionNuestraEnSuCampo;
import states.UltimoHombreContrario;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;

public class Goalkeeper extends Role{
	private final double KEEPER_DEFENSE_LINE = 0;
	
	public MatchState matchState;
	
	AprendizajeCBR cbr;
	CBRCase casoAnterior;
	long lastCase;
	String role="portero";

	@Override
	public int configure() {
		//Para cuando se quiera ver el nombre de los jugadores en el simulador
		worldAPI.setDisplayString(role);
		//Para poder manipular el CBR
		this.cbr = new AprendizajeCBR();
		try {
			this.cbr.configure();
			this.cbr.preCycle();
		} catch (ExecutionException e) {
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
		
		//Si el sistema considera que debe leerse un nuevo caso
		if(needToCreateCase())
		{
			//Hay que crear un nuevo caso (en el futuro en vez de crear un caso,
			//también se consultara la base de casos para saber que hacer)
			CBRCase caso = crearCaso();
			//Se guarda el momento en que se ha leido el caso, para asegurar que no 
			//se cogen dos casos muy juntos
			this.lastCase = worldAPI.getMatchTotalTime() - worldAPI.getMatchRemainingTime();
			//Se hace la consulta a la base de casos para que esta nos devuelva el caso mejor
			//y tomar una decision
			
			/**************************************************************/
//			try {
//				CBRCase recuperado = cbr.recuperarCaso(caso);
//				SolucionCaso solucion = (SolucionCaso)recuperado.getSolution();
//				String recoveredState = solucion.getNewState();
//				this.matchState = setMatchStateUsingName(recoveredState);
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
			/**************************************************************/
		}
		return WorldAPI.ROBOT_OK;
	}
	
	private MatchState setMatchStateUsingName(String state) {
		if(state.equals("Catenaccio"))
			return new Catenaccio();
		if(state.equals("Heroica"))
			return new Heroica();
		if(state.equals("IrAlAtaque"))
			return new IrAlAtaque();
		if(state.equals("IrAlAtaque"))
			return new JuegoBrusco();
		if(state.equals("PosesionContrarioEnSuCampo"))
			return new PosesionContrarioEnSuCampo();
		if(state.equals("PosesionContrarioNuestroCampo"))
			return new PosesionContrarioNuestroCampo();
		if(state.equals("PosesionNuestraEnNuestroCampo"))
			return new PosesionNuestraEnNuestroCampo();
		if(state.equals("PosesionNuestraEnSuCampo"))
			return new PosesionNuestraEnSuCampo();
		if(state.equals("UltimoHombreContrario"))
			return new UltimoHombreContrario();
		return null;
	}

	/**
	 * Devuelve si se cumplen los requisitos para la lectura de un nuevo caso
	 * @return true si las condiciones para leer un caso se cumplen, false an caso contrario
	 */
	private boolean needToCreateCase(){
		double ball = worldAPI.getBall().x;
		double pos = worldAPI.getPosition().x;
		long tiempoTranscurrido = worldAPI.getMatchTotalTime() - worldAPI.getMatchRemainingTime();
		//Si han pasado 25 segundos desde el anterior caso leido
		if(tiempoTranscurrido - this.lastCase > 25000 )
			return true;
		//Si el balon esta en medio y an pasado 15 segundos desde el anterior caso leido
		if( Math.abs(ball) - Math.abs(pos) == 0 && (tiempoTranscurrido - this.lastCase > 15000 ))
			return true;
		else
			return false;
	}

	/**
	 * Creates a case with the current match state
	 */
	private CBRCase crearCaso() {
		//Get the position of all the players
		Vec2[] opponents = worldAPI.getOpponents();
		Vec2[] teammates = worldAPI.getTeammates();
		//Return the number of players in each octant
		OctantsState state = setOctantsState(teammates, opponents);
		//Get the position of the ball
		int ballPos = getLocationsOctant(worldAPI.getBall());
		//Get our sore
		int ourGoals = worldAPI.getMyScore();
		//Get their score
		int theirGoals = worldAPI.getOpponentScore();
		//Get elapsed time
		long time = worldAPI.getMatchTotalTime() - worldAPI.getMatchRemainingTime();
		//Create the description object for the case
		DescripcionCaso descripcion = new DescripcionCaso();
		descripcion.setBallPosition(ballPos);
		descripcion.setNumPlayersEachOctant(state);
		descripcion.setOurGoals(ourGoals);
		descripcion.setTheirGoals(theirGoals);
		descripcion.setTime(time);
		//Create the case and set the description just created
		CBRCase caso = new CBRCase();
		caso.setDescription(descripcion);
		//Set the solution of the case, i.e. the decision taken in this situation
		//Later the evaluation of the case will say if decission was correct or not
		SolucionCaso solucion = new SolucionCaso();
		solucion.setNewState(matchState.getStateName());
		caso.setSolution(solucion);
		//If a previous case exists, evaluate it
		if(casoAnterior != null){
			ResultadoCaso resultado = new ResultadoCaso(evaluarCaso(casoAnterior));
			casoAnterior.setResult(resultado);
			//Save the case with its rasult (case was already saved, so just update it in the case base)
			this.cbr.guardarCaso(casoAnterior);
		}
		//The new case is saved in order to evaluate it later
		this.casoAnterior = caso;
		return caso;
	}

	/**
	 * Da un resultado al caso dependiendo si sus acciones llevaron a un buen resultado o no
	 * El resultado es un valor numerico entre 0 y 1
	 * @param caso
	 * @return Un valor numerico entre 0 y uno donde 1 es el mejor valor posible y 0 el peor
	 */
	private float evaluarCaso(CBRCase casoAnterior) {
		DescripcionCaso desc = (DescripcionCaso)casoAnterior.getDescription();
		float resultado = (float)0.5;
		if(desc.getOurGoals() < worldAPI.getMyScore())
		{//Hemos metido gol
			resultado += 0.5;
		}
		if(desc.getTheirGoals() < worldAPI.getOpponentScore())
		{//Nos han metido gol
			resultado -= 0.5;
		}
		switch (desc.getBallPosition()) {
		case 1:
		case 2:
		case 5:
		case 6:{
			switch (getLocationsOctant(worldAPI.getBall())) {
			case 3:
			case 4:
			case 7:
			case 8:
				//Hemos llevado el balon de nuestro campo al otro
				resultado += 0.2;
				break;

			default:
				break;
			}
			break;
		}
		case 3:
		case 4:
		case 7:
		case 8:{
			switch (getLocationsOctant(worldAPI.getBall())) {
			case 1:
			case 2:
			case 5:
			case 6:
				//Nos han metido el balon en nuestro campo
				resultado -= 0.2;
				break;

			default:
				break;
			}
			break;
		}

		default:
			break;
		}
		return resultado;
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
			int octant = getLocationsOctant(teammates[i])-1;
			int playersInOctant = state.octants.get(octant).intValue();
			state.octants.set(octant, playersInOctant+1);
		}
		for(int i = 0; i < opponents.length; i++)
		{
			//Adds 1 to the octant where the player is located
			int octant = getLocationsOctant(opponents[i])-1;
			int playersInOctant = state.octants.get(octant).intValue()+1;
			state.octants.set(octant, playersInOctant);
		}
		
		//Falta por meter la posicion del portero, porque en teammates no viene su posicion
		Vec2 pos = this.worldAPI.getPosition();
		int octant = getLocationsOctant(pos)-1;
		int playersInOctant = state.octants.get(octant).intValue();
		state.octants.set(octant, playersInOctant+1);
		
		return state;
	}

	/**
	 * Returns the octant number of the received location
	 * @param vec2
	 * @return Integer value representing the octant number of the player (from 1 to 8) or -1 if an error occurs
	 */
	private int getLocationsOctant(Vec2 playersPosition) {
		
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
