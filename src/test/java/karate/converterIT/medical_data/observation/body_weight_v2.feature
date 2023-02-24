Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Weight in kg

    And def inputJson = read(observation_composition_path+'body_weight_v2/kilogramm.json')
    And def resultJson = read(observation_output_path+'body_weight_v2/kilogramm.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Weight in pound

    And def inputJson = read(observation_composition_path+'body_weight_v2/pound.json')
    And def resultJson = read(observation_output_path+'body_weight_v2/pound.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

