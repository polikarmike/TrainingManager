package edu.epam.training.manager.domain;

import edu.epam.training.manager.constants.DatabaseConstants;
import edu.epam.training.manager.domain.base.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Trainer extends UserEntity<Long> {

    @OneToOne
    @JoinColumn(name = DatabaseConstants.COL_SPECIALIZATION, unique = true)
    private TrainingType specialization;

    @OneToMany(mappedBy = DatabaseConstants.TABLE_TRAINER)
    private final Set<Training> trainings = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = DatabaseConstants.TABLE_TRAINER_TRAINEE,
            joinColumns = @JoinColumn(name = DatabaseConstants.COL_TRAINER_ID),
            inverseJoinColumns = @JoinColumn(name = DatabaseConstants.COL_TRAINEE_ID)
    )
    private final Set<Trainee> trainees = new HashSet<>();

    @Override
    public String toString() {
        return "Trainer{" +
                "id='" + user.getId() + '\'' +
                ", firstName='" + user.getFirstName() + '\'' +
                ", lastName='" + user.getLastName() + '\'' +
                ", username='" + user.getUsername() + '\'' +
                ", isActive=" + user.isActive() +
                ", specialization='" + specialization.getTrainingTypeName() + '\'' +
                '}';
    }
}
