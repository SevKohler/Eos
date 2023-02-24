Feature: Test visit detail optional true


  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Test visit detail optional true

    And def inputJson = read(test_composition_path+'test_visit_detail_true_v0/visit_true.json')
    And def resultJson = read(test_output_path+'test_visit_detail_true_v0/visit_true.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
