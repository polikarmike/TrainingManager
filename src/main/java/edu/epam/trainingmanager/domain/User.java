package edu.epam.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.epam.trainingmanager.domain.common.HasUsername;
import edu.epam.trainingmanager.domain.common.Identifiable;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements Identifiable, HasUsername {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;

    @JsonProperty("isActive")
    protected boolean isActive;
}
