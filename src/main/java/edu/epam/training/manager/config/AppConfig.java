package edu.epam.training.manager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.epam.training.manager.dao.impl.TraineeDAOImpl;
import edu.epam.training.manager.dao.impl.TrainerDAOImpl;
import edu.epam.training.manager.dao.impl.TrainingDAOImpl;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.facade.GymFacade;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("edu.epam.training.manager")
@PropertySource("classpath:application.properties")
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public ObjectMapper objectMapper() {
        logger.debug("Initializing ObjectMapper...");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public JsonDataLoader jsonDataLoader() {
        logger.debug("Creating JsonDataLoader bean...");
        JsonDataLoader jsonDataLoader = new JsonDataLoader();
        jsonDataLoader.setObjectMapper(objectMapper());
        return jsonDataLoader;
    }

    @Bean
    public TraineeStorageImpl traineeStorage(
            JsonDataLoader jsonDataLoader,
            @Value("${storage.trainees.path}") String path) {

        logger.debug("Initializing TraineeStorage with path: {}", path);
        TraineeStorageImpl storage = new TraineeStorageImpl();
        storage.setJsonDataLoader(jsonDataLoader);
        storage.setInitFilePath(path);
        storage.setItemClass(Trainee.class);
        return storage;
    }

    @Bean
    public TrainerStorageImpl trainerStorage(
            JsonDataLoader jsonDataLoader,
            @Value("${storage.trainers.path}") String path) {

        logger.debug("Initializing TrainerStorage with path: {}", path);
        TrainerStorageImpl storage = new TrainerStorageImpl();
        storage.setJsonDataLoader(jsonDataLoader);
        storage.setInitFilePath(path);
        storage.setItemClass(Trainer.class);
        return storage;
    }

    @Bean
    public TrainingStorageImpl trainingStorage(
            JsonDataLoader jsonDataLoader,
            @Value("${storage.trainings.path}") String path) {

        logger.debug("Initializing TrainingStorage with path: {}", path);
        TrainingStorageImpl storage = new TrainingStorageImpl();
        storage.setJsonDataLoader(jsonDataLoader);
        storage.setInitFilePath(path);
        storage.setItemClass(Training.class);
        return storage;
    }

    @Bean
    public TraineeDAOImpl traineeDAO(TraineeStorageImpl traineeStorage) {
        logger.debug("Creating TraineeDAOImpl bean...");
        TraineeDAOImpl dao = new TraineeDAOImpl();
        dao.setStorage(traineeStorage);
        return dao;
    }

    @Bean
    public TrainerDAOImpl trainerDAO(TrainerStorageImpl trainerStorage) {
        logger.debug("Creating TrainerDAOImpl bean...");
        TrainerDAOImpl dao = new TrainerDAOImpl();
        dao.setStorage(trainerStorage);
        return dao;
    }

    @Bean
    public TrainingDAOImpl trainingDAO(TrainingStorageImpl trainingStorage) {
        logger.debug("Creating TrainingDAOImpl bean...");
        TrainingDAOImpl dao = new TrainingDAOImpl();
        dao.setStorage(trainingStorage);
        return dao;
    }

    @Bean
    public UsernameGenerator usernameGenerator() {
        logger.debug("Initializing UsernameGenerator...");
        return new UsernameGenerator();
    }

    @Bean
    public PasswordGenerator passwordGenerator() {
        logger.debug("Initializing PasswordGenerator...");
        return new PasswordGenerator();
    }

    @Bean
    public UserServiceImpl userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUsernameGenerator(usernameGenerator());
        return userService;
    }

    @Bean
    public TraineeServiceImpl traineeService(
            PasswordGenerator passwordGenerator,
            UserService userService) {

        logger.debug("Creating TraineeServiceImpl bean...");
        TraineeServiceImpl service = new TraineeServiceImpl();
        service.setPasswordGenerator(passwordGenerator);
        service.setUserService(userService);
        return service;
    }

    @Bean
    public TrainerServiceImpl trainerService(
            PasswordGenerator passwordGenerator,
            UserService userService) {

        logger.debug("Creating TrainerServiceImpl bean...");
        TrainerServiceImpl service = new TrainerServiceImpl();
        service.setUserService(userService);
        service.setPasswordGenerator(passwordGenerator);
        return service;
    }

    @Bean
    public TrainingServiceImpl trainingService(
            TrainerServiceImpl trainerServiceImpl) {

        logger.debug("Creating TrainingServiceImpl bean...");
        TrainingServiceImpl service = new TrainingServiceImpl();
        service.setTrainerServiceImpl(trainerServiceImpl);
        return service;
    }

    @Bean
    public GymFacade gymFacade(
            TraineeServiceImpl traineeServiceImpl,
            TrainerServiceImpl trainerServiceImpl,
            TrainingServiceImpl trainingServiceImpl) {

        logger.debug("Creating GymFacade bean...");
        return new GymFacade(
                traineeServiceImpl,
                trainerServiceImpl,
                trainingServiceImpl
        );
    }
}
