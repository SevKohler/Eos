Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-INSTRUCTION.therapeutic_item_order.v1
  # openEHR-EHR-OBSERVATION.timed_25_foot_walk.v1
  # openEHR-EHR-EVALUATION.tobacco_smoking_summary.v1
  # openEHR-EHR-ADMIN_ENTRY.translation_requirements.v1
  # openEHR-EHR-OBSERVATION.urinalysis.v1
  # openEHR-EHR-OBSERVATION.waist_circumference.v1
  # openEHR-EHR-OBSERVATION.ygtss_revised.v1
  # openEHR-EHR-EVALUATION.advance_care_directive.v2
  # openEHR-EHR-EVALUATION.adverse_reaction_risk.v2
  # openEHR-EHR-OBSERVATION.apgar.v2
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_11_v0/omocl_test_instance_11.json')
    And def resultJson = read(test_output_path+'test_omocl_11_v0/omocl_test_instance_11_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
