package edu.epam.trainingmanager.facade;

import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.domain.Trainer;
import edu.epam.trainingmanager.domain.Training;
import edu.epam.trainingmanager.dto.trainee.TraineeCreateDto;
import edu.epam.trainingmanager.dto.trainee.TraineeDto;
import edu.epam.trainingmanager.dto.trainee.TraineeUpdateDto;
import edu.epam.trainingmanager.dto.trainer.TrainerCreateDto;
import edu.epam.trainingmanager.dto.trainer.TrainerDto;
import edu.epam.trainingmanager.dto.trainer.TrainerUpdateDto;
import edu.epam.trainingmanager.dto.training.TrainingCreateDto;
import edu.epam.trainingmanager.dto.training.TrainingDto;
import edu.epam.trainingmanager.service.TraineeService;
import edu.epam.trainingmanager.service.TrainerService;
import edu.epam.trainingmanager.service.TrainingService;
import edu.epam.trainingmanager.utils.mapping.TraineeMapper;
import edu.epam.trainingmanager.utils.mapping.TrainerMapper;
import edu.epam.trainingmanager.utils.mapping.TrainingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    @Autowired
    public GymFacade(
            TraineeService traineeService,
            TrainerService trainerService,
            TrainingService trainingService) {

        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public TraineeDto registerTrainee(TraineeCreateDto dto) {
        Trainee newTrainee = traineeService.createProfile(
                Trainee.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .dateOfBirth(dto.getDateOfBirth())
                        .address(dto.getAddress())
                        .build()
        );

        return TraineeMapper.toDto(newTrainee);
    }

    public TraineeDto updateTrainee(TraineeUpdateDto dto) {
        Trainee existing = traineeService.getProfile(dto.getId());

        Trainee updatedTrainee = Trainee.builder()
                .id(existing.getId())
                .firstName(Optional.ofNullable(dto.getFirstName()).orElse(existing.getFirstName()))
                .lastName(Optional.ofNullable(dto.getLastName()).orElse(existing.getLastName()))
                .dateOfBirth(Optional.ofNullable(dto.getDateOfBirth()).orElse(existing.getDateOfBirth()))
                .address(Optional.ofNullable(dto.getAddress()).orElse(existing.getAddress()))
                .isActive(Optional.ofNullable(dto.getIsActive()).orElse(existing.isActive()))
                .username(existing.getUsername())
                .password(existing.getPassword())
                .build();

        return TraineeMapper.toDto(traineeService.updateProfile(updatedTrainee));
    }

    public void deleteTrainee(String id) {
        traineeService.deleteProfile(id);
    }

    public TraineeDto getTrainee(String id) {
        Trainee trainee = traineeService.getProfile(id);
        return TraineeMapper.toDto(trainee);
    }

    public List<TraineeDto> getAllTrainees() {
        return traineeService.getAllProfiles().stream()
                .map(TraineeMapper::toDto)
                .toList();
    }

    public TrainerDto registerTrainer(TrainerCreateDto dto) {
        Trainer newTrainer = trainerService.createProfile(
                Trainer.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .specialization(dto.getSpecialization())
                        .build()
        );

        return TrainerMapper.toDto(newTrainer);
    }

    public TrainerDto updateTrainer(TrainerUpdateDto dto) {
        Trainer existing = trainerService.getProfile(dto.getId());

        Trainer updatedTrainer = Trainer.builder()
                .id(existing.getId())
                .firstName(Optional.ofNullable(dto.getFirstName()).orElse(existing.getFirstName()))
                .lastName(Optional.ofNullable(dto.getLastName()).orElse(existing.getLastName()))
                .specialization(Optional.ofNullable(dto.getSpecialization()).orElse(existing.getSpecialization()))
                .isActive(Optional.ofNullable(dto.getIsActive()).orElse(existing.isActive()))
                .username(existing.getUsername())
                .password(existing.getPassword())
                .build();

        return TrainerMapper.toDto(trainerService.updateProfile(updatedTrainer));
    }

    public TrainerDto getTrainer(String id) {
        return TrainerMapper.toDto(trainerService.getProfile(id));
    }

    public List<TrainerDto> getAllTrainers() {
        return trainerService.getAllProfiles().stream()
                .map(TrainerMapper::toDto)
                .toList();
    }

    public TrainingDto registerTraining(TrainingCreateDto dto) {
        Training newTraining = trainingService.createTraining(
                Training.builder()
                        .trainingName(dto.getTrainingName())
                        .trainingType(dto.getTrainingType())
                        .trainerId(dto.getTrainerId())
                        .traineeId(dto.getTraineeId())
                        .trainingDate(dto.getTrainingDate())
                        .trainingDuration(dto.getTrainingDuration())
                        .build()
        );

        return TrainingMapper.toDto(newTraining);
    }


    public TrainingDto getTraining(String id) {
        return TrainingMapper.toDto(trainingService.getTraining(id));
    }

    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings().stream()
                .map(TrainingMapper::toDto)
                .toList();
    }

    public List<TrainingDto> getTrainingsByTrainer(String trainerId) {
        return trainingService.findTrainingsByTrainer(trainerId).stream()
                .map(TrainingMapper::toDto)
                .toList();
    }

    public List<TrainingDto> getTrainingsByTrainee(String traineeId) {
        return trainingService.findTrainingsByTrainee(traineeId).stream()
                .map(TrainingMapper::toDto)
                .toList();
    }
}


