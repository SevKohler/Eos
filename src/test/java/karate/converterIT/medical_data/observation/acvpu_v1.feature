Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: ACVPU

    And def inputJson = read(observation_composition_path+'acvpu_v1/acvpu.json')
    And def resultJson = read(observation_output_path+'acvpu_v1/acvpu.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

