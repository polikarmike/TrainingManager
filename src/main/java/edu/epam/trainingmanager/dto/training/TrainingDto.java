package edu.epam.trainingmanager.dto.training;

import edu.epam.trainingmanager.domain.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDto {
    private String id;
    private String trainingName;
    private TrainingType trainingType;
    private String trainerId;
    private String traineeId;
    private LocalDate trainingDate;
    private Duration trainingDuration;
}
