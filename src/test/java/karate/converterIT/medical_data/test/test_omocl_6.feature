Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-OBSERVATION.howru.v1
  # openEHR-EHR-OBSERVATION.imaging_exam_result.v1
  # openEHR-EHR-OBSERVATION.ipss.v1
  # openEHR-EHR-OBSERVATION.karnofsky_performance_status_scale.v1
  # openEHR-EHR-OBSERVATION.laboratory_test_result.v1
  # openEHR-EHR-EVALUATION.last_menstrual_period.v1
  # openEHR-EHR-OBSERVATION.lenke_classification.v1
  # openEHR-EHR-OBSERVATION.malnutrition_screening_tool.v1
  # openEHR-EHR-OBSERVATION.mayo_score.v1
  # openEHR-EHR-OBSERVATION.map_hand.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_6_v0/omocl_test_instance_6.json')
    And def resultJson = read(test_output_path+'test_omocl_6_v0/omocl_test_instance_6_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
