Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-ACTION.medication.v1
  # openEHR-EHR-OBSERVATION.medication_screening.v1
  # openEHR-EHR-EVALUATION.medication_summary.v1
  # openEHR-EHR-OBSERVATION.menstruation.v1
  # openEHR-EHR-OBSERVATION.menstrual_diary.v1
  # openEHR-EHR-EVALUATION.menstruation_summary.v1
  # openEHR-EHR-OBSERVATION.mallampati_classification.v1
  # openEHR-EHR-OBSERVATION.modified_rankin_scale.v1
  # openEHR-EHR-OBSERVATION.msfc_score.v1
  # openEHR-EHR-OBSERVATION.news_uk_rcp.v1
  # openEHR-EHR-OBSERVATION.news2.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_7_v0/omocl_test_instance_7.json')
    And def resultJson = read(test_output_path+'test_omocl_7_v0/omocl_test_instance_7_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
