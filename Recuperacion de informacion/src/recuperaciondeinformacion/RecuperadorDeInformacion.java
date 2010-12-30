/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package recuperaciondeinformacion;

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
import jcolibri.exception.ExecutionException;
import jcolibri.extensions.textual.IE.opennlp.IETextOpenNLP;
import jcolibri.extensions.textual.lucene.LuceneIndexSpanish;
import jcolibri.method.retrieve.RetrievalResult;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import jcolibri.method.retrieve.NNretrieval.similarity.local.textual.LuceneTextSimilaritySpanish;
import jcolibri.method.retrieve.selection.SelectCases;
import jcolibri.test.main.SwingProgressBar;
import recuperaciondeinformacion.test.Test;
import recuperaciondeinformacion.utils.NewsConnector;
import recuperaciondeinformacion.utils.representation.NewsDescription;

/**
 *
 * @author usuario_local
 */
public class RecuperadorDeInformacion  implements StandardCBRApplication{

    Connector _connector;
    CBRCaseBase _caseBase;

    LuceneIndexSpanish luceneIndex;

    /*
     * (non-Javadoc)
     *
     * @see jcolibri.cbraplications.BasicCBRApplication#configure()
     */
    public void configure() throws ExecutionException
    {
	try
	{
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

	return _caseBase;
    }

    /*
     * (non-Javadoc)
     *
     * @see jcolibri.cbraplications.StandardCBRApplication#cycle(jcolibri.cbrcore.CBRQuery)
     */
    public void cycle(CBRQuery query) throws ExecutionException
    {
	Collection<CBRCase> cases = _caseBase.getCases();

	NNConfig nnConfig = new NNConfig();
	nnConfig.setDescriptionSimFunction(new Average());


	//We only compare the "description" attribute using Lucene
	Attribute textualAttribute = new Attribute("title", NewsDescription.class);
	nnConfig.addMapping(textualAttribute, new LuceneTextSimilaritySpanish(luceneIndex,query,textualAttribute, true));
    textualAttribute = new Attribute("text", NewsDescription.class);
    nnConfig.addMapping(textualAttribute, new LuceneTextSimilaritySpanish(luceneIndex,query,textualAttribute, true));


	System.out.println("RESULT: ");

	Collection<RetrievalResult> res = NNScoringMethod.evaluateSimilarity(cases, query, nnConfig);
	res = SelectCases.selectTopKRR(res, 5);

	for(RetrievalResult rr: res)
	    System.out.println(rr);

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
                            queryDescription.setText(new Text(queryString));
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
}
