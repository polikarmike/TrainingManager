package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.dao.base.BaseDao;
import edu.epam.training.manager.domain.Trainee;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeDaoImpl extends BaseDao<Trainee, Long> implements TraineeDao {
    public TraineeDaoImpl(SessionFactory sessionFactory) {
        super(Trainee.class, sessionFactory);
    }
}
