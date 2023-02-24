Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Blood pressure

    And def inputJson = read(observation_composition_path+'blood_pressure_v2/blood_pressure.json')
    And def resultJson = read(observation_output_path+'blood_pressure_v2/blood_pressure.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

