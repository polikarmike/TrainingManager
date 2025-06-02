package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.dao.TrainingDao;
import edu.epam.training.manager.dao.base.AbstractDAO;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.storage.impl.TrainingStorageImpl;

public class TrainingDAOImpl
        extends AbstractDAO<Training, TrainingStorageImpl> implements TrainingDao {
}
