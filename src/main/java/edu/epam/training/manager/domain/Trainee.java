package edu.epam.training.manager.domain;

import edu.epam.training.manager.constants.EntityConstants;
import edu.epam.training.manager.domain.base.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Trainee extends UserEntity<Long> {
    @Column(name = EntityConstants.COL_DATE_OF_BIRTH)
    private LocalDate dateOfBirth;
    private String address;

    @ManyToMany
    @JoinTable(
            name = EntityConstants.TABLE_TRAINER_TRAINEE,
            joinColumns = @JoinColumn(name = EntityConstants.COL_TRAINEE_ID),
            inverseJoinColumns = @JoinColumn(name = EntityConstants.COL_TRAINER_ID)
    )
    private final Set<Trainer> trainers = new HashSet<>();

    @OneToMany(mappedBy = EntityConstants.TABLE_TRAINEE, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final Set<Training> trainings = new HashSet<>();

    @Override
    public String toString() {
        return "Trainee{" +
                "userId='" + user.getId() + '\'' +
                ", firstName='" + user.getFirstName() + '\'' +
                ", lastName='" + user.getLastName() + '\'' +
                ", username='" + user.getUsername() + '\'' +
                ", isActive=" + user.isActive() +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';
    }
}
