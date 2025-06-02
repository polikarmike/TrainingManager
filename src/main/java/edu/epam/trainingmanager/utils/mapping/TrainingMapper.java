package edu.epam.trainingmanager.utils.mapping;

import edu.epam.trainingmanager.domain.Training;
import edu.epam.trainingmanager.dto.training.TrainingDto;

public class TrainingMapper {
    public static TrainingDto toDto(Training training) {
        if (training == null) {
            return null;
        }

        return TrainingDto.builder()
                .id(training.getId())
                .trainingName(training.getTrainingName())
                .trainingType(training.getTrainingType())
                .trainerId(training.getTrainerId())
                .traineeId(training.getTraineeId())
                .trainingDate(training.getTrainingDate())
                .trainingDuration(training.getTrainingDuration())
                .build();
    }
}
