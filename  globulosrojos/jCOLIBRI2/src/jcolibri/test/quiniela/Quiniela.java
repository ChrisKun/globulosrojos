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
//		//Llanzar el SGBD
//		//jcolibri.test.database.HSQLDBserver.init();
//		
//		//crear el objeto que implementa la aplicacion CBR
//		Quiniela quinielaApp = new Quiniela();
//		try
//		{
//			//configuracion
//			quinielaApp.configure();
//			// preciclo
//			quinielaApp.preCycle();
//			
//			//crear objeto que almacena la consulta
//			CBRQuery query = new CBRQuery();
//			query.setDescription(new Casos());
//			
//			//Mientras que el usuario quiera (Se muestra ventana de continuar)
//			do
//			{
//				//Obtener los valores de la consulta
//				//ObtainQueryWithFormMethod.obtainQueryWithoutInitialValues(query, null, null);
//				//Ejecutar el ciclo
//				quinielaApp.cycle(query);
//			} while (JOptionPane.showConfirmDialog(null, "Continuar?")==JOptionPane.OK_OPTION);
//		} catch (Exception e)
//		{
//			org.apache.commons.logging.LogFactory.getLog(Quiniela.class).error(e);
//		}
//		
//		//Apagar el SGDB
//		//jcolibri.test.database.HSQLDBserver.shutDown();
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
		
//		//Modom test6
//		//Obtain only the first case
//		CBRCase newcase = _caseBase.getCases().iterator().next();
//		//Modify its id attribute and store it back
//		Attribute id = newcase.getDescription().getIdAttribute();
//		try {
//			Date d = new Date();
//			id.setValue(newcase.getDescription(), ("case "+d.toString()).replaceAll(" ", "_"));
//		} catch (AttributeAccessException e) {
//			org.apache.commons.logging.LogFactory.getLog(this.getClass()).error(e);
//		}
//		
//		ArrayList<CBRCase> casestoLearnt = new ArrayList<CBRCase>();
//		casestoLearnt.add(newcase);
//		_caseBase.learnCases(casestoLearnt);
		
		//Para configurar el KNN se utiliza un objeto NNCONfig
		NNConfig simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
		
		//Fijamos las funciones de similitud locales
		simConfig.addMapping(new Attribute("equipoLocal",Casos.class),new Equal());
		simConfig.addMapping(new Attribute("equipoVisitante",Casos.class),new Equal());
		//simConfig.addMapping(new Attribute("resultado",Casos.class),new Equal());
		simConfig.addMapping(new Attribute("diferenciaPuntos",Casos.class),new Equal());
		simConfig.addMapping(new Attribute("posicionEquipoLocal",Casos.class),new Interval(20));
		simConfig.addMapping(new Attribute("posicionEquipoVisitante",Casos.class),new Interval(20));
		simConfig.addMapping(new Attribute("golesAFavor",Casos.class),new Equal());
		simConfig.addMapping(new Attribute("golesEnContra",Casos.class),new Equal());
		simConfig.addMapping(new Attribute("porcentajeGanagadosLocal",Casos.class),new Interval(100));
		simConfig.addMapping(new Attribute("porcentajeGanagadosVisitante",Casos.class),new Interval(100));
		
		//Es posiblie modificar el peso de cada atributo en la media ponderada
		//Por defecto el peso es 1
		//simConfig.setWeight(new Attribute("Search", TravelDescription.class), 0.5);
		
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
