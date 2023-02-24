Feature: Convert death summary

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Death summary

    And def inputJson = read(evaluation_composition_path+'death_summary_v0/death.json')
    And def resultJson = read(evaluation_output_path+'death_summary_v0/death.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
