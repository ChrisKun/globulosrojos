package grupo14.aprendizaje.CBR;

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
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;

public class AprendizajeCBR implements StandardCBRApplication {

	private Connector connector;
	private CBRCaseBase caseBase;

	@Override
	public void configure() throws ExecutionException {
		try {
			this.connector = new PlainTextConnector();
			this.connector.initFromXMLfile(jcolibri.util.FileIO
					.findFile("connector/plainTextConfig.xml"));
			this.caseBase = new LinealCaseBase();
		} catch (ExecutionException e) {

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
		
		//TODO Falta ejecutar la consulta
	}

	@Override
	public void postCycle() throws ExecutionException {
		this.connector.close();
	}

}
