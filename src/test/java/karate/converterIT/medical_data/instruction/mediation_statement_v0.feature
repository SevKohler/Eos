Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'


  Scenario: Ace

    And def inputJson = read(instruction_composition_path+'medication_statement_v0/ace.json')
    And def resultJson = read(instruction_output_path+'medication_statement_v0/ace.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Ace with reason

    And def inputJson = read(instruction_composition_path+'medication_statement_v0/ace_with_reason.json')
    And def resultJson = read(instruction_output_path+'medication_statement_v0/ace_with_reason.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Anticoagulant

    And def inputJson = read(instruction_composition_path+'medication_statement_v0/anticoagulant.json')
    And def resultJson = read(instruction_output_path+'medication_statement_v0/anticoagulant.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Multiple entries

    And def inputJson = read(instruction_composition_path+'medication_statement_v0/multiple_entries.json')
    And def resultJson = read(instruction_output_path+'medication_statement_v0/multiple_entries.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson