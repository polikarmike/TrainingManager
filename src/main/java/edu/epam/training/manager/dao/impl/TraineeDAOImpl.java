package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.dao.base.AbstractUserDAO;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.storage.impl.TraineeStorageImpl;

public class TraineeDAOImpl extends AbstractUserDAO<Trainee, TraineeStorageImpl> implements TraineeDao {
}