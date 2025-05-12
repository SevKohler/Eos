Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-OBSERVATION.braden_scale.v1
  # openEHR-EHR-OBSERVATION.bvc.v1
  # openEHR-EHR-OBSERVATION.capillary_refill.v1
  # openEHR-EHR-EVALUATION.cause_of_death.v1
  # openEHR-EHR-OBSERVATION.cpax.v1
  # openEHR-EHR-OBSERVATION.cgas.v1
  # openEHR-EHR-OBSERVATION.clinical_frailty_scale2.v1
  # openEHR-EHR-OBSERVATION.clinical_frailty_scale.v1
  # openEHR-EHR-EVALUATION.clinical_synopsis.v1
  # openEHR-EHR-EVALUATION.communication_capability.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_2_v0/omocl_test_instance_2.json')
    And def resultJson = read(test_output_path+'test_omocl_2_v0/omocl_test_instance_2_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
