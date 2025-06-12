package edu.epam.training.manager.domain.common;

import edu.epam.training.manager.domain.User;

public interface HasUser {
    User getUser();
    void setUser(User user);
}
