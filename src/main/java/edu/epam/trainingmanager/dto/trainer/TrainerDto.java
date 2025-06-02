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
public class TrainerDto {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isActive;
    private TrainingType specialization;
}
