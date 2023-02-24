Feature: Convert all problem diagnose compositions

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Cardiovascular disease

    And def inputJson = read(evaluation_composition_path+'problem_diagnosis_v1/cardiovascular_disease.json')
    And def resultJson = read(evaluation_output_path+'problem_diagnosis_v1/cardiovascular_disease.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson

  Scenario: Chronic kidney disease

    And def inputJson = read(evaluation_composition_path+'problem_diagnosis_v1/chronic_kidney_disease.json')
    And def resultJson = read(evaluation_output_path+'problem_diagnosis_v1/chronic_kidney_disease.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: Chronic liver disease

    And def inputJson = read(evaluation_composition_path+'problem_diagnosis_v1/chronic_liver_disease.json')
    And def resultJson = read(evaluation_output_path+'problem_diagnosis_v1/chronic_liver_disease.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: Chronic lung disease

    And def inputJson = read(evaluation_composition_path+'problem_diagnosis_v1/chronic_lung_disease.json')
    And def resultJson = read(evaluation_output_path+'problem_diagnosis_v1/chronic_lung_disease.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: Chronic neurological mental disease
    And def inputJson = read(evaluation_composition_path+'problem_diagnosis_v1/chronic_neurological_mental_disease.json')
    And def resultJson = read(evaluation_output_path+'problem_diagnosis_v1/chronic_neurological_mental_disease.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: Complications with covid
    And def inputJson = read(evaluation_composition_path+'problem_diagnosis_v1/complications_covid.json')
    And def resultJson = read(evaluation_output_path+'problem_diagnosis_v1/complications_covid.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: Diabetes mellitus
    And def inputJson = read(evaluation_composition_path+'problem_diagnosis_v1/diabetes_mellitus.json')
    And def resultJson = read(evaluation_output_path+'problem_diagnosis_v1/diabetes_mellitus.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson


  Scenario: Organ recipient
    And def inputJson = read(evaluation_composition_path+'problem_diagnosis_v1/organ_recipient.json')
    And def resultJson = read(evaluation_output_path+'problem_diagnosis_v1/organ_recipient.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson