package edu.epam.training.manager.domain.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class UserEntity extends BaseEntity {
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    @JsonProperty("isActive")
    private boolean isActive;
}
