/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package recuperaciondeinformacion.test;

import jcolibri.evaluation.Evaluator;
import jcolibri.evaluation.evaluators.HoldOutEvaluator;
import jcolibri.exception.ExecutionException;
import recuperaciondeinformacion.RecuperadorDeInformacion;

/**
 *
 * @author usuario_local
 */
public class Test {

	public static void prueba() {
		Thread t = new Thread(new Runnable() {
			public void run()
			{
				RecuperadorDeInformacion test = new RecuperadorDeInformacion();
				
				try {
					test.configure();
					test.preCycle();		
				
					test.HoldOutEvaluation();
					
					java.util.Vector<Double> vec = Evaluator.getEvaluationReport().getSeries("Errores");
					double avg = 0.0;
					for (Double d: vec)
						avg+=d;
					avg=avg/(double)Evaluator.getEvaluationReport().getNumberOfCycles();
					avg = 1- avg;
					Evaluator.getEvaluationReport().putOtherData("Media errores", Double.toString(avg));
						
					java.util.Vector<Double> vec2 = Evaluator.getEvaluationReport().getSeries("Confianza");
					avg = 0.0;
					for (Double d: vec2)
						avg+=d;
					avg=avg/(double)Evaluator.getEvaluationReport().getNumberOfCycles();
					Evaluator.getEvaluationReport().putOtherData("Media confianza", Double.toString(avg));
						
					System.out.println(Evaluator.getEvaluationReport());
					jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Evaluacion Recuperador Informacion", false);
				} catch (ExecutionException e) {
				}
			}
		});
		t.start();
	}

    public static void main(String[] args) {
        Test.prueba();
    }
}
