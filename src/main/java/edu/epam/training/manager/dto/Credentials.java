package edu.epam.training.manager.dto;

import java.util.Objects;

public record Credentials(String username, String password) {
    private static final String USERNAME_NOT_NULL_MESSAGE = "username must not be null";
    private static final String PASSWORD_NOT_NULL_MESSAGE = "password must not be null";

    public Credentials(String username, String password) {
        this.username = Objects.requireNonNull(username, USERNAME_NOT_NULL_MESSAGE);
        this.password = Objects.requireNonNull(password, PASSWORD_NOT_NULL_MESSAGE);
    }
}
