package edu.epam.training.manager.storage.impl;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.storage.UserStorage;
import edu.epam.training.manager.storage.base.AbstractUserStorage;

import java.util.UUID;

public class TraineeStorageImpl extends AbstractUserStorage<Trainee, UUID> implements UserStorage<Trainee, UUID> {
}
