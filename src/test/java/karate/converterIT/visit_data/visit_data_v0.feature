Feature: Convert visits

  Background:
    Given url baseUrl
    Given path 'ehr/'+ehrId+'/composition'

  Scenario: Create Visits and transform them

    And def inputJson1 = read(visit_composition_path+'visit_data_1_v0.json')
    And def inputJson2 = read(visit_composition_path+'visit_data_2_v0.json')
    And def inputJson3 = read(visit_composition_path+'visit_data_3_v0.json')
    And def resultJson = read(visit_output_path+'visit_data_v0.json')
    
    # Store 3 visits
    And request inputJson1
    When method POST
    Then status 200

    Given path 'ehr/'+ehrId+'/composition'
    And request inputJson2
    When method POST
    Then status 200

    Given path 'ehr/'+ehrId+'/composition'
    And request inputJson3
    When method POST
    Then status 200
    
    # Now call to visit endpoint with query param aql to generate the visits
    Given url baseUrl
    And path 'visit'
    # Put the full AQL in param
    And param aql = "SELECT e/ehr_id/value as ehr_id, c/context/other_context/items[openEHR-EHR-CLUSTER.case_identification.v0]/items[at0001]/value/value as source_id, c/content[openEHR-EHR-ADMIN_ENTRY.hospitalization.v0]/data[at0001]/items[at0004]/value/value as begin, c/content[openEHR-EHR-ADMIN_ENTRY.hospitalization.v0]/data[at0001]/items[at0005]/value/value as end FROM EHR e CONTAINS COMPOSITION c WHERE c/archetype_details/template_id/value='Patientenaufenthalt'"
    When method POST
    Then status 200
    * match response contains resultJson
