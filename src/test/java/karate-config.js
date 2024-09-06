function fn() {

    var config = {
        baseUrl: 'http://localhost:8081',
        ehrId: 'bddb4332-090e-40ea-88d1-6e69f69d17eb',
        action_composition_path: 'classpath:json/compositions/medical_data/action/',
        evaluation_composition_path: 'classpath:json/compositions/medical_data/evaluation/',
        instruction_composition_path: 'classpath:json/compositions/medical_data/instruction/',
        observation_composition_path: 'classpath:json/compositions/medical_data/observation/',
        test_composition_path: 'classpath:json/compositions/medical_data/tests/',
        person_composition_path: 'classpath:json/compositions/person_data/',
        action_output_path: 'classpath:json/output/medical_data/action/',
        evaluation_output_path: 'classpath:json/output/medical_data/evaluation/',
        observation_output_path: 'classpath:json/output/medical_data/observation/',
        instruction_output_path: 'classpath:json/output/medical_data/instruction/',
        test_output_path: 'classpath:json/output/medical_data/tests/',
        person_output_path: 'classpath:json/output/person_data/'
    };

    return config;
}