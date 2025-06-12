package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.dao.base.BaseDao;
import edu.epam.training.manager.domain.Trainer;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerDaoImpl extends BaseDao<Trainer, Long> implements TrainerDao {
    public TrainerDaoImpl(SessionFactory sessionFactory) {
        super(Trainer.class, sessionFactory);
    }
}
