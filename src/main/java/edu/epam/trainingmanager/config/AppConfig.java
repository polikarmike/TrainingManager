package edu.epam.trainingmanager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.epam.trainingmanager.dao.impl.TraineeDAOImpl;
import edu.epam.trainingmanager.dao.impl.TrainerDAOImpl;
import edu.epam.trainingmanager.dao.impl.TrainingDAOImpl;
import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.domain.Trainer;
import edu.epam.trainingmanager.domain.Training;
import edu.epam.trainingmanager.facade.GymFacade;
import edu.epam.trainingmanager.service.impl.TrainerServiceImpl;
import edu.epam.trainingmanager.service.impl.TraineeServiceImpl;
import edu.epam.trainingmanager.service.impl.TrainingServiceImpl;
import edu.epam.trainingmanager.storage.BaseStorage;
import edu.epam.trainingmanager.storage.UserStorage;
import edu.epam.trainingmanager.storage.impl.TraineeStorage;
import edu.epam.trainingmanager.storage.impl.TrainerStorage;
import edu.epam.trainingmanager.storage.impl.TrainingStorage;
import edu.epam.trainingmanager.utils.data.JsonDataLoader;
import edu.epam.trainingmanager.utils.generation.IdGenerator;
import edu.epam.trainingmanager.utils.generation.PasswordGenerator;
import edu.epam.trainingmanager.utils.generation.UsernameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("edu.epam.trainingmanager")
@PropertySource("classpath:application.properties")
public class AppConfig {
    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    // Object Mapper & JSON Handling
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

    // Storage Beans (Data Persistence)
    @Bean
    public TraineeStorage traineeStorage(
            JsonDataLoader jsonDataLoader,
            @Value("${storage.trainees.path}") String path) {

        logger.debug("Initializing TraineeStorage with path: {}", path);
        TraineeStorage storage = new TraineeStorage();
        storage.setJsonDataLoader(jsonDataLoader);
        storage.setInitFilePath(path);
        storage.setItemClass(Trainee.class);
        return storage;
    }

    @Bean
    public TrainerStorage trainerStorage(
            JsonDataLoader jsonDataLoader,
            @Value("${storage.trainers.path}") String path) {

        logger.debug("Initializing TrainerStorage with path: {}", path);
        TrainerStorage storage = new TrainerStorage();
        storage.setJsonDataLoader(jsonDataLoader);
        storage.setInitFilePath(path);
        storage.setItemClass(Trainer.class);
        return storage;
    }

    @Bean
    public TrainingStorage trainingStorage(
            JsonDataLoader jsonDataLoader,
            @Value("${storage.trainings.path}") String path) {

        logger.debug("Initializing TrainingStorage with path: {}", path);
        TrainingStorage storage = new TrainingStorage();
        storage.setJsonDataLoader(jsonDataLoader);
        storage.setInitFilePath(path);
        storage.setItemClass(Training.class);
        return storage;
    }

    // DAO Beans (Data Access Layer)
    @Bean
    public TraineeDAOImpl traineeDAO(UserStorage<Trainee> traineeStorage) {
        logger.debug("Creating TraineeDAOImpl bean...");
        TraineeDAOImpl dao = new TraineeDAOImpl();
        dao.setStorage(traineeStorage);
        return dao;
    }

    @Bean
    public TrainerDAOImpl trainerDAO(UserStorage<Trainer> trainerStorage) {
        logger.debug("Creating TrainerDAOImpl bean...");
        TrainerDAOImpl dao = new TrainerDAOImpl();
        dao.setStorage(trainerStorage);
        return dao;
    }

    @Bean
    public TrainingDAOImpl trainingDAO(BaseStorage<Training> trainingStorage) {
        logger.debug("Creating TrainingDAOImpl bean...");
        TrainingDAOImpl dao = new TrainingDAOImpl();
        dao.setStorage(trainingStorage);
        return dao;
    }

    // Utility Beans (Generators)
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
    public IdGenerator idGenerator() {
        logger.debug("Initializing IdGenerator...");
        return new IdGenerator();
    }

    // Service Beans (Business Logic)
    @Bean
    public TraineeServiceImpl traineeService(
            UsernameGenerator usernameGenerator,
            PasswordGenerator passwordGenerator,
            IdGenerator idGenerator) {

        logger.debug("Creating TraineeServiceImpl bean...");
        TraineeServiceImpl service = new TraineeServiceImpl();
        service.setUsernameGenerator(usernameGenerator);
        service.setPasswordGenerator(passwordGenerator);
        service.setIdGenerator(idGenerator);
        return service;
    }

    @Bean
    public TrainerServiceImpl trainerService(
            UsernameGenerator usernameGenerator,
            PasswordGenerator passwordGenerator,
            IdGenerator idGenerator) {

        logger.debug("Creating TrainerServiceImpl bean...");
        TrainerServiceImpl service = new TrainerServiceImpl();
        service.setUsernameGenerator(usernameGenerator);
        service.setPasswordGenerator(passwordGenerator);
        service.setIdGenerator(idGenerator);
        return service;
    }

    @Bean
    public TrainingServiceImpl trainingService(
            TrainerServiceImpl trainerServiceImpl,
            TraineeServiceImpl traineeServiceImpl,
            IdGenerator idGenerator) {

        logger.debug("Creating TrainingServiceImpl bean...");
        TrainingServiceImpl service = new TrainingServiceImpl();
        service.setTraineeServiceImpl(traineeServiceImpl);
        service.setTrainerServiceImpl(trainerServiceImpl);
        service.setIdGenerator(idGenerator);
        return service;
    }

    // Facade (Main Entry Point)
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
