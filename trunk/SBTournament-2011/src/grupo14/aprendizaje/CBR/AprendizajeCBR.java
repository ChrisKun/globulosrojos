package grupo14.aprendizaje.CBR;

import grupo14.aprendizaje.CBR.caseComponents.Caso;
import grupo14.aprendizaje.CBR.caseComponents.DescripcionCaso;
import grupo14.aprendizaje.CBR.caseComponents.ResultadoCaso;
import grupo14.aprendizaje.CBR.caseComponents.SolucionCaso;

import java.util.ArrayList;
import java.util.Collection;

import grupo14.aprendizaje.CBR.voting.Prediction;
import grupo14.aprendizaje.CBR.voting.SimilarityWeightedVotingMethod;
import grupo14.players.PlayerActions;

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

	private Connector connector;
	private CBRCaseBase caseBase;

	@Override
	public void configure() throws ExecutionException {
		try {
			this.connector = new PlainTextConnector();
			this.connector.initFromXMLfile(jcolibri.util.FileIO
					.findFile("grupo14/aprendizaje/CBR/connector/plainTextConfig.xml"));
			this.caseBase = new LinealCaseBase();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		this.caseBase.init(this.connector);
		Collection<CBRCase> cases = this.caseBase.getCases();
		for(CBRCase c: cases)
			System.out.println(c);
		return this.caseBase;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Método equivalente al cycle de jColibri, con la diferencia de que devuelve un caso en vez de void
	 * @param query: Caso que se utilizará como consulta
	 * @return Objeto Caso que contiene la solución a aplicar
	 * @throws ExecutionException
	 */
	public Caso recuperarCaso(CBRQuery query) throws ExecutionException {
		NNConfig simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
		
		Attribute numPlayersEachOctant = new Attribute("numPlayersEachOctant",DescripcionCaso.class);
		Attribute ballPosition = new Attribute("ballPosition",DescripcionCaso.class);
		Attribute time = new Attribute("time",DescripcionCaso.class);
		Attribute ourGoals = new Attribute("ourGoals",DescripcionCaso.class);
		Attribute theirGoals = new Attribute("theirGoals",DescripcionCaso.class);
		
		//Fijamos las funciones de similitud locales
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
		
		//Se recogen los N casos mÃ¡s similares
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), query, simConfig);
		eval = SelectCases.selectTopKRR(eval, 5);

		//Votacion ponderada
		SimilarityWeightedVotingMethod votacion = new SimilarityWeightedVotingMethod();
		Prediction prediccion = votacion.getPredictedClass(eval);
		
		//Se supone que aqui tenemos las acciones a realizar
		return (Caso)prediccion.getClassification();
	}

	@Override
	public void postCycle() throws ExecutionException {
		this.connector.close();
	}
	
	/**
	 * Añade un caso nuevo a la base de casos
	 * @param caso: El caso que se quiere guardar
	 */
	public void guardarCaso(Caso caso)
	{
		Collection<CBRCase> aprendido = new ArrayList<CBRCase>();
		aprendido.add(caso);
		this.caseBase.learnCases(aprendido);
	}
	
	public static void main(String[] args)
	{
		Caso caso = new Caso();
		DescripcionCaso desc = new DescripcionCaso();
		desc.setBallPosition(2);
		ArrayList<Integer> octants = new ArrayList<Integer>();
		octants.add(new Integer(2));
		octants.add(new Integer(5));
		octants.add(new Integer(4));
		octants.add(new Integer(1));
		desc.setNumPlayersEachOctant(octants);
		desc.setOurGoals(2);
		desc.setTheirGoals(1);
		desc.setTime(36);
		caso.setDescripcion(desc);
		ResultadoCaso resultado = new ResultadoCaso();
		resultado.setValoracionCaso(0.7f);
		caso.setResultado(resultado);
		SolucionCaso solucion = new SolucionCaso();
		ArrayList<PlayerActions> playerActions = new ArrayList<PlayerActions>();
		playerActions.add(PlayerActions.BloquearContrario);
		playerActions.add(PlayerActions.CorrerHaciaBalon);
		solucion.setPlayerActions(playerActions);
		caso.setSolution(solucion);
		
		AprendizajeCBR cbr = new AprendizajeCBR();
		try {
			cbr.configure();
			cbr.preCycle();
			cbr.guardarCaso(caso);
			cbr.postCycle();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
