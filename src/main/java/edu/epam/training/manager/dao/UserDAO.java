package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.common.DaoSelectableByUsername;
import edu.epam.training.manager.domain.base.UserEntity;


public interface UserDAO<T extends UserEntity> extends BaseDAO<T>, DaoSelectableByUsername<T> {

}