Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Body temperature

    And def inputJson = read(observation_composition_path+'body_temperature_v2/body_temp.json')
    And def resultJson = read(observation_output_path+'body_temperature_v2/body_temp.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Body temperature with null flavour

    And def inputJson = read(observation_composition_path+'body_temperature_v2/with_nullflavour.json')
    And def resultJson = read(observation_output_path+'body_temperature_v2/with_nullflavour.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

