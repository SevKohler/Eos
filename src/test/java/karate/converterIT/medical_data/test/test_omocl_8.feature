Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-OBSERVATION.nyha_heart_failure.v1
  # openEHR-EHR-OBSERVATION.nine_hole_peg_test.v1
  # openEHR-EHR-OBSERVATION.nutritional_risk_screening.v1
  # openEHR-EHR-EVALUATION.obstetric_summary.v1
  # openEHR-EHR-EVALUATION.occupation_summary.v1
  # openEHR-EHR-OBSERVATION.paced_auditory_serial_addition_test.v1
  # openEHR-EHR-OBSERVATION.pf_ratio.v1
  # openEHR-EHR-OBSERVATION.exam.v1
  # openEHR-EHR-EVALUATION.precaution.v1
  # openEHR-EHR-OBSERVATION.problem_screening.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_8_v0/omocl_test_instance_8.json')
    And def resultJson = read(test_output_path+'test_omocl_8_v0/omocl_test_instance_8_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
