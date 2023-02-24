package karate.converterIT.medical_data.evaluation;

import com.intuit.karate.junit5.Karate;

public class EvaluationRunner {

        @Karate.Test
        Karate testEvaluations() {
            return Karate.run().relativeTo(getClass());
        }

}
