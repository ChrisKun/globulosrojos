package jcolibri.method.retrieve.NNretrieval.similarity.local;

import java.util.List;

import jcolibri.exception.NoApplicableSimilarityFunctionException;
import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

public class FeaturesSim implements LocalSimilarityFunction {

	@Override
	public double compute(Object caseObject, Object queryObject) throws NoApplicableSimilarityFunctionException {
        if ((caseObject == null) || (queryObject == null) || !(caseObject instanceof List) || !(queryObject instanceof List))
            return 0;
    
	    List list1 = ((List<?>)caseObject);
	    List list2 = ((List<?>)queryObject);
	    
	    double sim = 0.0;
	    
	    if (list1.size() > 0 && list2.size() > 0)
	    	sim = 1.0;
	    
	    return sim;
	}

	@Override
	public boolean isApplicable(Object caseObject, Object queryObject) {
        if (queryObject instanceof List) return true;
        else return false;
	}

}
