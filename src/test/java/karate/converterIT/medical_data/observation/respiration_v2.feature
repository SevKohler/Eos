Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Respiration

    And def inputJson = read(observation_composition_path+'respiration_v2/respiration.json')
    And def resultJson = read(observation_output_path+'respiration_v2/respiration.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
