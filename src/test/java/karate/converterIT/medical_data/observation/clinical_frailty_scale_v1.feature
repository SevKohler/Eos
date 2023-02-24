Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Clinical Frailty with seven

    And def inputJson = read(observation_composition_path+'clinical_frailty_scale_v1/cfs_seven.json')
    And def resultJson = read(observation_output_path+'clinical_frailty_scale_v1/cfs_seven.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Clinical Frailty with eight

    And def inputJson = read(observation_composition_path+'clinical_frailty_scale_v1/cfs_eight.json')
    And def resultJson = read(observation_output_path+'clinical_frailty_scale_v1/cfs_eight.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

