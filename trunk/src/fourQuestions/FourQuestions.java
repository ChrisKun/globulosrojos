package fourQuestions;

import gamesData.GameDescription;

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

public class FourQuestions implements StandardCBRApplication {
	/** Connector object */
	Connector _connector;
	/** CaseBase object */
	CBRCaseBase _caseBase;
	/** KNN config */
	NNConfig simConfig;

	public void configure() throws ExecutionException {
		// Create a data base connector
		_connector = new PlainTextConnector();
		// Init the ddbb connector with the config file
		_connector
				.initFromXMLfile(jcolibri.util.FileIO
						.findFile("jcolibri/test/recommenders/housesData/plaintextconfig.xml"));
		// Create a Lineal case base for in-memory organization
		_caseBase = new LinealCaseBase();

		simConfig = new NNConfig();
		// Set the average() global similarity function for the description of
		// the case
		simConfig.setDescriptionSimFunction(new Average());
		// simConfig.addMapping(new Attribute("name", GameDescription.class),
		// new Table("jcolibri/test/recommenders/housesData/area.csv"));
		simConfig
				.addMapping(new Attribute("numPlayers", GameDescription.class),
						new Equal());
		simConfig.addMapping(new Attribute("bestNumPlayers",
				GameDescription.class), new Equal());
		simConfig.addMapping(
				new Attribute("playingTime", GameDescription.class),
				new Equal());
		simConfig.addMapping(new Attribute("age", GameDescription.class),
				new Equal());

	}

	public void cycle(CBRQuery query) throws ExecutionException {
		// Obtain query
		ObtainQueryWithFormMethod.obtainQueryWithInitialValues(query, null, null);

		// Execute KNN
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(
				_caseBase.getCases(), query, simConfig);

		// Select cases
		Collection<CBRCase> retrievedCases = SelectCases.selectTopK(eval, 5);

		// Display cases
		UserChoice choice = DisplayCasesTableMethod
				.displayCasesInTableBasic(retrievedCases);// DisplayCasesMethod.displayCases(retrievedCases);

		// Buy or Quit
		if (BuyOrQuit.buyOrQuit(choice))
			System.out.println("Finish - User Buys: "
					+ choice.getSelectedCase());

		else
			System.out.println("Finish - User Quits");

	}

	public void postCycle() throws ExecutionException {
		_connector.close();
	}

	public CBRCaseBase preCycle() throws ExecutionException {
		// Load cases from connector into the case base
		_caseBase.init(_connector);
		// Print the cases
		java.util.Collection<CBRCase> cases = _caseBase.getCases();
		for (CBRCase c : cases)
			System.out.println(c);
		return _caseBase;
	}

	public static void main(String[] args) {

		StandardCBRApplication recommender = new FourQuestions();

		try {
			recommender.configure();

			recommender.preCycle();

			CBRQuery query = new CBRQuery();

			GameDescription gd = new GameDescription();
			gd.setAge(10);
			gd.setNumPlayers(4);
			gd.setBestNumPlayers(4);
			gd.setPlayingTime(120);
			query.setDescription(gd);

			recommender.cycle(query);

			recommender.postCycle();

		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

}
