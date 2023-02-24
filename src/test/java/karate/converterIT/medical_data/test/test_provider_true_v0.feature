Feature: Convert test provider optional true

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test provider for optional true

    And def inputJson = read(test_composition_path+'test_provider_true_v0/provider_true.json')
    And def resultJson = read(test_output_path+'test_provider_true_v0/provider_true.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
