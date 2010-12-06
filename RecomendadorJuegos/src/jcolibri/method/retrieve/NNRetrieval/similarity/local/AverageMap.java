package jcolibri.method.retrieve.NNRetrieval.similarity.local;

import java.util.Map;
import java.util.Map.Entry;

import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

public class AverageMap implements LocalSimilarityFunction {

	@Override
    public double compute(Object o1, Object o2) throws jcolibri.exception.NoApplicableSimilarityFunctionException{
        if ((o1 == null) || (o2 == null) || !(o1 instanceof Map) || !(o2 instanceof Map))
        	return 0;
        
        Entry<?, ?>[] list1 = (Entry<?, ?>[])((Map<?, ?>)o1).entrySet().toArray();
        Entry<?, ?>[] list2 = (Entry<?, ?>[])((Map<?, ?>)o2).entrySet().toArray();
        
        double d  = 1.0 / list1.length;
        double sim = 0.0;
        
        for (int i = 0; i < list1.length; i++)
        	for (int j = 0; j < list2.length; j++)
        		if (list1[i].getKey().equals(list2[j].getKey())) sim += d;
        
        return sim;
    }

	@Override
	public boolean isApplicable(Object caseObject, Object queryObject) {
		if (queryObject instanceof Map) return true;
		else return false;
	}

}
