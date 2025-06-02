package edu.epam.trainingmanager;

import edu.epam.trainingmanager.config.AppConfig;
import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.domain.Trainer;
import edu.epam.trainingmanager.domain.TrainingType;


import edu.epam.trainingmanager.dto.trainee.TraineeCreateDto;
import edu.epam.trainingmanager.dto.trainee.TraineeDto;
import edu.epam.trainingmanager.dto.trainee.TraineeUpdateDto;
import edu.epam.trainingmanager.dto.trainer.TrainerCreateDto;
import edu.epam.trainingmanager.dto.trainer.TrainerDto;
import edu.epam.trainingmanager.dto.trainer.TrainerUpdateDto;
import edu.epam.trainingmanager.dto.training.TrainingCreateDto;
import edu.epam.trainingmanager.dto.training.TrainingDto;
import edu.epam.trainingmanager.facade.GymFacade;
import edu.epam.trainingmanager.storage.UserStorage;
import edu.epam.trainingmanager.storage.impl.TraineeStorage;
import edu.epam.trainingmanager.storage.impl.TrainerStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Duration;
import java.time.LocalDate;

public class TrainingManagerApplication {
    private static final Logger logger = LoggerFactory.getLogger(TrainingManagerApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Application");

//        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
//            GymFacade gymFacade = context.getBean(GymFacade.class);
//            UserStorage<Trainee> traineeStorage = context.getBean(TraineeStorage.class);
//            UserStorage<Trainer> trainerStorage = context.getBean(TrainerStorage.class);
//
//            // ------ Trainee Operations ------
//            printSection("Registering Trainees");
//            TraineeDto trainee1 = gymFacade.registerTrainee(
//                    TraineeCreateDto.builder()
//                            .firstName("Liam")
//                            .lastName("Anderson")
//                            .dateOfBirth(LocalDate.of(1997, 5, 12))
//                            .address("321 Elm Street, Toronto, Canada")
//                            .build());
//            TraineeDto trainee2 = gymFacade.registerTrainee(
//                    TraineeCreateDto.builder()
//                            .firstName("Liam")
//                            .lastName("Anderson")
//                            .dateOfBirth(LocalDate.of(1998, 4, 22))
//                            .address("789 Maple Drive, Vancouver, Canada")
//                            .build());
//            printItem("Added", trainee1);
//            printItem("Added", trainee2);
//
//            printSection("All Trainees");
//            gymFacade.getAllTrainees().forEach(TrainingManagerApplication::printItem);
//
//            printSection("Updating Trainee");
//            TraineeUpdateDto updateDto = TraineeUpdateDto.builder()
//                    .id(trainee1.getId())
//                    .address("500 Elm Street, Toronto, Canada")
//                    .isActive(true)
//                    .build();
//            TraineeDto updatedTrainee = gymFacade.updateTrainee(updateDto);
//            printItem("Updated", updatedTrainee);
//
//            printSection("Deleting Trainee");
//            gymFacade.deleteTrainee(trainee2.getId());
//            System.out.println("Deleted ID: " + trainee2.getId());
//
//            printSection("Trainees After Deletion");
//            gymFacade.getAllTrainees().forEach(TrainingManagerApplication::printItem);
//
//            // ------ Trainer Operations ------
//            printSection("Registering Trainers");
//            TrainerDto trainer1 = gymFacade.registerTrainer(
//                    TrainerCreateDto.builder()
//                            .firstName("Michael")
//                            .lastName("Brown")
//                            .specialization(new TrainingType("Cardio"))
//                            .build());
//            TrainerDto trainer2 = gymFacade.registerTrainer(
//                    TrainerCreateDto.builder()
//                            .firstName("Sophia")
//                            .lastName("Martinez")
//                            .specialization(new TrainingType("Strength Training"))
//                            .build());
//            printItem("Added", trainer1);
//            printItem("Added", trainer2);
//
//            printSection("All Trainers");
//            gymFacade.getAllTrainers().forEach(TrainingManagerApplication::printItem);
//
//            printSection("Updating Trainer");
//            TrainerUpdateDto trainerUpdateDto = TrainerUpdateDto.builder()
//                    .id(trainer1.getId())
//                    .specialization(new TrainingType("Advanced Cardio"))
//                    .isActive(true)
//                    .build();
//            TrainerDto updatedTrainer = gymFacade.updateTrainer(trainerUpdateDto);
//            printItem("Updated", updatedTrainer);
//
//            printSection("Trainers After Update");
//            gymFacade.getAllTrainers().forEach(TrainingManagerApplication::printItem);
//
//            // ------ Training Operations ------
//            printSection("Scheduling a Training");
//            TrainingDto training = gymFacade.registerTraining(
//                    TrainingCreateDto.builder()
//                            .trainingName("Strength Training Evening")
//                            .trainingType(trainer2.getSpecialization())
//                            .trainerId(trainer2.getId())
//                            .traineeId(trainee1.getId())
//                            .trainingDate(LocalDate.of(2025, 6, 3))
//                            .trainingDuration(Duration.ofMinutes(60))
//                            .build());
//            printItem("Added", training);
//
//            printSection("All Trainings");
//            gymFacade.getAllTrainings().forEach(TrainingManagerApplication::printItem);
//
//            printSection("Trainings by Trainer " + trainer2.getId());
//            gymFacade.getTrainingsByTrainer(trainer2.getId()).forEach(TrainingManagerApplication::printItem);
//
//            printSection("Trainings by Trainee " + trainee1.getId());
//            gymFacade.getTrainingsByTrainee(trainee1.getId()).forEach(TrainingManagerApplication::printItem);
//
//            // ------ Direct Storage Access ------
//            printSection("Direct Access: All Trainees in Storage");
//            traineeStorage.findAll().forEach(TrainingManagerApplication::printItem);
//
//            printSection("Direct Access: All Trainers in Storage");
//            trainerStorage.findAll().forEach(TrainingManagerApplication::printItem);
//
//            printSection("Trainee Passwords");
//            traineeStorage.findAll().forEach(t -> System.out.printf("Username: %s, Password: %s%n",
//                    t.getUsername(), t.getPassword()));
//
//            printSection("Trainer Passwords");
//            trainerStorage.findAll().forEach(t -> System.out.printf("Username: %s, Password: %s%n",
//                    t.getUsername(), t.getPassword()));
//        }

        logger.info("Closing Application");
    }

    private static void printSection(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    private static void printItem(Object item) {
        System.out.println(item);
    }

    private static void printItem(String prefix, Object item) {
        System.out.printf("%s: %s%n", prefix, item);
    }
}








