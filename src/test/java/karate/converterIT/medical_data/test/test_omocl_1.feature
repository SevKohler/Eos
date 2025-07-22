Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-OBSERVATION.four_a_test.v1
  # openEHR-EHR-OBSERVATION.acvpu.v1
  # openEHR-EHR-EVALUATION.advance_intervention_decisions.v1
  # openEHR-EHR-OBSERVATION.adverse_reaction_monitoring.v1
  # openEHR-EHR-OBSERVATION.age_assertion.v1
  # openEHR-EHR-EVALUATION.alcohol_consumption_summary.v1
  # openEHR-EHR-OBSERVATION.asa_status.v1
  # openEHR-EHR-EVALUATION.art_cycle_summary.v1
  # openEHR-EHR-OBSERVATION.body_segment_area.v1
  # openEHR-EHR-OBSERVATION.body_segment_circumference.v1
  # openEHR-EHR-OBSERVATION.body_segment_discrepancy.v1
  # openEHR-EHR-OBSERVATION.body_segment_length.v1
  # openEHR-EHR-OBSERVATION.body_surface_area.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_1_v0/omocl_test_instance_1.json')
    And def resultJson = read(test_output_path+'test_omocl_1_v0/omocl_test_instance_1_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
