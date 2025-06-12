package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.dao.operations.AuthenticationOperations;
import edu.epam.training.manager.dao.operations.UserSearchOperations;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class SystemUserDaoImpl implements UserSearchOperations, AuthenticationOperations {
    private final SessionFactory sessionFactory;

    public SystemUserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
