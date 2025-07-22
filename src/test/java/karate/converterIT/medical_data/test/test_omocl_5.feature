Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-EVALUATION.gender.v1
  # openEHR-EHR-OBSERVATION.glasgow_coma_scale.v1
  # openEHR-EHR-OBSERVATION.glasgow_outcome_scale_extended.v1
  # openEHR-EHR-EVALUATION.goal.v1
  # openEHR-EHR-OBSERVATION.head_circumference.v1
  # openEHR-EHR-ACTION.health_education.v1
  # openEHR-EHR-EVALUATION.health_risk.v1
  # openEHR-EHR-OBSERVATION.hip_circumference.v1
  # openEHR-EHR-OBSERVATION.hirsutism_scales.v1
  # openEHR-EHR-EVALUATION.housing_summary.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_5_v0/omocl_test_instance_5.json')
    And def resultJson = read(test_output_path+'test_omocl_5_v0/omocl_test_instance_5_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
