Feature: Convert all pregnancy statuses

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Pregnant

    And def inputJson = read(observation_composition_path+'pregnancy_status_v0/pregnant.json')
    And def resultJson = read(observation_output_path+'pregnancy_status_v0/pregnant.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Not pregnant

    And def inputJson = read(observation_composition_path+'pregnancy_status_v0/not-pregnant.json')
    And def resultJson = read(observation_output_path+'pregnancy_status_v0/not-pregnant.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

