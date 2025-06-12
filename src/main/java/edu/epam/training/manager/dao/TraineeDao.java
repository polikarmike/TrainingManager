package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.operations.*;
import edu.epam.training.manager.domain.Trainee;

public interface TraineeDao
        extends CreateDao<Trainee, Long>,
                ReadDao<Trainee, Long>,
                UpdateDao<Trainee, Long>,
                DeleteDao<Trainee, Long>,
                UserAccountOperations<Trainee, Long>,
                TraineeDaoOperations{
}
