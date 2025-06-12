package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.operations.CreateDao;
import edu.epam.training.manager.dao.operations.ReadDao;
import edu.epam.training.manager.domain.Training;

public interface TrainingDao extends CreateDao<Training, Long>, ReadDao<Training, Long> {
}
