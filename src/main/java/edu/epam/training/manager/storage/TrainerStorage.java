package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.storage.common.*;

public interface TrainerStorage
        extends Creatable<Trainer>,
        Updatable<Trainer>,
        Selectable<Trainer>,
        SelectableByUsername<Trainer> { }
