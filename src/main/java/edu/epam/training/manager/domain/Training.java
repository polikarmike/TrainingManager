package edu.epam.training.manager.domain;

import edu.epam.training.manager.constants.DatabaseConstants;
import edu.epam.training.manager.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Training extends BaseEntity<Long> {
    @Column(name= DatabaseConstants.COL_TRAINING_NAME, nullable = false)
    private String trainingName;

    @ManyToOne
    @JoinColumn(name = DatabaseConstants.COL_TRAINING_TYPE_ID, nullable = false)
    private TrainingType trainingType;

    @ManyToOne
    @JoinColumn(name = DatabaseConstants.COL_TRAINER_ID, nullable = false)
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = DatabaseConstants.COL_TRAINEE_ID, nullable = false)
    private Trainee trainee;

    @Column(name= DatabaseConstants.COL_TRAINING_DATE, nullable = false)
    private LocalDate trainingDate;

    @Column(name= DatabaseConstants.COL_TRAINING_DURATION, nullable = false)
    private Integer trainingDuration;
}
