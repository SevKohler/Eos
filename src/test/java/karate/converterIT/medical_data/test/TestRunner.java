package karate.converterIT.medical_data.test;

import com.intuit.karate.junit5.Karate;

public class TestRunner {

    @Karate.Test
    Karate testTests() {
        return Karate.run().relativeTo(getClass());
    }

}
