package grupo14.aprendizaje.CBR;

import java.util.Collection;

import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbraplications.StandardCBRApplication;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.connector.PlainTextConnector;
import jcolibri.exception.ExecutionException;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void postCycle() throws ExecutionException {
		this.connector.close();
	}

}
