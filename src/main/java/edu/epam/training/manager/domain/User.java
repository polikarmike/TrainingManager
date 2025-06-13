package edu.epam.training.manager.domain;

import edu.epam.training.manager.constants.EntityConstants;
import edu.epam.training.manager.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<Long> {
    @Column(name = EntityConstants.COL_FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = EntityConstants.COL_LAST_NAME, nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = EntityConstants.COL_IS_ACTIVE, nullable = false)
    private boolean isActive;
}
