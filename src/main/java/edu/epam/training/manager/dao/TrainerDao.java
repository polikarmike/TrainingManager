package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.operations.*;
import edu.epam.training.manager.domain.Trainer;

public interface TrainerDao
        extends CreateDao<Trainer, Long>,
                ReadDao<Trainer, Long>,
                UpdateDao<Trainer, Long>,
                UserAccountOperations<Trainer, Long>,
                TrainerDaoOperations{
}
