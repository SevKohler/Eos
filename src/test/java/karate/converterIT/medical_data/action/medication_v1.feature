Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Vaccine covid

    And def inputJson = read(action_composition_path+'/medication_v1/vaccine_covid.json')
    And def resultJson = read(action_output_path+'medication_v1/vaccine_covid.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Vaccine 3 times against covid

    And def inputJson = read(action_composition_path+'/medication_v1/vaccine_covid_3_times.json')
    And def resultJson = read(action_output_path+'medication_v1/vaccine_covid_3_times.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Vaccine hepathitis A

    And def inputJson = read(action_composition_path+'/medication_v1/vaccine_hep_a.json')
    And def resultJson = read(action_output_path+'medication_v1/vaccine_hep_a.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Vaccine covid

    And def inputJson = read(action_composition_path+'/medication_v1/vaccine_streptococcus.json')
    And def resultJson = read(action_output_path+'medication_v1/vaccine_streptococcus.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

