package edu.epam.trainingmanager.dto.trainee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeCreateDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
}
