package cbr;

import gui.Opciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;

import antlr.collections.impl.Vector;

import jcolibri.casebase.CachedLinealCaseBase;
import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.connector.DataBaseConnector;
import jcolibri.connector.PlainTextConnector;
import jcolibri.evaluation.Evaluator;
import jcolibri.evaluation.evaluators.HoldOutEvaluator;
import jcolibri.evaluation.evaluators.LeaveOneOutEvaluator;
import jcolibri.evaluation.evaluators.SameSplitEvaluator;
import jcolibri.exception.AttributeAccessException;
import jcolibri.exception.ExecutionException;
import jcolibri.extensions.recommendation.casesDisplay.DisplayCasesTableMethod;
import jcolibri.method.gui.formFilling.ObtainQueryWithFormMethod;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.StandardGlobalSimilarityFunction;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import jcolibri.method.retrieve.selection.SelectCases;
import jcolibri.test.test8.EvaluableApp;

public class Quiniela implements jcolibri.cbraplications.StandardCBRApplication 
{
	//Connector object
	Connector _connector;
	//CaseBase object
	CBRCaseBase _caseBase;
	
	Prediction prediccion;
	Collection<RetrievalResult> eval;
	
	private boolean isEvaluation;
	
	public Quiniela(boolean isEvaluation) {
		super();
		this.isEvaluation = isEvaluation;
	}
	
	public Quiniela() {
		super();
		isEvaluation = false;
	}

	public Collection<RetrievalResult> getEval() {
		return eval;
	}

	public Prediction getPrediction() {
		return prediccion;
	}

	private Log log;
	
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

			//Estos se elegiran segun el comboBox
			
			//test.HoldOutEvaluation();
			//test.LeaveOneOutEvaluation();
//			test.SameSplitEvaluation();
//		
//			java.util.Vector<Double> vec = Evaluator.getEvaluationReport().getSeries("Errores");
//			double avg = 0.0;
//			for (Double d: vec)
//				avg+=d;
//			avg=avg/(double)Evaluator.getEvaluationReport().getNumberOfCycles();
//			Evaluator.getEvaluationReport().putOtherData("Media errores", Double.toString(avg));
//			
//			java.util.Vector<Double> vec2 = Evaluator.getEvaluationReport().getSeries("Confianza");
//			avg = 0.0;
//			for (Double d: vec2)
//				avg+=d;
//			avg=avg/(double)Evaluator.getEvaluationReport().getNumberOfCycles();
//			Evaluator.getEvaluationReport().putOtherData("Media confianza", Double.toString(avg));
//			
//			System.out.println(Evaluator.getEvaluationReport());
//			jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Evaluacion Quinielas", false);
//			
//			
			
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
			_connector.initFromXMLfile(jcolibri.util.FileIO.findFile("Ficheros/plaintextconfig.xml"));
			//La organizacion en la memoria ser� lineal
			_caseBase = new CachedLinealCaseBase();
		} catch (Exception e)
		{
			throw new ExecutionException(e);
		}
		log = org.apache.commons.logging.LogFactory.getLog(this.getClass());
		
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		
		//Para configurar el KNN se utiliza un objeto NNCONfig
		NNConfig simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
//		simConfig.setDescriptionSimFunction(new StandardGlobalSimilarityFunction() {
//			
//			@Override
//			public double computeSimilarity(double[] values, double[] weigths,
//					int numberOfvalues) {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//		});
		
		Attribute equipoLocal = new Attribute("equipoLocal",Casos.class);
		Attribute equipoVisitante = new Attribute("equipoVisitante",Casos.class);
		Attribute puntosEquipoLocal = new Attribute("puntosEquipoLocal",Casos.class);
		Attribute puntosEquipoVisitante = new Attribute("puntosEquipoVisitante",Casos.class);
		Attribute diferenciaPuntos = new Attribute("diferenciaPuntos",Casos.class);
		Attribute posicionEquipoLocal = new Attribute("posicionEquipoLocal",Casos.class);
		Attribute posicionEquipoVisitante = new Attribute("posicionEquipoVisitante",Casos.class);
		Attribute golesAFavorEquipoLocal = new Attribute("golesAFavorEquipoLocal",Casos.class);
		Attribute golesEnContraEquipoLocal = new Attribute("golesEnContraEquipoLocal",Casos.class);
		Attribute golesAFavorEquipoVisitante = new Attribute("golesAFavorEquipoVisitante",Casos.class);
		Attribute golesEnContraEquipoVisitante = new Attribute("golesEnContraEquipoVisitante",Casos.class);
		Attribute porcentajeGanagadosLocal = new Attribute("porcentajeGanagadosLocal",Casos.class);
		Attribute porcentajeGanagadosVisitante = new Attribute("porcentajeGanagadosVisitante",Casos.class);
		Attribute resultadoLocal = new Attribute("resultadoLocal",Casos.class);
		Attribute resultadoVisitante = new Attribute("resultadoVisitante",Casos.class);
		
		//Fijamos las funciones de similitud locales
		simConfig.addMapping(equipoLocal,new Equal());
		simConfig.setWeight(equipoLocal, 1.0);
		simConfig.addMapping(equipoVisitante,new Equal());
		simConfig.setWeight(equipoVisitante, 1.0);
		simConfig.addMapping(puntosEquipoLocal,new Interval(100));
		simConfig.setWeight(puntosEquipoLocal, 0.2);
		simConfig.addMapping(puntosEquipoVisitante,new Interval(100));
		simConfig.setWeight(puntosEquipoVisitante, 0.2);		
		simConfig.addMapping(diferenciaPuntos,new Interval(80));
		simConfig.setWeight(diferenciaPuntos, 0.3);
		simConfig.addMapping(posicionEquipoLocal,new Interval(20));
		simConfig.setWeight(posicionEquipoLocal, 0.1);
		simConfig.addMapping(posicionEquipoVisitante,new Interval(20));
		simConfig.setWeight(posicionEquipoVisitante, 0.1);
		simConfig.addMapping(golesAFavorEquipoLocal,new Interval(60));
		simConfig.setWeight(golesAFavorEquipoLocal, 0.01);
		simConfig.addMapping(golesEnContraEquipoLocal,new Interval(60));
		simConfig.setWeight(golesEnContraEquipoLocal, 0.01);
		simConfig.addMapping(golesAFavorEquipoVisitante,new Interval(60));
		simConfig.setWeight(golesAFavorEquipoVisitante, 0.01);
		simConfig.addMapping(golesEnContraEquipoVisitante,new Interval(60));
		simConfig.setWeight(golesEnContraEquipoVisitante, 0.01);
		simConfig.addMapping(porcentajeGanagadosLocal,new Interval(1));
		simConfig.setWeight(porcentajeGanagadosLocal, 0.3);
		simConfig.addMapping(porcentajeGanagadosVisitante,new Interval(1));
		simConfig.setWeight(porcentajeGanagadosVisitante, 0.3);
		simConfig.addMapping(resultadoLocal,new Interval(10));
		simConfig.setWeight(resultadoLocal, 0.001);
		simConfig.addMapping(resultadoVisitante,new Interval(10));
		simConfig.setWeight(resultadoVisitante, 0.001);
		
		log.info("Query: "+ query.getDescription());
		
		//Ejecutamos la recuperacion por vecino mas proximo
		
		eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		

		
		
		//imprimimos los k mejores casos
//		eval = SelectCases.selectTopKRR(eval, 5);
//		
//		//Imprimimos el resultado del k-NN y obtenemos la lista de casos recuperados
//		
//		Collection<CBRCase> casos = new ArrayList<CBRCase>();
//		System.out.println("Casos Recuperados");
//		for(RetrievalResult nse: eval)
//		{
//			System.out.println(nse);
//			casos.add(nse.get_case());
//		}
		//Aqui se incluiria el codigo para adaptar la solucion
		
		//Solamente mostramos el resultado
		//DisplayCasesTableMethod.displayCasesInTableBasic(casos);
	
		eval = SelectCases.selectTopKRR(eval, Opciones.opcionKNN);
		System.out.println("//////////////////////////////////////////////////// " + eval.toString());
		
		
		
		//votacion basica
		if (Opciones.opcionVotacion == Opciones.votacion.BASICA) {
			MajorityVotingMethod prueba = new MajorityVotingMethod();
			prediccion = prueba.getPredictedClass(eval);
			System.out.println("Votacion Basica "+prediccion.Classification.toString()+" __ "+prediccion.getConfidence());
		}
		//votacion ponderada
		else if (Opciones.opcionVotacion == Opciones.votacion.PONDERADA) {
			SimilarityWeightedVotingMethod prueba2 = new SimilarityWeightedVotingMethod();
			prediccion = prueba2.getPredictedClass(eval);
			System.out.println("Votacion ponderada "+prediccion.Classification.toString()+" __ "+prediccion.getConfidence());
		}

		if(isEvaluation)
		{
			//esto es para las evaluaciones
			CBRCase caso = (CBRCase)query;
			Solucion sol = (Solucion)caso.getSolution();
			double pre;
			if(sol.getRes().equals(prediccion.Classification.toString()))
				pre = 1.0;
			else 
				pre = 0.0;
			System.out.println("sol.getRes()"+sol.getRes().toString()+" - "+prediccion.Classification.toString());
			Evaluator.getEvaluationReport().addDataToSeries("Errores", pre);
			Evaluator.getEvaluationReport().addDataToSeries("Confianza", prediccion.getConfidence());
		}
	}

	@Override
	public void postCycle() throws ExecutionException {
		//this._caseBase.close();
		
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
	
	public void HoldOutEvaluation()
	{
		//SwingProgressBar shows the progress
    	jcolibri.util.ProgressController.clear();
    	jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), HoldOutEvaluator.class);
	
    	// Example of the Hold-Out evaluation
		
		HoldOutEvaluator eval = new HoldOutEvaluator();
		eval.init(new Quiniela(true));
		eval.HoldOut(5, 1);
		
		
//		System.out.println(Evaluator.getEvaluationReport());
//		jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Quiniela - Evaluation", false);
	}
	
	public void LeaveOneOutEvaluation()
	{
		//SwingProgressBar shows the progress
    	jcolibri.util.ProgressController.clear();
    	jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), LeaveOneOutEvaluator.class);
	
    	//Example of the Leave-One-Out evaluation
		
		LeaveOneOutEvaluator eval = new LeaveOneOutEvaluator();
		eval.init(new Quiniela(true));
		eval.LeaveOneOut();
		
//		System.out.println(Evaluator.getEvaluationReport());
//		jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Quiniela - Evaluation", false);
	}
	
	public void SameSplitEvaluation()
	{
		//SwingProgressBar shows the progress
    	jcolibri.util.ProgressController.clear();
    	jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), SameSplitEvaluator.class);
	
    	// Example of the Same-Split evaluation
		
		SameSplitEvaluator eval = new SameSplitEvaluator();
		eval.init(new Quiniela(true));
		eval.generateSplit(5, "split1.txt");
		eval.HoldOutfromFile("split1.txt");
		
//		System.out.println(Evaluator.getEvaluationReport());
//		jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Quiniela - Evaluation", false);
	}
	
}
