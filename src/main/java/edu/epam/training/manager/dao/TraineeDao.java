package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.common.*;
import edu.epam.training.manager.domain.Trainee;

public interface TraineeDao extends
        DaoCreatable<Trainee>,
        DaoUpdatable<Trainee>,
        DaoDeletable<Trainee>,
        DaoSelectable<Trainee>,
        DaoSelectableByUsername<Trainee> { }