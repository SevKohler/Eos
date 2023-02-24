package karate.converterIT.medical_data.observation;

import com.intuit.karate.junit5.Karate;

public class ObservationRunner {

    @Karate.Test
    Karate testObservations() {
        return Karate.run().relativeTo(getClass());
    }

}
