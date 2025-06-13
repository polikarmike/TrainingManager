package edu.epam.training.manager.domain;

import edu.epam.training.manager.constants.EntityConstants;
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
    @JoinColumn(name = EntityConstants.COL_SPECIALIZATION, unique = true)
    private TrainingType specialization;

    @OneToMany(mappedBy = EntityConstants.TABLE_TRAINER)
    private final Set<Training> trainings = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = EntityConstants.TABLE_TRAINER_TRAINEE,
            joinColumns = @JoinColumn(name = EntityConstants.COL_TRAINER_ID),
            inverseJoinColumns = @JoinColumn(name = EntityConstants.COL_TRAINEE_ID)
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
