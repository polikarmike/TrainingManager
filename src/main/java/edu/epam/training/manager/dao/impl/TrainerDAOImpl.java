package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.dao.base.AbstractUserDAO;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.storage.impl.TrainerStorageImpl;

public class TrainerDAOImpl extends AbstractUserDAO<Trainer, TrainerStorageImpl> implements TrainerDao {
}
