package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.common.DaoCreatable;
import edu.epam.training.manager.dao.common.DaoDeletable;
import edu.epam.training.manager.dao.common.DaoSelectable;
import edu.epam.training.manager.dao.common.DaoUpdatable;
import edu.epam.training.manager.domain.base.BaseEntity;

public interface BaseDAO<T extends BaseEntity> extends
        DaoCreatable<T>,
        DaoUpdatable<T>,
        DaoDeletable<T>,
        DaoSelectable<T> {

}
