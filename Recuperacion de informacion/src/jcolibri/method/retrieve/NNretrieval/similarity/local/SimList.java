package jcolibri.method.retrieve.NNretrieval.similarity.local;

import java.util.List;

import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

public class SimList implements LocalSimilarityFunction {
    @Override
    public double compute(Object o1, Object o2) throws jcolibri.exception.NoApplicableSimilarityFunctionException{
        if ((o1 == null) || (o2 == null) || !(o1 instanceof List) || !(o2 instanceof List))
                return 0;
        
        List list1 = ((List<?>)o1);
        List list2 = ((List<?>)o2);
        
        double d  = 1.0 / list2.size();
        double sim = 0.0;
        
        for (Object obj0 : list2) {
                        for (Object obj1 : list1) {
                                if (obj0.equals(obj1)) sim += d;
                        }
                }


        return sim;
    }


    @Override
    public boolean isApplicable(Object caseObject, Object queryObject) {
            if (queryObject instanceof List) return true;
            else return false;
    }
}
