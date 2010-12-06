package jcolibri.method.retrieve.NNRetrieval.similarity.local;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

public class AverageMap implements LocalSimilarityFunction {

	@Override
    public double compute(Object o1, Object o2) throws jcolibri.exception.NoApplicableSimilarityFunctionException{
        if ((o1 == null) || (o2 == null) || !(o1 instanceof Map) || !(o2 instanceof Map))
        	return 0;
        
        Set<?> set1 = ((Map<?, ?>)o1).entrySet();
        Set<?> set2 = ((Map<?, ?>)o2).entrySet();
        
        double d  = 1.0 / set1.size();
        double sim = 0.0;
        
        for (Object elem0 : set1) {
			for (Object elem1 : set2) {
				Entry<Integer, Float> e0 = (Entry<Integer, Float>) elem0;
				Entry<Integer, Float> e1 = (Entry<Integer, Float>) elem1;
				if (e0.getKey().equals(e1.getKey())) sim += d;
			}
		}

        return sim;
    }

	@Override
	public boolean isApplicable(Object caseObject, Object queryObject) {
		if (queryObject instanceof Map) return true;
		else return false;
	}

}
