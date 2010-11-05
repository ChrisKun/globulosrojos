package jcolibri.test.quiniela;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;

import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.connector.DataBaseConnector;
import jcolibri.connector.PlainTextConnector;
import jcolibri.exception.AttributeAccessException;
import jcolibri.exception.ExecutionException;
import jcolibri.extensions.recommendation.casesDisplay.DisplayCasesTableMethod;
import jcolibri.method.gui.formFilling.ObtainQueryWithFormMethod;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import jcolibri.method.retrieve.selection.SelectCases;
import jcolibri.test.test6.Test6;

public class Quiniela implements jcolibri.cbraplications.StandardCBRApplication 
{
	//Connector object
	Connector _connector;
	//CaseBase object
	CBRCaseBase _caseBase;
	
	public static void main(String[] args)
	{
		Quiniela test = new Quiniela();
		try {
			test.configure();
			test.preCycle();
			
			//aqui metemos la GUI
			CBRQuery query = new CBRQuery();
			query.setDescription(new Casos());
			do
			{
				ObtainQueryWithFormMethod.obtainQueryWithoutInitialValues(query, null, null);
				test.cycle(query);
			}while (JOptionPane.showConfirmDialog(null, "Continuar?")==JOptionPane.OK_OPTION);
			
			
		} catch (ExecutionException e) {
			org.apache.commons.logging.LogFactory.getLog(Quiniela.class).error(e);
		}
	}
	
	@Override
	public void configure() throws ExecutionException {
		try
		{
			//Crear el conector con la base de casos
//			_connector = new DataBaseConnector();
			_connector = new PlainTextConnector();
			//Inicializar el conector con su archivo xml de configuracion
			_connector.initFromXMLfile(jcolibri.util.FileIO.findFile("jcolibri/test/quiniela/plaintextconfig.xml"));
			//La organizacion en la memoria será lineal
			_caseBase = new LinealCaseBase();
		} catch (Exception e)
		{
			throw new ExecutionException(e);
		}
		
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		
		//Para configurar el KNN se utiliza un objeto NNCONfig
		NNConfig simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
		
		Attribute equipoLocal = new Attribute("equipoLocal",Casos.class);
		Attribute equipoVisitante = new Attribute("equipoVisitante",Casos.class);
		Attribute diferenciaPuntos = new Attribute("diferenciaPuntos",Casos.class);
		Attribute posicionEquipoLocal = new Attribute("posicionEquipoLocal",Casos.class);
		Attribute posicionEquipoVisitante = new Attribute("posicionEquipoVisitante",Casos.class);
		Attribute golesAFavor = new Attribute("golesAFavor",Casos.class);
		Attribute golesEnContra = new Attribute("golesEnContra",Casos.class);
		Attribute porcentajeGanagadosLocal = new Attribute("porcentajeGanagadosLocal",Casos.class);
		Attribute porcentajeGanagadosVisitante = new Attribute("porcentajeGanagadosVisitante",Casos.class);
		
		//Fijamos las funciones de similitud locales
		simConfig.addMapping(equipoLocal,new Equal());
		simConfig.setWeight(equipoLocal, 1.0);
		simConfig.addMapping(equipoVisitante,new Equal());
		simConfig.setWeight(equipoVisitante, 1.0);
		simConfig.addMapping(diferenciaPuntos,new Equal());
		simConfig.setWeight(diferenciaPuntos, 0.6);
		simConfig.addMapping(posicionEquipoLocal,new Interval(20));
		simConfig.setWeight(posicionEquipoLocal, 0.2);
		simConfig.addMapping(posicionEquipoVisitante,new Interval(20));
		simConfig.setWeight(posicionEquipoVisitante, 0.2);
		simConfig.addMapping(golesAFavor,new Equal());
		simConfig.setWeight(golesAFavor, 0.3);
		simConfig.addMapping(golesEnContra,new Equal());
		simConfig.setWeight(golesEnContra, 0.3);
		simConfig.addMapping(porcentajeGanagadosLocal,new Interval(100));
		simConfig.setWeight(porcentajeGanagadosLocal, 0.7);
		simConfig.addMapping(porcentajeGanagadosVisitante,new Interval(100));
		simConfig.setWeight(porcentajeGanagadosVisitante, 0.7);
		
		//Ejecutamos la recuperacion por vecino mas proximo
		
		Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		
		// imprimimos los k mejores casos
		
		eval = SelectCases.selectTopKRR(eval, 5);
		
		//Imprimimos el resultado del k-NN y obtenemos la lista de casos recuperados
		
		Collection<CBRCase> casos = new ArrayList<CBRCase>();
		System.out.println("Casos Recuperados");
		for(RetrievalResult nse: eval)
		{
			System.out.println(nse);
			casos.add(nse.get_case());
		}
		//Aqui se incluiria el codigo para adaptar la solucion
		
		//Solamente mostramos el resultado
		DisplayCasesTableMethod.displayCasesInTableBasic(casos);
		
		//votacion basica
		MajorityVotingMethod prueba = new MajorityVotingMethod();
		Prediction pre;
		pre = prueba.getPredictedClass(eval);
		System.out.println("Votacion Basica "+pre.Classification.toString()+" __ "+pre.confidence);
		//votacion ponderada
		SimilarityWeightedVotingMethod prueba2 = new SimilarityWeightedVotingMethod();
		pre = prueba2.getPredictedClass(eval);
		System.out.println("Votacion ponderada "+pre.Classification.toString()+" __ "+pre.confidence);
		
		
	}

	@Override
	public void postCycle() throws ExecutionException {
		this._caseBase.close();
		
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		//Cargar los casos desde el conector a la base de casos
		_caseBase.init(_connector);
		//Imprimir los casos leidos
		java.util.Collection<CBRCase> cases = _caseBase.getCases();
		for(CBRCase c: cases)
				System.out.println(c);
		return _caseBase;
	}
	
}
