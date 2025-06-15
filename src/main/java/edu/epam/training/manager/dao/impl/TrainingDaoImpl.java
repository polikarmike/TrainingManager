package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.dao.base.BaseDao;
import edu.epam.training.manager.dao.interfaces.CreateDao;
import edu.epam.training.manager.domain.Training;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingDaoImpl extends BaseDao<Training, Long> implements CreateDao<Training, Long> {
    public TrainingDaoImpl(SessionFactory sessionFactory) {
        super(Training.class, sessionFactory);
    }
}
