package karate.converterIT.medical_data.instruction;

import com.intuit.karate.junit5.Karate;

public class InstructionRunner {

    @Karate.Test
    Karate testInstructions() {
        return Karate.run().relativeTo(getClass());
    }

}
