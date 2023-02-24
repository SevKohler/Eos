Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Multiple entries

    And def inputJson = read(instruction_composition_path+'medication_order_v2/multiple_entries.json')
    And def resultJson = read(instruction_output_path+'medication_order_v2/multiple_entries.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: With route

    And def inputJson = read(instruction_composition_path+'medication_order_v2/with_route.json')
    And def resultJson = read(instruction_output_path+'medication_order_v2/with_route.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: With trade name

    And def inputJson = read(instruction_composition_path+'medication_order_v2/with_trade_name.json')
    And def resultJson = read(instruction_output_path+'medication_order_v2/with_trade_name.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: With no coded name

    And def inputJson = read(instruction_composition_path+'medication_order_v2/no_coded_name.json')
    And def resultJson = read(instruction_output_path+'medication_order_v2/no_coded_name.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson




