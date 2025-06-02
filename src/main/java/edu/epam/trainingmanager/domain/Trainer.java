package edu.epam.trainingmanager.domain;

import edu.epam.trainingmanager.domain.common.Identifiable;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Trainer extends User implements Identifiable {
    private TrainingType specialization;

    @Override
    public String toString() {
        return "Trainer{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", isActive=" + isActive +
                ", specialization='" + specialization.getName() + '\'' +
                '}';
    }
}