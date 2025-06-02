package edu.epam.trainingmanager.utils.mapping;

import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.dto.trainee.TraineeDto;

public class TraineeMapper {

    public static TraineeDto toDto(Trainee trainee) {
        if (trainee == null) {
            return null;
        }
        return TraineeDto.builder()
                .id(trainee.getId())
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .username(trainee.getUsername())
                .isActive(trainee.isActive())
                .dateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .build();
    }
}
