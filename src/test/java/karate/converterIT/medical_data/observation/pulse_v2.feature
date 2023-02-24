Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Pulse

    And def inputJson = read(observation_composition_path+'pulse_v2/pulse.json')
    And def resultJson = read(observation_output_path+'pulse_v2/pulse.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
