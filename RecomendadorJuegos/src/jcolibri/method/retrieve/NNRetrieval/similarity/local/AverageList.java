package jcolibri.method.retrieve.NNRetrieval.similarity.local;

import java.util.List;

import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

public class AverageList implements LocalSimilarityFunction {

	@Override
    public double compute(Object o1, Object o2) throws jcolibri.exception.NoApplicableSimilarityFunctionException{
        if ((o1 == null) || (o2 == null) || !(o1 instanceof List) || !(o2 instanceof List))
        	return 0;
        
        Object[] list1 = ((List<?>)o1).toArray();
        Object[] list2 = ((List<?>)o2).toArray();
        
        double d  = 1.0 / list1.length;
        double sim = 0.0;
        
        for (int i = 0; i < list1.length; i++)
        	for (int j = 0; j < list2.length; j++)
        		if (list1[i].equals(list2[j])) sim += d;
        
        return sim;
    }

	@Override
	public boolean isApplicable(Object caseObject, Object queryObject) {
		if (queryObject instanceof List) return true;
		else return false;
	}

}
