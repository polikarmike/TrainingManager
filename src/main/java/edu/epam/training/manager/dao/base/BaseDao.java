package edu.epam.training.manager.dao.base;

import edu.epam.training.manager.domain.base.BaseEntity;
import lombok.Getter;
import org.hibernate.SessionFactory;

@Getter
public abstract class BaseDao<T extends BaseEntity<ID>, ID> {
    private final Class<T> entityClass;
    private final SessionFactory sessionFactory;

    public BaseDao(Class<T> entityClass, SessionFactory sessionFactory) {
        this.entityClass = entityClass;
        this.sessionFactory = sessionFactory;
    }
}
