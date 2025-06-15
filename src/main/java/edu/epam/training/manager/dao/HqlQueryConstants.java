package edu.epam.training.manager.dao;

public class HqlQueryConstants {
    public static final String HQL_USER_FIND_BY_USERNAME =
            "FROM %s e WHERE e.user.username = :username";

    public static final String HQL_TRAINER_FIND_UNASSIGNED =
            "SELECT t FROM Trainer t WHERE t.trainees IS EMPTY";

    public static final String HQL_USER_VALIDATE =
            "FROM User u WHERE u.username = :username AND u.password = :password";
    public static final String HQL_USER_FIND_USERNAMES_WITH_PREFIX =
            "SELECT u.username FROM User u WHERE u.username LIKE :prefix";
}
