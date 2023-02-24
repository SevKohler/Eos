Feature: Convert all lab test results

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'


  Scenario: 4 entries

    And def inputJson = read(observation_composition_path+'laboratory_test_result_v1/blood_gas_4_entries.json')
    And def resultJson = read(observation_output_path+'laboratory_test_result_v1/blood_gas_4_entries.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: 4 entries with interval event

    And def inputJson = read(observation_composition_path+'laboratory_test_result_v1/blood_gas_4_entries_interval_event.json')
    And def resultJson = read(observation_output_path+'laboratory_test_result_v1/blood_gas_4_entries_interval_event.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: 4 entries with interval event and duration

    And def inputJson = read(observation_composition_path+'laboratory_test_result_v1/blood_gas_4_entries_interval_event_duration.json')
    And def resultJson = read(observation_output_path+'laboratory_test_result_v1/blood_gas_4_entries_interval_event_duration.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: 8 entries

    And def inputJson = read(observation_composition_path+'laboratory_test_result_v1/blood_gas_8_entries.json')
    And def resultJson = read(observation_output_path+'laboratory_test_result_v1/blood_gas_8_entries.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: with fact relationship

    And def inputJson = read(observation_composition_path+'laboratory_test_result_v1/blood_gas_with_fact_relationship.json')
    And def resultJson = read(observation_output_path+'laboratory_test_result_v1/blood_gas_with_fact_relationship.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Serological finding

    And def inputJson = read(observation_composition_path+'laboratory_test_result_v1/serological_finding.json')
    And def resultJson = read(observation_output_path+'laboratory_test_result_v1/serological_finding.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Serological finding with DV CODED TEXT operator

    And def inputJson = read(observation_composition_path+'laboratory_test_result_v1/serological_finding_operator.json')
    And def resultJson = read(observation_output_path+'laboratory_test_result_v1/serological_finding_operator.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: Serological finding with magnitude status

    And def inputJson = read(observation_composition_path+'laboratory_test_result_v1/serological_finding_magnitude_status.json')
    And def resultJson = read(observation_output_path+'laboratory_test_result_v1/serological_finding_magnitude_status.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: Virological finding

    And def inputJson = read(observation_composition_path+'laboratory_test_result_v1/virological_finding.json')
    And def resultJson = read(observation_output_path+'laboratory_test_result_v1/virological_finding.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Virological finding with quantity

    And def inputJson = read(observation_composition_path+'laboratory_test_result_v1/virological_finding_with_quantity.json')
    And def resultJson = read(observation_output_path+'laboratory_test_result_v1/virological_finding_with_quantity.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

