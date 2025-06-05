package edu.epam.training.manager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.epam.training.manager.dao.impl.TraineeDaoImpl;
import edu.epam.training.manager.dao.impl.TrainerDaoImpl;
import edu.epam.training.manager.dao.impl.TrainingDAOImpl;
import edu.epam.training.manager.facade.GymFacade;
import edu.epam.training.manager.service.impl.TraineeServiceImpl;
import edu.epam.training.manager.service.impl.TrainerServiceImpl;
import edu.epam.training.manager.service.impl.TrainingServiceImpl;
import edu.epam.training.manager.service.impl.UserServiceImpl;
import edu.epam.training.manager.utils.data.JsonDataLoader;
import edu.epam.training.manager.utils.generation.PasswordGenerator;
import edu.epam.training.manager.utils.generation.UsernameGenerator;
import org.junit.jupiter.api.*;
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
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @AfterEach
    void tearDown() {
        context.close();
    }

    @Test
    void testObjectMapperBean() {
        assertNotNull(context.getBean(ObjectMapper.class));
    }

    @Test
    void testJsonDataLoaderBean() {
        assertNotNull(context.getBean(JsonDataLoader.class));
    }

    @Test
    void testTraineeDAOImplBean() {
        assertNotNull(context.getBean(TraineeDaoImpl.class));
    }

    @Test
    void testTrainerDAOImplBean() {
        assertNotNull(context.getBean(TrainerDaoImpl.class));
    }

    @Test
    void testTrainingDAOImplBean() {
        assertNotNull(context.getBean(TrainingDAOImpl.class));
    }

    @Test
    void testUsernameGeneratorBean() {
        UsernameGenerator generator = context.getBean(UsernameGenerator.class);
        assertNotNull(generator);
        assertNotNull(generator.generateCandidates("Lila", "Smith"));
    }

    @Test
    void testPasswordGeneratorBean() {
        PasswordGenerator generator = context.getBean(PasswordGenerator.class);
        assertNotNull(generator);
    }

    @Test
    void testUserServiceBean() {
        assertNotNull(context.getBean(UserServiceImpl.class));
    }

    @Test
    void testTraineeServiceBean() {
        assertNotNull(context.getBean(TraineeServiceImpl.class));
    }

    @Test
    void testTrainerServiceBean() {
        assertNotNull(context.getBean(TrainerServiceImpl.class));
    }

    @Test
    void testTrainingServiceBean() {
        assertNotNull(context.getBean(TrainingServiceImpl.class));
    }

    @Test
    void testGymFacadeBean() {
        assertNotNull(context.getBean(GymFacade.class));
    }
}
