Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Apheresis

    And def inputJson = read(action_composition_path+'procedure_v1/apheresis.json')
    And def resultJson = read(action_output_path+'procedure_v1/apheresis.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

