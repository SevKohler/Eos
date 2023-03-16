function fn() {

    var config = {
        baseUrl: 'http://localhost:8081',
        ehrId: '0fcbfd31-d5d9-46f9-a325-51dcde5f6d56',
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