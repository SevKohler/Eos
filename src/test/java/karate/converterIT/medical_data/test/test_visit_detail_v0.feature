Feature: Test visit detail optional false

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test visit detail optional false

    And def inputJson = read(test_composition_path+'test_visit_detail_v0/visit.json')
    And def resultJson = read(test_output_path+'test_visit_detail_v0/visit.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
