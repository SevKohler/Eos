Feature: Test provider optional false

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider optional false

    And def inputJson = read(test_composition_path+'test_provider_v0/provider.json')
    And def resultJson = read(test_output_path+'test_provider_v0/provider.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
