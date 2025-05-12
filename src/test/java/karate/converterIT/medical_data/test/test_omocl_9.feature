Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-OBSERVATION.procedure_screening.v1
  # openEHR-EHR-OBSERVATION.progress_note.v1
  # openEHR-EHR-OBSERVATION.pasi_score.v1
  # openEHR-EHR-OBSERVATION.pulse_oximetry.v1
  # openEHR-EHR-OBSERVATION.qsofa_score.v1
  # openEHR-EHR-EVALUATION.reason_for_encounter.v1
  # openEHR-EHR-OBSERVATION.revised_cardiac_risk_index.v1
  # openEHR-EHR-ACTION.service.v1
  # openEHR-EHR-OBSERVATION.simplified_tanner_whitehouse_3.v1
  # openEHR-EHR-EVALUATION.smokeless_tobacco_summary.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_9_v0/omocl_test_instance_9.json')
    And def resultJson = read(test_output_path+'test_omocl_9_v0/omocl_test_instance_9_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
