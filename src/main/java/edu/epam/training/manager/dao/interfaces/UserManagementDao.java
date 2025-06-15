package edu.epam.training.manager.dao.interfaces;

import java.util.List;

public interface UserManagementDao {
    boolean validateCredentials(String username, String password);
    List<String> findUsernamesWithPrefix(String prefix);
}
