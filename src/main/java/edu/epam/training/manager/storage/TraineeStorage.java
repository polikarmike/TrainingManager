package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.storage.common.*;

public interface TraineeStorage
        extends Creatable<Trainee>,
        Updatable<Trainee>,
        Selectable<Trainee>,
        Deletable<Trainee>,
        SelectableByUsername<Trainee>, UserStorage<Trainee> { }
