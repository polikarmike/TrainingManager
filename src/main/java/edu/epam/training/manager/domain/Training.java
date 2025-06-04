package edu.epam.training.manager.domain;

import edu.epam.training.manager.domain.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Training extends BaseEntity<UUID> {
    private String trainingName;
    private TrainingType trainingType;
    private UUID trainerId;
    private UUID traineeId;
    private LocalDate trainingDate;
    private Duration trainingDuration;
}
