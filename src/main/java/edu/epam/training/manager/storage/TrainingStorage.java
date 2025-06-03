package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.storage.common.StorageCreatable;
import edu.epam.training.manager.storage.common.StorageSelectable;

public interface TrainingStorage
        extends StorageCreatable<Training>,
        StorageSelectable<Training> { }
