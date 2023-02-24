Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Blood pressure

    And def inputJson = read(observation_composition_path+'body_mass_index_v2/body_mass_index_96.json')
    And def resultJson = read(observation_output_path+'body_mass_index_v2/body_mass_index_96.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

