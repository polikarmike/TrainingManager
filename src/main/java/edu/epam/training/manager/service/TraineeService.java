package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.service.common.ServiceCreatable;
import edu.epam.training.manager.service.common.ServiceDeletable;
import edu.epam.training.manager.service.common.ServiceReadable;
import edu.epam.training.manager.service.common.ServiceUpdatable;

import java.util.UUID;

public interface TraineeService extends
        ServiceCreatable<Trainee>,
        ServiceReadable<Trainee>,
        ServiceUpdatable<Trainee>,
        ServiceDeletable<Trainee> {
}
