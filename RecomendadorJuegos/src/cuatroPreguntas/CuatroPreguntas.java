package cuatroPreguntas;

import java.util.ArrayList;
import java.util.Collection;

import sistema.Game;
import sistema.GameConnector;
import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.exception.ExecutionException;
import jcolibri.extensions.recommendation.casesDisplay.DisplayCasesTableMethod;
import jcolibri.extensions.recommendation.casesDisplay.UserChoice;
import jcolibri.extensions.recommendation.conditionals.BuyOrQuit;
import jcolibri.method.gui.formFilling.ObtainQueryWithFormMethod;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import jcolibri.method.retrieve.selection.SelectCases;

public class CuatroPreguntas {
	
	private Connector _connector;
	private CBRCaseBase _caseBase;
	private NNConfig simConfig;
	Collection<Attribute> hiddenAtts;
	
	public void configure() throws ExecutionException
    {
		// Create a data base connector
		_connector = new GameConnector();
		// Create a Lineal case base for in-memory organization
		_caseBase = new LinealCaseBase();
		
		simConfig = new NNConfig();
		// Set the average() global similarity function for the description of the case
		simConfig.setDescriptionSimFunction(new Average());
		simConfig.addMapping(new Attribute("gameId", Game.class), new Equal());
		simConfig.addMapping(new Attribute("url", Game.class), new Equal());
		simConfig.addMapping(new Attribute("name", Game.class), new Equal());
		simConfig.addMapping(new Attribute("codeName", Game.class), new Equal());
		simConfig.addMapping(new Attribute("image", Game.class), new Equal());
		simConfig.addMapping(new Attribute("artists", Game.class), new Equal());
		simConfig.addMapping(new Attribute("designers", Game.class), new Equal());
		simConfig.addMapping(new Attribute("publishers", Game.class), new Equal());
		simConfig.addMapping(new Attribute("yearPublished", Game.class), new Equal());
		simConfig.addMapping(new Attribute("minNumPlayers", Game.class), new Equal());
		simConfig.addMapping(new Attribute("maxNumPlayers", Game.class), new Equal());
		simConfig.addMapping(new Attribute("minBestNumPlayers", Game.class), new Equal());
		simConfig.addMapping(new Attribute("maxBestNumPlayers", Game.class), new Equal());
		simConfig.addMapping(new Attribute("minRecNumPlayers", Game.class), new Equal());
		simConfig.addMapping(new Attribute("maxRecNumPlayers", Game.class), new Equal());
		simConfig.addMapping(new Attribute("playingTime", Game.class), new Equal());
		simConfig.addMapping(new Attribute("age", Game.class), new Equal());
		simConfig.addMapping(new Attribute("subdomains", Game.class), new Equal());
		simConfig.addMapping(new Attribute("categories", Game.class), new Equal());
		simConfig.addMapping(new Attribute("mechanics", Game.class), new Equal());
		
		hiddenAtts = new ArrayList<Attribute>();
		hiddenAtts.add(new Attribute("gameId", Game.class));
		hiddenAtts.add(new Attribute("url", Game.class));
		hiddenAtts.add(new Attribute("name", Game.class));
		hiddenAtts.add(new Attribute("codeName", Game.class));
		hiddenAtts.add(new Attribute("image", Game.class));
		hiddenAtts.add(new Attribute("artists", Game.class));
		hiddenAtts.add(new Attribute("designers", Game.class));
		hiddenAtts.add(new Attribute("publishers", Game.class));
		//hiddenAtts.add(new Attribute("yearPublished", Game.class));
		hiddenAtts.add(new Attribute("minNumPlayers", Game.class));
		hiddenAtts.add(new Attribute("maxNumPlayers", Game.class));
		hiddenAtts.add(new Attribute("minBestNumPlayers", Game.class));
		hiddenAtts.add(new Attribute("maxBestNumPlayers", Game.class));
		hiddenAtts.add(new Attribute("minRecNumPlayers", Game.class));
		hiddenAtts.add(new Attribute("maxRecNumPlayers", Game.class));
		hiddenAtts.add(new Attribute("playingTime", Game.class));
		//hiddenAtts.add(new Attribute("age", Game.class));
		hiddenAtts.add(new Attribute("subdomains", Game.class));
		hiddenAtts.add(new Attribute("categories", Game.class));
		hiddenAtts.add(new Attribute("mechanics", Game.class));
    }
	
	public CBRCaseBase preCycle() throws ExecutionException
    {
		// Load cases from connector into the case base
		_caseBase.init(_connector);
		// Print the cases
		Collection<CBRCase> cases = _caseBase.getCases();
		for(CBRCase c: cases)
			System.out.println(c);
		return _caseBase;
    }
	
	public Collection<CBRCase> cycle(CBRQuery query) throws ExecutionException
    {
		// Obtain query
		ObtainQueryWithFormMethod.obtainQueryWithInitialValues(query,hiddenAtts,null);
		
		// Execute KNN
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		
		// Select cases
		Collection<CBRCase> retrievedCases = SelectCases.selectTopK(eval, 5);
		
		return retrievedCases;
		
		// Display cases
//		UserChoice choice = DisplayCasesTableMethod.displayCasesInTableBasic(retrievedCases);//DisplayCasesMethod.displayCases(retrievedCases);
//	
//		// Buy or Quit
//		if(BuyOrQuit.buyOrQuit(choice))
//		    System.out.println("Finish - User Buys: "+choice.getSelectedCase());
//		
//		else
//		    System.out.println("Finish - User Quits");

    }
	
	public void postCycle() throws ExecutionException
    {
		_connector.close();
    }
	
	public Collection<CBRCase> execute()
	{	
		CuatroPreguntas recomendador = new CuatroPreguntas();
		
		try {
			
			recomendador.configure();
			recomendador.preCycle();
			
			CBRQuery query = new CBRQuery();
			
			Game juego = new Game();
			
			juego.setAge(12);
			juego.setPlayingTime(100);
			
			query.setDescription(juego);
			
			return recomendador.cycle(query);
		
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}

}
