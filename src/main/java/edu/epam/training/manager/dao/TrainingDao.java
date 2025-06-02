package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.common.DaoCreatable;
import edu.epam.training.manager.dao.common.DaoSelectable;
import edu.epam.training.manager.domain.Training;

public interface TrainingDao extends
        DaoCreatable<Training>,
        DaoSelectable<Training> { }
