Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: current

    And def inputJson = read(evaluation_composition_path+'tobacco_smoking_summary_v1/current.json')
    And def resultJson = read(evaluation_output_path+'/tobacco_smoking_summary_v1/current.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: never

    And def inputJson = read(evaluation_composition_path+'tobacco_smoking_summary_v1/never.json')
    And def resultJson = read(evaluation_output_path+'/tobacco_smoking_summary_v1/never.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: absent

    And def inputJson = read(evaluation_composition_path+'tobacco_smoking_summary_v1/absent.json')
    And def resultJson = read(evaluation_output_path+'/tobacco_smoking_summary_v1/absent.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

