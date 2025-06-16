package karate.converterIT.visit_data;

import com.intuit.karate.junit5.Karate;

public class VisitRunner {

    @Karate.Test
    Karate testVisits() {
        return Karate.run().relativeTo(getClass());
    }
}
