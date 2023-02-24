Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: SpCO

    And def inputJson = read(observation_composition_path+'pulse_oximetry_v1/spco.json')
    And def resultJson = read(observation_output_path+'pulse_oximetry_v1/spco.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: SpMet

    And def inputJson = read(observation_composition_path+'pulse_oximetry_v1/spmet.json')
    And def resultJson = read(observation_output_path+'pulse_oximetry_v1/spmet.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: SpO

    And def inputJson = read(observation_composition_path+'pulse_oximetry_v1/spo.json')
    And def resultJson = read(observation_output_path+'pulse_oximetry_v1/spo.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: SpOC

    And def inputJson = read(observation_composition_path+'pulse_oximetry_v1/spoc.json')
    And def resultJson = read(observation_output_path+'pulse_oximetry_v1/spoc.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: Unsupported proportion type

    And def inputJson = read(observation_composition_path+'pulse_oximetry_v1/unsupported_proportion_type.json')
    And def resultJson = read(observation_output_path+'pulse_oximetry_v1/unsupported_proportion_type.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


