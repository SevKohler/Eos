package karate.converterIT.person_data;

import com.intuit.karate.junit5.Karate;

public class PersonRunner {

    @Karate.Test
    Karate testPersons() {
        return Karate.run().relativeTo(getClass());
    }
}
