package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.dao.base.AbstractCreateReadDao;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.storage.BaseStorage;

import java.util.UUID;

public class TrainingDAOImpl extends AbstractCreateReadDao<Training, BaseStorage<Training, UUID>, UUID> {
}
