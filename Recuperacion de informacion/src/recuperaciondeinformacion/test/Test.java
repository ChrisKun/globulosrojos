/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package recuperaciondeinformacion.test;

import jcolibri.evaluation.Evaluator;
import jcolibri.evaluation.evaluators.HoldOutEvaluator;
import recuperaciondeinformacion.RecuperadorDeInformacion;

/**
 *
 * @author usuario_local
 */
public class Test {

    public static void HoldOutEvaluation()
    {
            //SwingProgressBar shows the progress
    jcolibri.util.ProgressController.clear();
    jcolibri.util.ProgressController.register(new jcolibri.test.main.SwingProgressBar(), HoldOutEvaluator.class);

    // Example of the Hold-Out evaluation
            HoldOutEvaluator eval = new HoldOutEvaluator();
            eval.init(new RecuperadorDeInformacion());
            eval.HoldOut(15, 1);


            System.out.println(Evaluator.getEvaluationReport());
            jcolibri.evaluation.tools.EvaluationResultGUI.show(Evaluator.getEvaluationReport(), "Recuperador de información - Evaluation", false);
    }

    public static void main(String[] args) {
        Test.HoldOutEvaluation();
    }
}
