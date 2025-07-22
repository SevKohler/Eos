Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-OBSERVATION.social_context_screening.v1
  # openEHR-EHR-EVALUATION.social_network.v1
  # openEHR-EHR-EVALUATION.social_summary.v1
  # openEHR-EHR-EVALUATION.specimen_summary.v1
  # openEHR-EHR-OBSERVATION.story.v1
  # openEHR-EHR-OBSERVATION.substance_use_screening.v1
  # openEHR-EHR-EVALUATION.substance_use_summary.v1
  # openEHR-EHR-OBSERVATION.symptom_sign_screening.v1
  # openEHR-EHR-OBSERVATION.tanner.v1
  # openEHR-EHR-OBSERVATION.testicular_volume.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_10_v0/omocl_test_instance_10.json')
    And def resultJson = read(test_output_path+'test_omocl_10_v0/omocl_test_instance_10_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
