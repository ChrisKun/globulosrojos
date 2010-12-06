package criticar;

import java.util.ArrayList;
import java.util.Collection;

import sistema.Game;
import sistema.Sistema;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRQuery;
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
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.selection.SelectCases;

public class InterfazCriticar {

	private Collection<CBRCase> selectedCases;
	private Collection<CritiqueOption> criticas;
	
	public InterfazCriticar(Collection<CBRCase> selectedCases) {
		this.selectedCases = selectedCases;
		criticas = new ArrayList<CritiqueOption>();
		criticas.add(new CritiqueOption("Más jugadores recomendados",new Attribute("numPlayers", Game.class),new QueryLess()));
		criticas.add(new CritiqueOption("Más edad recomendada",new Attribute("age", Game.class),new QueryLess()));
		criticas.add(new CritiqueOption("Otra mecánica de juego",new Attribute("mechanics", Game.class),new NotEqual()));
	}
	
	public void show() {
		boolean fin = false;
		CBRQuery query = null;
		while (!fin) {
			CriticalUserChoice choice = DisplayCasesTableWithCritiquesMethod.displayCasesInTableWithCritiques(selectedCases, criticas, Sistema.getCBjuegosInstance().getCases());
			if(ContinueOrFinish.continueOrFinish(choice)){
				query = choice.getSelectedCaseAsQuery();
				MoreLikeThis.moreLikeThis(query, choice.getSelectedCase());
				// No tiene filtrado
				// TODO Queda el simConfig (null)
				Collection<RetrievalResult> retrievedCases = NNScoringMethod.evaluateSimilarity(Sistema.getCBjuegosInstance().getCases(), query, null);
				selectedCases = SelectCases.selectTopK(retrievedCases, 10);
			}
			else {
				fin = true;
				if(BuyOrQuit.buyOrQuit(choice))
				    System.out.println("Finish - User Buys: " + choice.getSelectedCase());
				
				else
				    System.out.println("Finish - User Quits");
		    }
		}
	}
}
