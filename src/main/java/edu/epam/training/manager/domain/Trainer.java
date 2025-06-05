package edu.epam.training.manager.domain;

import edu.epam.training.manager.domain.base.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Trainer extends UserEntity<UUID> {
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
