package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.storage.common.*;

public interface TraineeStorage
        extends StorageCreatable<Trainee>,
        StorageUpdatable<Trainee>,
        StorageSelectable<Trainee>,
        StorageDeletable<Trainee>,
        StorageSelectableByUsername<Trainee>, UserStorage<Trainee> { }
