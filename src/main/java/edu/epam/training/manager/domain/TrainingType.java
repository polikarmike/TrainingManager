package edu.epam.training.manager.domain;

import edu.epam.training.manager.domain.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "training_type")
@Getter
@EqualsAndHashCode(callSuper = true)
public class TrainingType extends BaseEntity<Long> {
    @Column(name = "training_type_name", nullable = false)
    private String trainingTypeName;
}
