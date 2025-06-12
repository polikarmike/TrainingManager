package edu.epam.training.manager;

import edu.epam.training.manager.config.AppConfig;
import edu.epam.training.manager.facade.GymFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TrainingManagerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingManagerApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Application");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        GymFacade gymFacade = context.getBean(GymFacade.class);

        gymFacade.toggleTraineeStatus("john.doe", "hashed_password_1", "john.doe");

        LOGGER.info("Closing Application");
    }
}
