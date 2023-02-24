Feature: Convert device summary

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Device summary

    And def inputJson = read(evaluation_composition_path+'device_summary_v0/medical_device.json')
    And def resultJson = read(evaluation_output_path+'device_summary_v0/medical_device.json')
    And request inputJson
    When method POST
    Then status 200
    * match response contains resultJson
