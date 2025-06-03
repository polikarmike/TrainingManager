package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.service.common.ServiceCreatable;
import edu.epam.training.manager.service.common.ServiceReadable;

import java.util.UUID;

public interface TrainingService extends
        ServiceCreatable<Training>,
        ServiceReadable<Training> {
}

