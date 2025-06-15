package edu.epam.training.manager.dao;

public class HqlQueryConstants {
    public static final String HQL_USER_FIND_BY_USERNAME =
            "FROM %s e WHERE e.user.username = :username";

    public static final String HQL_TRAINER_FIND_UNASSIGNED_BY_TRAINEE =
            "select t " +
                    "from Trainer t " +
                    "where not exists (" +
                    "  select 1 " +
                    "  from t.trainees tr " +
                    "  where tr.user.username = :username" +
                    ")";

    public static final String HQL_USER_VALIDATE =
            "FROM User u WHERE u.username = :username AND u.password = :password";
    public static final String HQL_USER_FIND_USERNAMES_WITH_PREFIX =
            "SELECT u.username FROM User u WHERE u.username LIKE :prefix";
}
