package config;



import com.fasterxml.jackson.databind.ObjectMapper;

import edu.epam.trainingmanager.config.AppConfig;
import edu.epam.trainingmanager.dao.impl.TrainerDAOImpl;

import edu.epam.trainingmanager.storage.impl.TraineeStorage;

import edu.epam.trainingmanager.facade.GymFacade;
import edu.epam.trainingmanager.utils.data.JsonDataLoader;

import edu.epam.trainingmanager.utils.generation.PasswordGenerator;
import edu.epam.trainingmanager.utils.generation.UsernameGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@TestPropertySource("classpath:application.properties")
class AppConfigTest {

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();
    }

    @AfterEach
    void tearDown() {
        context.close();
    }

    @Test
    void testObjectMapperBean() {
        ObjectMapper mapper = context.getBean(ObjectMapper.class);
        assertNotNull(mapper);
    }

    @Test
    void testJsonDataLoaderBean() {
        JsonDataLoader loader = context.getBean(JsonDataLoader.class);
        assertNotNull(loader);
    }

    @Test
    void testTraineeStorageBean() {
        TraineeStorage storage = context.getBean(TraineeStorage.class);
        assertNotNull(storage);
    }

    @Test
    void testTrainerDAOBean() {
        TrainerDAOImpl dao = context.getBean(TrainerDAOImpl.class);
        assertNotNull(dao);
        assertNotNull(dao.findAll());
    }

    @Test
    void testUsernameGeneratorBean() {
        UsernameGenerator generator = context.getBean(UsernameGenerator.class);
        assertNotNull(generator);
        String username = generator.generate("John", "Doe", name -> false);
        assertEquals("John.Doe", username);
    }

    @Test
    void testPasswordGeneratorBean() {
        PasswordGenerator generator = context.getBean(PasswordGenerator.class);
        assertNotNull(generator);
        String password = generator.generate(8);
        assertEquals(8, password.length());
    }

    @Test
    void testGymFacadeBean() {
        GymFacade facade = context.getBean(GymFacade.class);
        assertNotNull(facade);
    }
}


