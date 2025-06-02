package edu.epam.trainingmanager.dto.trainer;

import edu.epam.trainingmanager.domain.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerCreateDto {
    private String firstName;
    private String lastName;
    private TrainingType specialization;
}
