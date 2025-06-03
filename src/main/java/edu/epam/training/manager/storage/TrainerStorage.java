package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.storage.common.*;

public interface TrainerStorage
        extends StorageCreatable<Trainer>,
        StorageUpdatable<Trainer>,
        StorageSelectable<Trainer>,
        StorageSelectableByUsername<Trainer> { }
