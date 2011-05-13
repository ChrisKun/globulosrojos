package grupo14.utils;

import grupo14.aprendizaje.CBR.AprendizajeCBR;
import grupo14.aprendizaje.CBR.OctantsState;
import grupo14.aprendizaje.CBR.caseComponents.DescripcionCaso;
import grupo14.aprendizaje.CBR.caseComponents.ResultadoCaso;
import grupo14.aprendizaje.CBR.caseComponents.SolucionCaso;
import grupo14.players.MatchState;
import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.ExecutionException;
import EDU.gatech.cc.is.util.Vec2;
import teams.rolebased.WorldAPI;

/**
 * Class with methods to manage the CBR system
 * @author markel
 *
 */
public class CBRUtils {

	public AprendizajeCBR cbr;
	private CBRCase casoAnterior;
	public long lastCase;
	private int ourGoals;
	private int theirGoals;
	
	/**
	 * Initiates the CBR tools
	 */
	public CBRUtils()
	{
		//Para poder manipular el CBR
		this.cbr = new AprendizajeCBR();
		try {
			this.cbr.configure();
			this.cbr.preCycle();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		this.lastCase = 0;
		this.ourGoals = 0;
		this.theirGoals = 0;
	}
	
	/**
	 * Devuelve si se cumplen los requisitos para la lectura de un nuevo caso
	 * Las condiciones son:
	 * - Que hayan transcurrido 25 segundo sin crear un caso
	 * - Que el balon este en el centro del campo y que llevemos más de 15 segundos sin crear un caso
	 * - Que se haya marcado un gol en cualquiera de las porterías
	 * @return true si las condiciones para leer un caso se cumplen, false an caso contrario
	 */
	public boolean needToCreateCase(WorldAPI worldAPI, long lastCase){
		double ball = worldAPI.getBall().x;
		double pos = worldAPI.getPosition().x;
		long tiempoTranscurrido = worldAPI.getMatchTotalTime() - worldAPI.getMatchRemainingTime();
		//Si han pasado 25 segundos desde el anterior caso leido
		if(tiempoTranscurrido - lastCase > 25000 )
			return true;
		//Si el balon esta en medio y han pasado 15 segundos desde el anterior caso leido
		else if( Math.abs(ball) - Math.abs(pos) == 0 && (tiempoTranscurrido - lastCase > 15000 ))
			return true;
		//Si ha habido algun gol
		else if(worldAPI.getMyScore() != this.ourGoals || worldAPI.getOpponentScore() != this.theirGoals)
		{
			this.ourGoals = worldAPI.getMyScore();
			this.theirGoals = worldAPI.getOpponentScore();
			return true;		
		}else
			return false;
	}
	
	/**
	 * Creates a case with the current match state
	 */
	public CBRCase crearCaso(WorldAPI worldAPI, MatchState matchState) {
		//Get the position of all the players
		Vec2[] opponents = worldAPI.getOpponents();
		Vec2[] teammates = worldAPI.getTeammates();
		//Return the number of players in each octant
		OctantsState state = fieldUtils.setOctantsState(teammates, opponents, worldAPI);
		//Get the position of the ball
		int ballPos = fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI);
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
			ResultadoCaso resultado = new ResultadoCaso(evaluarCaso(worldAPI, casoAnterior));
			casoAnterior.setResult(resultado);
			//Save the case with its result (case was already saved, so just update it in the case base)
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
	private float evaluarCaso(WorldAPI worldAPI, CBRCase casoAnterior) {
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
			switch (fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI)) {
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
			switch (fieldUtils.getLocationsOctant(worldAPI.getBall(), worldAPI)) {
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
}
