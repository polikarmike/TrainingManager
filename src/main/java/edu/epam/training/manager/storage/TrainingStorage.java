package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.storage.common.Creatable;
import edu.epam.training.manager.storage.common.Selectable;

public interface TrainingStorage
        extends Creatable<Training>,
        Selectable<Training> { }
