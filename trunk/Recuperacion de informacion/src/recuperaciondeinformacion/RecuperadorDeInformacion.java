/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package recuperaciondeinformacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import jcolibri.casebase.LinealCaseBase;
import jcolibri.cbraplications.StandardCBRApplication;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRCase;
import jcolibri.cbrcore.CBRCaseBase;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.cbrcore.Connector;
import jcolibri.datatypes.Text;
import jcolibri.evaluation.Evaluator;
import jcolibri.evaluation.evaluators.HoldOutEvaluator;
import jcolibri.evaluation.evaluators.LeaveOneOutEvaluator;
import jcolibri.evaluation.evaluators.SameSplitEvaluator;
import jcolibri.exception.ExecutionException;
import jcolibri.extensions.textual.IE.common.BasicInformationExtractor;
import jcolibri.extensions.textual.IE.common.FeaturesExtractor;
import jcolibri.extensions.textual.IE.common.StopWordsDetectorSpanish;
import jcolibri.extensions.textual.IE.common.TextStemmerSpanish;
import jcolibri.extensions.textual.IE.opennlp.IETextOpenNLP;
import jcolibri.extensions.textual.IE.opennlp.OpennlpPOStaggerSpanish;
import jcolibri.extensions.textual.IE.opennlp.OpennlpSplitterSpanish;
import jcolibri.extensions.textual.IE.representation.IEText;
import jcolibri.extensions.textual.IE.representation.Token;
import jcolibri.extensions.textual.lucene.LuceneIndexSpanish;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.SimList;
import jcolibri.method.retrieve.NNretrieval.similarity.local.textual.LuceneTextSimilaritySpanish;
import jcolibri.method.retrieve.selection.SelectCases;
import jcolibri.test.main.SwingProgressBar;
import recuperaciondeinformacion.gui.ListaNoticias;
import recuperaciondeinformacion.utils.NewsConnector;
import recuperaciondeinformacion.utils.representation.NewsDescription;
import recuperaciondeinformacion.utils.representation.NewsSolution;

/**
 *
 * @author usuario_local
 */
public class RecuperadorDeInformacion  implements StandardCBRApplication{

    Connector _connector;
    CBRCaseBase _caseBase;

    LuceneIndexSpanish luceneIndex;
    Prediction prediccion;
    private boolean isEvaluation;
    
    public boolean isIE = false;
    public boolean acciones;
    public boolean propiedades;

    public void setAcciones(boolean acciones) {
    	this.acciones = acciones;
    }
    
    public void setPropiedades(boolean propiedades) {
    	this.propiedades = propiedades;
    }
    
    public RecuperadorDeInformacion(boolean isEvaluation)
    {
    	super();
    	this.isEvaluation = isEvaluation;
    }
    
    public RecuperadorDeInformacion()
    {
    	super();
    	isEvaluation = false;
    }
    /*
     * (non-Javadoc)
     *
     * @see jcolibri.cbraplications.BasicCBRApplication#configure()
     */
    public void configure() throws ExecutionException
    {
	try
	{
		/////////////////////////////////
		//para la visualizacion de la base de casos
		jcolibri.util.ProgressController.clear();
    	jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), jcolibri.extensions.visualization.CasesVisualization.class);
		/////////////////////////////////
    	
	    _connector = new NewsConnector("data/noticias", 150);
	    _caseBase = new LinealCaseBase();

	    jcolibri.util.ProgressController.clear();
	    SwingProgressBar pb = new SwingProgressBar();
	    jcolibri.util.ProgressController.register(pb);
	} catch (Exception e)
	{
	    throw new ExecutionException(e);
	}
    }

    /*
     * (non-Javadoc)
     *
     * @see jcolibri.cbraplications.StandardCBRApplication#preCycle()
     */
    public CBRCaseBase preCycle() throws ExecutionException
    {
    	
		_caseBase.init(_connector);
	
		//Here we create the Lucene index
		luceneIndex = jcolibri.method.precycle.LuceneIndexCreatorSpanish.createLuceneIndex(_caseBase);
		
		Collection<CBRCase> cases = _caseBase.getCases();
		// Divide el texto en parrafos, frases y palabras
		OpennlpSplitterSpanish.split(cases); 
		// Borra las palabras vacias
		StopWordsDetectorSpanish.detectStopWords(cases); 
		// Extrae las raices de cada palabra
		TextStemmerSpanish.stem(cases); 
		// Realiza el etiquetado morfologico
		OpennlpPOStaggerSpanish.tag(cases); 
		// Extraer los nombres y verbos almacenandolos en los atributos "nombres" y "verbos"
		extractMainTokens(cases);
		// Cargar las caracteristicas
		FeaturesExtractor.loadRules("src/featuresRules.txt");
		// Extraemos las caracteristicas de los casos
		FeaturesExtractor.extractFeatures(cases);
		//Perform IE copying extracted features or phrases into other attributes of the case
		BasicInformationExtractor.extractInformation(cases);
	
		return _caseBase;
    }

    /*
     * (non-Javadoc)
     *
     * @see jcolibri.cbraplications.StandardCBRApplication#cycle(jcolibri.cbrcore.CBRQuery)
     */
    public void cycle(CBRQuery query) throws ExecutionException
    {
    	
		// Divide el texto en parrafos, frases y palabras
		OpennlpSplitterSpanish.split(query); 
		// Borra las palabras vacias
		StopWordsDetectorSpanish.detectStopWords(query); 
		// Extrae las raices de cada palabra
		TextStemmerSpanish.stem(query); 
		// Realiza el etiquetado morfologico 
		OpennlpPOStaggerSpanish.tag(query); 
		// Extraer los nombres y verbos almacenandolos en los atributos "nombres" y "verbos" 
		extractMainTokens(query);
		// Extraemos las caracteristicas de la query
		FeaturesExtractor.extractFeatures(query);
		// Extraemos caracteristicas del texto
		BasicInformationExtractor.extractInformation(query);
		
		Collection<CBRCase> cases = _caseBase.getCases();
	
		NNConfig nnConfig = new NNConfig();
		nnConfig.setDescriptionSimFunction(new Average());
	
	
		//We only compare the "description" attribute using Lucene
		Attribute textualAttribute = new Attribute("title", NewsDescription.class);
		nnConfig.addMapping(textualAttribute, new LuceneTextSimilaritySpanish(luceneIndex,query,textualAttribute, true));
		nnConfig.setWeight(textualAttribute, 0.3);
	    textualAttribute = new Attribute("text", NewsDescription.class);
	    nnConfig.addMapping(textualAttribute, new LuceneTextSimilaritySpanish(luceneIndex,query,textualAttribute, true));
	    nnConfig.setWeight(textualAttribute, 0.3);
	    if (acciones) {
	        textualAttribute = new Attribute("nombres", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 0.6);
	        textualAttribute = new Attribute("verbos", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 0.6);
	    }
	    if (propiedades) {
		    textualAttribute = new Attribute("Politicos", NewsDescription.class);
		    nnConfig.addMapping(textualAttribute, new SimList());
		    nnConfig.setWeight(textualAttribute, 1.0);
	        textualAttribute = new Attribute("JugadoresRealMadrid", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 1.0);
	        textualAttribute = new Attribute("JugadoresBarcelona", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 1.0);
	        textualAttribute = new Attribute("PresidentesFutbol", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 1.0);
	        textualAttribute = new Attribute("EntrenadoresFutbol", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 1.0);
	        textualAttribute = new Attribute("JugadoresTenis", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 1.0);
	        textualAttribute = new Attribute("CorredoresFormula1", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 1.0);
	        textualAttribute = new Attribute("Paises", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 1.0);
	        textualAttribute = new Attribute("Bancos", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 1.0);
	        textualAttribute = new Attribute("JugadoresBaloncesto", NewsDescription.class);
	        nnConfig.addMapping(textualAttribute, new SimList());
	        nnConfig.setWeight(textualAttribute, 1.0);
	        }
	
	    ///////////////////////////////////////////////
	    //Visualizacion de la base de casos
	    //jcolibri.extensions.visualization.CasesVisualization.visualize(_caseBase.getCases(), nnConfig);
	    ///////////////////////////////////////////////  
	    
		System.out.println("RESULT: ");
	
		Collection<RetrievalResult> res = NNScoringMethod.evaluateSimilarity(cases, query, nnConfig);
		res = SelectCases.selectTopKRR(res, 5);
	
		for(RetrievalResult rr: res)
		    System.out.println(rr);
		
		////////////////////////////////////////////
		//votacion ponderada para usarlo en los test
		if(isEvaluation)
		{
	        SimilarityWeightedVotingMethod prueba = new SimilarityWeightedVotingMethod();
	        prediccion = prueba.getPredictedClass(res);
		
	        //esto es para las evaluaciones
	        CBRCase caso = new CBRCase();
	        caso = (CBRCase)query;
	        		
	        NewsSolution sol = (NewsSolution)caso.getSolution();
	        double pre;
	        if(sol.getCategory().equals((prediccion.Classification.toString())))
	                pre = 1.0;
	        else 
	                pre = 0.0;
	        System.out.println("sol.getRes()"+sol.getCategory().toString()+" - "+prediccion.Classification.toString());
	        Evaluator.getEvaluationReport().addDataToSeries("Errores", pre);
	        Evaluator.getEvaluationReport().addDataToSeries("Confianza", prediccion.getConfidence());
		}
        
		// MENU
		ArrayList<CBRCase> news = new ArrayList<CBRCase>();
		for(RetrievalResult rr: res)
			news.add(rr.get_case());
		ListaNoticias menu = new ListaNoticias(news);
		menu.pack();
		menu.setVisible(true);  
		
		NewsDescription qrd = (NewsDescription)query.getDescription();
		CBRCase mostSimilar = res.iterator().next().get_case();
		NewsDescription rrd = (NewsDescription)mostSimilar.getDescription();
	    
	    // TODO Mostrar interfaz mostrando los resultados
		//new ResultFrame(qrd.getDescription().getRAWContent(), rrd.getName(), rrd.getAddress(), rrd.getDescription().getRAWContent());
    }

    /*
     * (non-Javadoc)
     *
     * @see jcolibri.cbraplications.StandardCBRApplication#postCycle()
     */
    public void postCycle() throws ExecutionException
    {
	_connector.close();

    }
    
	public void extractMainTokens(Collection<CBRCase> cases)
	{
		for(CBRCase c: cases)
			extractMainTokens((NewsDescription)c.getDescription());
	}
	
	public void extractMainTokens(CBRQuery query)
	{
			extractMainTokens((NewsDescription)query.getDescription());
	}
	
	public void extractMainTokens(NewsDescription desc)
	{
		ArrayList<String> nombres = new ArrayList<String>();
		ArrayList<String> verbos = new ArrayList<String>();
		
		getMainTokens((IEText)desc.getText(),nombres, verbos);
		desc.setNombres(nombres);
		desc.setVerbos(verbos);
	}

	void getMainTokens(IEText text, Collection<String> names, Collection<String> verbs)
	{
		for(Token t: text.getAllTokens())
		{
			if(t.getPostag().startsWith("N"))
				if(t.getStem()!=null)
					names.add(t.getStem());
			if(t.getPostag().startsWith("V"))
				if(t.getStem()!=null)
					verbs.add(t.getStem());
		}
	}



    public static void main(String[] args)
    {
        RecuperadorDeInformacion rdi = new RecuperadorDeInformacion();
        try {
            rdi.configure();
            rdi.preCycle();
            boolean _continue = true;
            while(_continue)
            {
                    String queryString = javax.swing.JOptionPane.showInputDialog("Please enter the news description:");
                    if(queryString == null)
                    _continue = false;
                    else
                    {
                            CBRQuery query = new CBRQuery();
                            NewsDescription queryDescription = new NewsDescription();
                            //TODO Tener la posibilidad de mandarlo como texto, título, los 2, lo que sea
                            //TODO Cambiar el Text a IETextOpenNLP para extraer información semántica
                            queryDescription.setText(new IETextOpenNLP(queryString));
                            queryDescription.setTitle(new Text(queryString));
                            query.setDescription(queryDescription);

                            rdi.cycle(query);
                    }
            }
               rdi.postCycle();
        } catch (ExecutionException ex) {
            Logger.getLogger(RecuperadorDeInformacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void HoldOutEvaluation()
    {
            //SwingProgressBar shows the progress
    jcolibri.util.ProgressController.clear();
    jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), HoldOutEvaluator.class);
    
    // Example of the Hold-Out evaluation
            HoldOutEvaluator eval = new HoldOutEvaluator();
            eval.init(new RecuperadorDeInformacion(true));
            eval.HoldOut(15, 1);
            
            
//          System.out.println(Evaluator.getEvaluationReport());
//          jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Quiniela - Evaluation", false);
    }
    
    public void LeaveOneOutEvaluation()
    {
            //SwingProgressBar shows the progress
    jcolibri.util.ProgressController.clear();
    jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), LeaveOneOutEvaluator.class);
    
    //Example of the Leave-One-Out evaluation
            
            LeaveOneOutEvaluator eval = new LeaveOneOutEvaluator();
            eval.init(new RecuperadorDeInformacion(true));
            eval.LeaveOneOut();
            
//          System.out.println(Evaluator.getEvaluationReport());
//          jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Quiniela - Evaluation", false);
    }
    
    public void SameSplitEvaluation()
    {
            //SwingProgressBar shows the progress
    jcolibri.util.ProgressController.clear();
    jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), SameSplitEvaluator.class);
    
    // Example of the Same-Split evaluation
            
            SameSplitEvaluator eval = new SameSplitEvaluator();
            eval.init(new RecuperadorDeInformacion(true));
            eval.generateSplit(10, "split1.txt");
            eval.HoldOutfromFile("split1.txt");
            
//          System.out.println(Evaluator.getEvaluationReport());
//          jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Quiniela - Evaluation", false);
    }

}
