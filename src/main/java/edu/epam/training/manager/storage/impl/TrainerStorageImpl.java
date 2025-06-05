package edu.epam.training.manager.storage.impl;

import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.storage.UserStorage;
import edu.epam.training.manager.storage.base.AbstractUserStorage;

import java.util.UUID;

public class TrainerStorageImpl extends AbstractUserStorage<Trainer, UUID>  implements UserStorage<Trainer, UUID> {
}
