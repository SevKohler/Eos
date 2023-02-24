package karate.converterIT.medical_data.action;

import com.intuit.karate.junit5.Karate;

public class ActionRunner  {

    @Karate.Test
    Karate testActions() {
        return Karate.run().relativeTo(getClass());
    }
}
