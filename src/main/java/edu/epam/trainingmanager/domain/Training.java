package edu.epam.trainingmanager.domain;

import edu.epam.trainingmanager.domain.common.Identifiable;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training implements Identifiable {
    private String id;
    private String trainingName;
    private TrainingType trainingType;
    private String trainerId;
    private String traineeId;
    private LocalDate trainingDate;
    private Duration trainingDuration;
}
