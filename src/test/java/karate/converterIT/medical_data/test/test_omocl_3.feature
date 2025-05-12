Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-EVALUATION.contraceptive_summary.v1
  # openEHR-EHR-EVALUATION.contraindication.v1
  # openEHR-EHR-OBSERVATION.cormack_lehane.v1
  # openEHR-EHR-OBSERVATION.crb_65.v1
  # openEHR-EHR-OBSERVATION.curb_65.v1
  # openEHR-EHR-EVALUATION.death_summary.v1
  # openEHR-EHR-OBSERVATION.investigation_screening.v1
  # openEHR-EHR-EVALUATION.differential_diagnoses.v1
  # openEHR-EHR-OBSERVATION.ecg_result.v1
  # openEHR-EHR-OBSERVATION.ecog.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_3_v0/omocl_test_instance_3.json')
    And def resultJson = read(test_output_path+'test_omocl_3_v0/omocl_test_instance_3_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
