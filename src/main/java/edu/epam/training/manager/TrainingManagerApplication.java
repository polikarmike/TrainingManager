package edu.epam.training.manager;

import edu.epam.training.manager.config.AppConfig;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.dto.Credentials;
import edu.epam.training.manager.facade.GymFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class TrainingManagerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingManagerApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Application");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        GymFacade gymFacade = context.getBean(GymFacade.class);

        Credentials authCredentials = new Credentials("olivia.davis", "hashed_password_9");

        List<Training> trainings = gymFacade.getTraineeTrainings(authCredentials,
                "olivia.davis",
                LocalDate.of(2025, 6, 2),
                LocalDate.of(2025, 6, 10),
                null,
                null);

        LOGGER.info("Training List: {}", trainings);

        LOGGER.info("Closing Application");
    }
}
