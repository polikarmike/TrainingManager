package edu.epam.training.manager.domain;

import lombok.Getter;

@Getter
public enum TrainingType {
    YOGA("Yoga"),
    CARDIO("Cardio"),
    STRENGTH("Strength"),
    PILATES("Pilates"),
    CROSSFIT("CrossFit"),
    DANCE("Dance"),
    BOXING("Boxing"),
    CYCLING("Cycling"),
    SWIMMING("Swimming");

    private final String displayName;

    TrainingType(String displayName) {
        this.displayName = displayName;
    }
}
