Feature: Convert person

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Create a Person

    And def inputJson = read(person_composition_path+'person_data_v0.json')
    And def resultJson = read(person_output_path+'person_data_v0.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
