package grupo14.players;

import grupo14.aprendizaje.CBR.AprendizajeCBR;
import grupo14.aprendizaje.CBR.OctantsState;
import grupo14.aprendizaje.CBR.caseComponents.DescripcionCaso;
import grupo14.aprendizaje.CBR.caseComponents.ResultadoCaso;
import grupo14.aprendizaje.CBR.caseComponents.SolucionCaso;
import grupo14.states.Catenaccio;
import grupo14.states.Heroica;
import grupo14.states.IrAlAtaque;
import grupo14.states.JuegoBrusco;
import grupo14.states.PosesionContrarioEnSuCampo;
import grupo14.states.PosesionContrarioNuestroCampo;
import grupo14.states.PosesionNuestraEnNuestroCampo;
import grupo14.states.PosesionNuestraEnSuCampo;
import grupo14.states.UltimoHombreContrario;
import grupo14.utils.fieldUtils;
import jcolibri.cbrcore.CBRCase;
import jcolibri.exception.ExecutionException;
import teams.rolebased.Role;
import teams.rolebased.WorldAPI;
import EDU.gatech.cc.is.util.Vec2;

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
		MatchState state = new Heroica();
		matchState = state;
		//getMatchState();
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
		if(state.equals("JuegoBrusco"))
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
	
	public MatchState getState()
	{
		return this.matchState;
	}
	
	public void getMatchState()
	{
		int lado = worldAPI.getFieldSide(); //-1 en la derecha, 1 en la izquierda
		//si el balon lo tiene el contrario, estado=2, estado=3... 
		if(worldAPI.getMyScore()>worldAPI.getOpponentScore())
			matchState = new Catenaccio();
		else if (worldAPI.getMyScore()<worldAPI.getOpponentScore())
			matchState = new Heroica();
		else 
		{
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

				}
			}
		}
		else
		{
			System.out.println("El balon esta en el campo contrario");

		}
		}
	}
	//devuelve la posicion de un item desde el centro del campo
	public Vec2 getRelativePosition(Vec2 position)
	{
		return new Vec2(worldAPI.getPosition().x+position.x,worldAPI.getPosition().y+position.y);
	}
	public boolean whoHasTheBall()
	{
		// si el balon esta en tierra de nadie, tambien lo toma como si fuera posesion contraria
		//0.06 = cankick
		double unidadDeCercania = 0.1;
		boolean nosotros = true;
		Vec2[] oponentes = worldAPI.getOpponents();
		Vec2[] companeros = worldAPI.getTeammates();
		Vec2 oponenteMasCercano = oponentes[0];
		Vec2 companeroMasCercano = companeros[0];
		Vec2 balon = getRelativePosition(worldAPI.getBall());
		int index = oponentes.length;
		for(int i=1; i<index;i++)
		{
			if((Math.abs(getRelativePosition(oponentes[i]).x - balon.x) <= unidadDeCercania) && (Math.abs(getRelativePosition(oponentes[i]).y - balon.y) <= unidadDeCercania))
			{
				oponenteMasCercano = oponentes[i];
			}
			if((Math.abs(getRelativePosition(companeros[i]).x - balon.x) <= unidadDeCercania) && (Math.abs(getRelativePosition(companeros[i]).y - balon.y) <= unidadDeCercania))
			{
				companeroMasCercano = companeros[i];
			}
		}
		if(oponenteMasCercano.x > companeroMasCercano.x && oponenteMasCercano.y > companeroMasCercano.y  )
			nosotros = true;
		else
			nosotros = false;
		return nosotros;
	}
	
}
