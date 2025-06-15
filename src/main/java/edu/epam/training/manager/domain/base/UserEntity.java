package edu.epam.training.manager.domain.base;

import edu.epam.training.manager.constants.DatabaseConstants;
import edu.epam.training.manager.domain.User;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserEntity<ID> extends BaseEntity<ID> {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = DatabaseConstants.COL_USER_ID, unique = true)
    protected User user;
}
