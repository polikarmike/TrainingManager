package edu.epam.training.manager;

import edu.epam.training.manager.config.AppConfig;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.TrainingType;
import edu.epam.training.manager.facade.GymFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

public class TrainingManagerApplication {
    private static final Logger logger = LoggerFactory.getLogger(TrainingManagerApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Application");

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            GymFacade gymFacade = context.getBean(GymFacade.class);

            UUID traineeId = UUID.fromString("a1d5f78e-32c7-4f9e-93e8-7c2a6f3bdf2d");
            Trainee fetchedTrainee = gymFacade.getTrainee(traineeId);
            logger.info("Fetched Trainee: {}", fetchedTrainee);

                Trainer newTrainer = Trainer.builder()
                    .firstName("Chloe")
                    .lastName("White")
                    .specialization(TrainingType.YOGA)
                    .build();
            Trainer registeredTrainer = gymFacade.registerTrainer(newTrainer);
            logger.info("Registered Trainer: {}", registeredTrainer);

            UUID trainingId = UUID.fromString("3c7e59a1-8d2b-4859-9c3f-72a6f3bdf2d1");
            Training fetchedTraining = gymFacade.getTraining(trainingId);
            logger.info("Fetched Training: {}", fetchedTraining);
        }

        logger.info("Closing Application");
    }
}








