Feature: Convert full template
  # This karate test covers the following archetypes
  # openEHR-EHR-OBSERVATION.esas_r.v1
  # openEHR-EHR-EVALUATION.education_summary.v1
  # openEHR-EHR-EVALUATION.ethnicity.v1
  # openEHR-EHR-OBSERVATION.exposure_screening.v1
  # openEHR-EHR-EVALUATION.financial_summary.v1
  # openEHR-EHR-OBSERVATION.fluid_balance.v1
  # openEHR-EHR-OBSERVATION.fluid_input.v1
  # openEHR-EHR-OBSERVATION.fluid_output.v1
  # openEHR-EHR-OBSERVATION.fetal_biometry.v1
  # openEHR-EHR-EVALUATION.gender.v1
  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_omocl_4_v0/omocl_test_instance_4.json')
    And def resultJson = read(test_output_path+'test_omocl_4_v0/omocl_test_instance_4_out.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
