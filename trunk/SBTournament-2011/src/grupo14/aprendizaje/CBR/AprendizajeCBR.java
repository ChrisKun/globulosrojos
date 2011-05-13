package grupo14.aprendizaje.CBR;

import grupo14.aprendizaje.CBR.caseComponents.DescripcionCaso;
import grupo14.aprendizaje.CBR.caseComponents.ResultadoCaso;
import grupo14.aprendizaje.CBR.voting.Prediction;
import grupo14.aprendizaje.CBR.voting.SimilarityWeightedVotingMethod;

import java.util.ArrayList;
import java.util.Collection;

import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbraplications.StandardCBRApplication;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.connector.PlainTextConnector;
import jcolibri.exception.ExecutionException;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import jcolibri.method.retrieve.selection.SelectCases;

public class AprendizajeCBR implements StandardCBRApplication {

	private Connector connectorEste;
	private Connector connectorOeste;
	// Base de casos donde se guardaran los casos que tengan mas jugadores en el
	// lado este
	private CBRCaseBase caseBaseEast;
	// Base de casos donde se guardaran los casos que tengan mas jugadores en el
	// lado oeste
	private CBRCaseBase caseBaseWest;

	@Override
	public void configure() throws ExecutionException {
		try {
			this.connectorEste = new PlainTextConnector();
			this.connectorOeste = new PlainTextConnector();
			this.connectorEste
					.initFromXMLfile(jcolibri.util.FileIO
							.findFile("grupo14/aprendizaje/CBR/connector/plainTextConfig.xml"));
			this.connectorOeste
					.initFromXMLfile(jcolibri.util.FileIO
							.findFile("grupo14/aprendizaje/CBR/connector/plainTextConfigWest.xml"));
			this.caseBaseEast = new LinealCaseBase();
			this.caseBaseWest = new LinealCaseBase();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		this.caseBaseEast.init(this.connectorEste);
		this.caseBaseWest.init(this.connectorOeste);

		return this.caseBaseEast;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		// TODO Auto-generated method stub
	}

	/**
	 * Metodo equivalente al cycle de jColibri, con la diferencia de que
	 * devuelve una prediccion en vez de void
	 * 
	 * @param query: Caso que se utilizara como consulta
	 * @return Objeto Prediction que contiene la solucion a aplicar
	 * @throws ExecutionException
	 */
	public Prediction recuperarCaso(CBRQuery query) throws ExecutionException {
		NNConfig simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());

		Attribute numPlayersEachOctant = new Attribute("numPlayersEachOctant",
				DescripcionCaso.class);
		Attribute ballPosition = new Attribute("ballPosition",
				DescripcionCaso.class);
		Attribute time = new Attribute("time", DescripcionCaso.class);
		Attribute ourGoals = new Attribute("ourGoals", DescripcionCaso.class);
		Attribute theirGoals = new Attribute("theirGoals",
				DescripcionCaso.class);

		// Fijamos las funciones de similitud locales
		simConfig.addMapping(numPlayersEachOctant, new Equal());
		simConfig.setWeight(numPlayersEachOctant, 1.0);
		simConfig.addMapping(ballPosition, new Equal());
		simConfig.setWeight(ballPosition, 1.0);
		simConfig.addMapping(time, new Equal());
		simConfig.setWeight(time, 0.5);
		simConfig.addMapping(ourGoals, new Equal());
		simConfig.setWeight(ourGoals, 0.7);
		simConfig.addMapping(theirGoals, new Equal());
		simConfig.setWeight(theirGoals, 0.7);

		Collection<RetrievalResult> eval;
		if (((DescripcionCaso) query.getDescription()).masJugadoresEnElEste()) {
			// Se recogen los N casos más similares
			eval = NNScoringMethod.evaluateSimilarity(caseBaseEast.getCases(),
					query, simConfig);
		} else {
			// Se recogen los N casos más similares
			eval = NNScoringMethod.evaluateSimilarity(caseBaseWest.getCases(),
					query, simConfig);
		}

		eval = SelectCases.selectTopKRR(eval, 5);

		// Votacion ponderada
		SimilarityWeightedVotingMethod votacion = new SimilarityWeightedVotingMethod();
		Prediction prediccion = votacion.getPredictedClass(eval);

		// Se supone que aqui tenemos las acciones a realizar
		return prediccion;
	}

	@Override
	public void postCycle() throws ExecutionException {
		this.connectorEste.close();
		this.connectorOeste.close();
	}

	/**
	 * A�ade un caso nuevo a la base de casos
	 * 
	 * @param caso: El caso que se quiere guardar
	 */
	public void guardarCaso(CBRCase caso) {
		CBRCaseBase selectedCaseBase;
		if (((DescripcionCaso) caso.getDescription()).masJugadoresEnElEste())
			selectedCaseBase = this.caseBaseEast;
		else
			selectedCaseBase = this.caseBaseWest;
		int caseId = selectedCaseBase.getCases().size();
		((DescripcionCaso) caso.getDescription()).setCaseId("Case " + caseId);
		// Se guarda el nuevo caso en la base de casos
		Collection<CBRCase> aprendido = new ArrayList<CBRCase>();
		aprendido.add(caso);
		selectedCaseBase.learnCases(aprendido);
	}

	/**
	 * A�ade un caso nuevo a la base de casos
	 * 
	 * @param caso
	 *            : El caso que se quiere guardar
	 */
	public void guardarCaso(ArrayList<Integer> situation) {
		// Se crea la descripcion del caso en la que se guardara la situacion
		// del juego
		DescripcionCaso descripcion = new DescripcionCaso();
		OctantsState state = new OctantsState();
		state.octants = situation;
		descripcion.setNumPlayersEachOctant(state);
		int caseId = this.caseBaseEast.getCases().size();
		descripcion.setCaseId("Case " + caseId);
		// Se crea un caso nuevo y se le asigna la descripcion recien creada
		CBRCase caso = new CBRCase();
		caso.setDescription(descripcion);
		// Se guarda el nuevo caso en la base de casos
		Collection<CBRCase> aprendido = new ArrayList<CBRCase>();
		aprendido.add(caso);
		this.caseBaseEast.learnCases(aprendido);
	}

	/**
	 * Busca un caso en la base de casos utilizando el caseId y lo evalua
	 * 
	 * @param caseId
	 *            : identificador del caso que se quiere evaluar
	 * @param evaluacion
	 *            : valor entre 0 y 1 para evaluar el caso (1=mejor evaluacion,
	 *            0=peor evaluacion)
	 */
	public void evaluarCaso(String caseId, float evaluacion) {
		// Se recogen todos los casos de la base
		Collection<CBRCase> casos = caseBaseEast.getCases();
		// Iteramos para encontrar el caso con el id que hemos recibido
		for (CBRCase caso : casos) {
			// Si se encuentra el caso con el id que buscamos...
			if (caso.getID().equals(caseId))
				// Aplicarle como resultado la evaluacion recibida
				caso.setResult(new ResultadoCaso(evaluacion));
		}
	}

	// public static void main(String[] args)
	// {
	// CBRCase caso = new CBRCase();
	// DescripcionCaso desc = new DescripcionCaso();
	// desc.setCaseId("Case 2");
	// ArrayList<Integer> octants = new ArrayList<Integer>();
	// octants.add(new Integer(2));
	// octants.add(new Integer(5));
	// octants.add(new Integer(4));
	// octants.add(new Integer(1));
	// octants.add(new Integer(2));
	// octants.add(new Integer(5));
	// octants.add(new Integer(4));
	// octants.add(new Integer(1));
	// OctantsState state = new OctantsState();
	// state.octants = octants;
	// desc.setNumPlayersEachOctant(state);
	// desc.setBallPosition(2);
	// desc.setTime(36);
	// desc.setOurGoals(2);
	// desc.setTheirGoals(1);
	// caso.setDescription(desc);
	// ResultadoCaso resultado = new ResultadoCaso();
	// resultado.setValoracionCaso(0.7f);
	// caso.setResult(resultado);
	// SolucionCaso solucion = new SolucionCaso();
	// ArrayList<PlayerActions> playerActions = new ArrayList<PlayerActions>();
	// playerActions.add(PlayerActions.BloquearContrario);
	// playerActions.add(PlayerActions.CorrerHaciaBalon);
	// playerActions.add(PlayerActions.BloquearContrario);
	// playerActions.add(PlayerActions.CorrerHaciaBalon);
	// playerActions.add(PlayerActions.BloquearContrario);
	// TeamActions teamActions = new TeamActions();
	// teamActions.setPlayerActions(playerActions);
	// solucion.setPlayerActions(teamActions);
	// caso.setSolution(solucion);
	//
	// AprendizajeCBR cbr = new AprendizajeCBR();
	// try {
	// cbr.configure();
	// cbr.preCycle();
	// cbr.guardarCaso(caso);
	// cbr.postCycle();
	// } catch (ExecutionException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

}
