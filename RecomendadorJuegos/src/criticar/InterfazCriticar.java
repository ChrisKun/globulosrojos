package criticar;

import java.util.ArrayList;
import java.util.Collection;

import sistema.Game;
import sistema.Sistema;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.exception.ExecutionException;
import jcolibri.extensions.recommendation.casesDisplay.UserChoice;
import jcolibri.extensions.recommendation.conditionals.BuyOrQuit;
import jcolibri.extensions.recommendation.conditionals.ContinueOrFinish;
import jcolibri.extensions.recommendation.navigationByProposing.CriticalUserChoice;
import jcolibri.extensions.recommendation.navigationByProposing.CritiqueOption;
import jcolibri.extensions.recommendation.navigationByProposing.DisplayCasesTableWithCritiquesMethod;
import jcolibri.extensions.recommendation.navigationByProposing.queryElicitation.MoreLikeThis;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.FilterBasedRetrieval.FilterBasedRetrievalMethod;
import jcolibri.method.retrieve.FilterBasedRetrieval.FilterConfig;
import jcolibri.method.retrieve.FilterBasedRetrieval.predicates.NotEqual;
import jcolibri.method.retrieve.FilterBasedRetrieval.predicates.QueryLess;
import jcolibri.method.retrieve.NNRetrieval.NNConfig.SimConfigJuegos;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.selection.SelectCases;

public class InterfazCriticar {

	private Collection<CBRCase> selectedCases;
	private Collection<CritiqueOption> criticas;
	
	public InterfazCriticar(Collection<CBRCase> selectedCases) {
		this.selectedCases = selectedCases;
		criticas = new ArrayList<CritiqueOption>();
		criticas.add(new CritiqueOption("Más jugadores recomendados",new Attribute("minNumPlayers", Game.class),new QueryLess()));
		criticas.add(new CritiqueOption("Más edad recomendada",new Attribute("age", Game.class),new QueryLess()));
		criticas.add(new CritiqueOption("Otra mecánica de juego",new Attribute("mechanics", Game.class),new NotEqual()));
	}
	
    public void sequence1(CBRQuery query, FilterConfig filterConfig)  throws ExecutionException
    {	
		// Execute Filter
		Collection<CBRCase> filtered = FilterBasedRetrievalMethod.filterCases(Sistema.getCBjuegosInstance().getCases(), query, filterConfig);
		
		// Execute NN
		Collection<RetrievalResult> retrievedCases = NNScoringMethod.evaluateSimilarity(filtered, query, new SimConfigJuegos());
		
		// Select cases
		Collection<CBRCase> selectedCases = SelectCases.selectTopK(retrievedCases, 10);
	    	
		// Obtain critizied query
		CriticalUserChoice choice = DisplayCasesTableWithCritiquesMethod.displayCasesInTableWithCritiques(selectedCases, criticas, Sistema.getCBjuegosInstance().getCases());
		
		if(ContinueOrFinish.continueOrFinish(choice))
		    sequence2(choice.getSelectedCaseAsQuery(), choice);
		else
		    sequence3(choice, selectedCases);
    }
    
    public void sequence2(CBRQuery query, CriticalUserChoice cuc) throws ExecutionException
    {
		// Replaze current query with the critizied one
		MoreLikeThis.moreLikeThis(query, cuc.getSelectedCase());
		sequence1(query, cuc.getFilterConfig());
    }
    
    public void sequence3(UserChoice choice, Collection<CBRCase> retrievedCases)  throws ExecutionException
    {
		if(BuyOrQuit.buyOrQuit(choice))
		    System.out.println("Finish - User Buys: "+choice.getSelectedCase());
		
		else
		    System.out.println("Finish - User Quits");
    }
	
	public void show() {
		CriticalUserChoice choice = DisplayCasesTableWithCritiquesMethod.displayCasesInTableWithCritiques(selectedCases, criticas, Sistema.getCBjuegosInstance().getCases());
		
		try {
		if(ContinueOrFinish.continueOrFinish(choice))
				sequence2(choice.getSelectedCaseAsQuery(), choice);
		else
		    sequence3(choice, selectedCases);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
