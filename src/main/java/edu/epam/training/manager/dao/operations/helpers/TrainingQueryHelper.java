package edu.epam.training.manager.dao.operations.helpers;

import edu.epam.training.manager.constants.ParameterConstants;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.TrainingType;
import edu.epam.training.manager.exception.base.DaoException;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingQueryHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingQueryHelper.class);

    private static final String LOG_QUERY_PARAMS  =
            "Querying trainings: mainRole='{}', mainUsername='{}', secondaryRole='{}', " +
                    "secondaryUsername='{}', trainingType='{}', fromDate={}, toDate={}";
    private static final String LOG_QUERY_RESULT  = "Query returned {} trainings";
    private static final String LOG_QUERY_ERROR   =
            "Error querying trainings with parameters mainRole='{}', mainUsername='{}': {}";
    private static final String ERROR_FETCH_TEMPLATE =
            "Error fetching trainings with parameters %s=%s";

    public static List<Training> fetchTrainings(
            SessionFactory sessionFactory,
            String mainRole, String mainUsername,
            String secondaryRole, String secondaryUsername,
            String trainingType,
            LocalDate fromDate, LocalDate toDate
    ) {
        logQueryParameters(mainRole, mainUsername, secondaryRole, secondaryUsername, trainingType, fromDate, toDate);

        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Training> cq = cb.createQuery(Training.class);
            Root<Training> root = cq.from(Training.class);

            List<Predicate> predicates = buildPredicates(cb, root,
                    mainRole, mainUsername,
                    secondaryRole, secondaryUsername,
                    trainingType,
                    fromDate, toDate);

            cq.where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(root.get(ParameterConstants.TRAINING_DATE)));

            List<Training> result = session.createQuery(cq).getResultList();
            LOGGER.debug(LOG_QUERY_RESULT, result.size());
            return result;

        } catch (Exception e) {
            LOGGER.error(LOG_QUERY_ERROR, mainRole, mainUsername, e.getMessage(), e);
            throw new DaoException(
                    String.format(ERROR_FETCH_TEMPLATE, mainRole, mainUsername),
                    e
            );
        }
    }

    private static void logQueryParameters(
            String mainRole, String mainUsername,
            String secondaryRole, String secondaryUsername,
            String trainingType, LocalDate fromDate, LocalDate toDate
    ) {
        LOGGER.debug(LOG_QUERY_PARAMS,
                mainRole, mainUsername,
                secondaryRole, secondaryUsername,
                trainingType, fromDate, toDate);
    }

    private static List<Predicate> buildPredicates(
            CriteriaBuilder cb,
            Root<Training> root,
            String mainRole, String mainUsername,
            String secondaryRole, String secondaryUsername,
            String trainingType,
            LocalDate fromDate, LocalDate toDate
    ) {
        List<Predicate> predicates = new ArrayList<>();

        addUserPredicate(predicates, cb, root, mainRole, mainUsername);
        addUserPredicate(predicates, cb, root, secondaryRole, secondaryUsername);
        addTrainingTypePredicate(predicates, cb, root, trainingType);
        addDateRangePredicates(predicates, cb, root, fromDate, toDate);

        return predicates;
    }

    private static void addUserPredicate(
            List<Predicate> predicates,
            CriteriaBuilder cb,
            Root<Training> root,
            String role, String username
    ) {
        if (role != null && username != null && !username.isEmpty()) {
            Join<?, ?> roleJoin = root.join(role);
            Join<?, ?> userJoin = roleJoin.join(ParameterConstants.USER);
            predicates.add(cb.equal(userJoin.get(ParameterConstants.USERNAME), username));
        }
    }

    private static void addTrainingTypePredicate(
            List<Predicate> predicates,
            CriteriaBuilder cb,
            Root<Training> root,
            String trainingType
    ) {
        if (trainingType != null && !trainingType.isEmpty()) {
            Join<Training, TrainingType> typeJoin = root.join(ParameterConstants.TRAINING_TYPE);
            predicates.add(cb.equal(typeJoin.get(ParameterConstants.TRAINING_TYPE_NAME), trainingType));
        }
    }

    private static void addDateRangePredicates(
            List<Predicate> predicates,
            CriteriaBuilder cb,
            Root<Training> root,
            LocalDate fromDate, LocalDate toDate
    ) {
        if (fromDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(ParameterConstants.TRAINING_DATE), fromDate));
        }
        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(ParameterConstants.TRAINING_DATE), toDate));
        }
    }
}
