package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.common.DaoCreatable;
import edu.epam.training.manager.dao.common.DaoSelectable;
import edu.epam.training.manager.dao.common.DaoSelectableByUsername;
import edu.epam.training.manager.dao.common.DaoUpdatable;
import edu.epam.training.manager.domain.Trainer;

public interface TrainerDao extends
        DaoCreatable<Trainer>,
        DaoUpdatable<Trainer>,
        DaoSelectable<Trainer>,
        DaoSelectableByUsername<Trainer> { }
