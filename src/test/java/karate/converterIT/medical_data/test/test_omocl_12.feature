Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-OBSERVATION.berg_balance_scale.v1
  # openEHR-EHR-ADMIN_ENTRY.citizenship.v1
  # openEHR-EHR-OBSERVATION.guss.v1
  # openEHR-EHR-OBSERVATION.guss_icu.v1
  # openEHR-EHR-EVALUATION.hand_dominance.v1
  # openEHR-EHR-OBSERVATION.physical_activity_screening.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Convert mapped archetypes

    And def inputJson = read(test_composition_path+'test_omocl_12_v0/omocl_test_instance_12.json')
    And def resultJson = read(test_output_path+'test_omocl_12_v0/omocl_test_instance_12_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
