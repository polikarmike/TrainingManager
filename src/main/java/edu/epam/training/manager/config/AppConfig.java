package edu.epam.training.manager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.epam.training.manager.dao.impl.TraineeDaoImpl;
import edu.epam.training.manager.dao.impl.TrainerDaoImpl;
import edu.epam.training.manager.dao.impl.TrainingDAOImpl;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.service.UserService;
import edu.epam.training.manager.service.impl.TraineeServiceImpl;
import edu.epam.training.manager.service.impl.TrainerServiceImpl;
import edu.epam.training.manager.service.impl.TrainingServiceImpl;
import edu.epam.training.manager.service.impl.UserServiceImpl;
import edu.epam.training.manager.storage.impl.TraineeStorageImpl;
import edu.epam.training.manager.storage.impl.TrainerStorageImpl;
import edu.epam.training.manager.storage.impl.TrainingStorageImpl;
import edu.epam.training.manager.utils.data.JsonDataLoader;
import edu.epam.training.manager.utils.generation.PasswordGenerator;
import edu.epam.training.manager.utils.generation.UsernameGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("edu.epam.training.manager")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public JsonDataLoader getJsonDataLoader() {
        JsonDataLoader jsonDataLoader = new JsonDataLoader();
        jsonDataLoader.setObjectMapper(getObjectMapper());
        return jsonDataLoader;
    }

    @Bean
    public TraineeStorageImpl getTraineeStorage(
            JsonDataLoader jsonDataLoader,
            @Value("${storage.trainees.path}") String path) {

        TraineeStorageImpl storage = new TraineeStorageImpl();
        storage.setJsonDataLoader(jsonDataLoader);
        storage.setInitFilePath(path);
        storage.setItemClass(Trainee.class);
        return storage;
    }

    @Bean
    public TrainerStorageImpl getTrainerStorage(
            JsonDataLoader jsonDataLoader,
            @Value("${storage.trainers.path}") String path) {

        TrainerStorageImpl storage = new TrainerStorageImpl();
        storage.setJsonDataLoader(jsonDataLoader);
        storage.setInitFilePath(path);
        storage.setItemClass(Trainer.class);
        return storage;
    }

    @Bean
    public TrainingStorageImpl getTrainingStorage(
            JsonDataLoader jsonDataLoader,
            @Value("${storage.trainings.path}") String path) {

        TrainingStorageImpl storage = new TrainingStorageImpl();
        storage.setJsonDataLoader(jsonDataLoader);
        storage.setInitFilePath(path);
        storage.setItemClass(Training.class);
        return storage;
    }

    @Bean
    public TraineeDaoImpl getTraineeDAO(TraineeStorageImpl traineeStorage) {
        TraineeDaoImpl dao = new TraineeDaoImpl();
        dao.setStorage(traineeStorage);
        return dao;
    }

    @Bean
    public TrainerDaoImpl getTrainerDAO(TrainerStorageImpl trainerStorage) {
        TrainerDaoImpl dao = new TrainerDaoImpl();
        dao.setStorage(trainerStorage);
        return dao;
    }

    @Bean
    public TrainingDAOImpl getTrainingDAO(TrainingStorageImpl trainingStorage) {
        TrainingDAOImpl dao = new TrainingDAOImpl();
        dao.setStorage(trainingStorage);
        return dao;
    }

    @Bean
    public UsernameGenerator getUsernameGenerator() {
        return new UsernameGenerator();
    }

    @Bean
    public PasswordGenerator getPasswordGenerator() {
        return new PasswordGenerator();
    }

    @Bean
    public UserServiceImpl getUserService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUsernameGenerator(getUsernameGenerator());
        return userService;
    }

    @Bean
    public TraineeServiceImpl getTraineeService(
            PasswordGenerator passwordGenerator,
            UserService userService) {

        TraineeServiceImpl service = new TraineeServiceImpl();
        service.setPasswordGenerator(passwordGenerator);
        service.setUserService(userService);
        return service;
    }

    @Bean
    public TrainerServiceImpl getTrainerService(
            PasswordGenerator passwordGenerator,
            UserService userService) {

        TrainerServiceImpl service = new TrainerServiceImpl();
        service.setUserService(userService);
        service.setPasswordGenerator(passwordGenerator);
        return service;
    }

    @Bean
    public TrainingServiceImpl getTrainingService(
            TrainerServiceImpl trainerServiceImpl) {

        TrainingServiceImpl service = new TrainingServiceImpl();
        service.setTrainerService(trainerServiceImpl);
        return service;
    }
}
