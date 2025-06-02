package edu.epam.trainingmanager.utils.mapping;

import edu.epam.trainingmanager.dto.trainer.TrainerDto;
import edu.epam.trainingmanager.domain.Trainer;

public class TrainerMapper {
    public static TrainerDto toDto(Trainer trainer) {
        if (trainer == null) {
            return null;
        }
        return TrainerDto.builder()
                .id(trainer.getId())
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .username(trainer.getUsername())
                .isActive(trainer.isActive())
                .specialization(trainer.getSpecialization())
                .build();
    }
}
