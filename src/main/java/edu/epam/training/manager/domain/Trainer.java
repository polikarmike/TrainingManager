package edu.epam.training.manager.domain;

import edu.epam.training.manager.domain.common.HasUser;
import edu.epam.training.manager.domain.base.BaseEntity;
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
public class Trainer extends BaseEntity<Long> implements HasUser {

    @OneToOne
    @JoinColumn(name = "specialization", unique = true)
    private TrainingType specialization;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "trainer")
    private Set<Training> trainings = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "trainer_trainee",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id")
    )
    private Set<Trainee> trainees = new HashSet<>();

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
