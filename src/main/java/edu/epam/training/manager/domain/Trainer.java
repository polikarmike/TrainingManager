package edu.epam.training.manager.domain;

import edu.epam.training.manager.domain.base.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Trainer extends UserEntity {
    private TrainingType specialization;

    @Override
    public String toString() {
        return "Trainer{" +
                "id='" + getId() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", isActive=" + isActive() +
                ", specialization='" + specialization.getDisplayName() + '\'' +
                '}';
    }
}