package preguntas;

import java.util.ArrayList;
import java.util.Collection;

import data.Game;
import data.GameConnector;
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
		//simConfig.addMapping(new Attribute("numPlayers", Game.class), new Equal());
		simConfig.addMapping(new Attribute("playingTime", Game.class), new Equal());
		simConfig.addMapping(new Attribute("age", Game.class), new Equal());
		//simConfig.addMapping(new Attribute("categories", Game.class), new Equal());
		
		hiddenAtts = new ArrayList<Attribute>();
		hiddenAtts.add(new Attribute("id", Game.class));
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
	
	public void cycle(CBRQuery query) throws ExecutionException
    {
		// Obtain query
		ObtainQueryWithFormMethod.obtainQueryWithInitialValues(query,hiddenAtts,null);
		
		// Execute KNN
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		
		// Select cases
		Collection<CBRCase> retrievedCases = SelectCases.selectTopK(eval, 5);
		
		// Display cases
		UserChoice choice = DisplayCasesTableMethod.displayCasesInTableBasic(retrievedCases);//DisplayCasesMethod.displayCases(retrievedCases);
	
		// Buy or Quit
		if(BuyOrQuit.buyOrQuit(choice))
		    System.out.println("Finish - User Buys: "+choice.getSelectedCase());
		
		else
		    System.out.println("Finish - User Quits");

    }
	
	public void postCycle() throws ExecutionException
    {
		_connector.close();
    }
	
	public static void main(String[] args)
	{
		CuatroPreguntas recomendador = new CuatroPreguntas();
		
		try {
			
			recomendador.configure();
			recomendador.preCycle();
			
			CBRQuery query = new CBRQuery();
			
			Game juego = new Game();
			
			juego.setName("eii");
			juego.setPlayingTime(100);
			
			query.setDescription(juego);
			
			recomendador.cycle(query);
		
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
