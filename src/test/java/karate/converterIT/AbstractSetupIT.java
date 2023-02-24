package karate.converterIT;

import org.bih.eos.jpabase.model.entity.Person;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public abstract class AbstractSetupIT {

    protected static long patientId;

/*
    @Autowired
    private ConceptDao conceptDao;
*/

    @BeforeAll
    static void setup() {
      /*  EHRToPerson ehrToPerson = new EHRToPerson();
        ehrToPerson.setEhrId("integration_test_ehr");
        ehrToPerson.setPerson(createTestPerson());
        patientId = ehrToPerson.getPerson().getId();
        persistenceService.persistEhrToPerson(ehrToPerson);*/
    }

    private static Person createTestPerson() {
    /*    Person person = new Person();
        person.setGenderSourceConcept(conceptService.findById(0L));
        person.setRaceSourceConcept(conceptService.findById(0L));
        person.setEthnicitySourceConcept(conceptService.findById(0L));
        return personService.create(person);*/
        return null;
    }

    @AfterAll
    private static void asd() {
        // personService.removeById(patientId);
    }

}
