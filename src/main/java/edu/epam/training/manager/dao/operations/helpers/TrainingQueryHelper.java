package edu.epam.training.manager.dao.operations.helpers;

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

    private static final String LOG_QUERY_RESULT  =
            "Query returned {} trainings";
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
        LOGGER.debug(LOG_QUERY_PARAMS,
                mainRole, mainUsername,
                secondaryRole, secondaryUsername,
                trainingType, fromDate, toDate);

        try {
            Session session = sessionFactory.getCurrentSession();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Training> cq = cb.createQuery(Training.class);
            Root<Training> root = cq.from(Training.class);

            List<Predicate> predicates = new ArrayList<>();

            if (mainRole != null && mainUsername != null) {
                Join<?, ?> mainJoin = root.join(mainRole);
                Join<?, ?> mainUserJoin = mainJoin.join("user");
                predicates.add(cb.equal(mainUserJoin.get("username"), mainUsername));
            }

            if (secondaryRole != null && secondaryUsername != null && !secondaryUsername.isEmpty()) {
                Join<?, ?> secJoin = root.join(secondaryRole);
                Join<?, ?> secUserJoin = secJoin.join("user");
                predicates.add(cb.equal(secUserJoin.get("username"), secondaryUsername));
            }

            if (trainingType != null && !trainingType.isEmpty()) {
                Join<Training, TrainingType> typeJoin = root.join("trainingType");
                predicates.add(cb.equal(typeJoin.get("trainingTypeName"), trainingType));
            }

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("trainingDate"), fromDate));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("trainingDate"), toDate));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            cq.orderBy(cb.asc(root.get("trainingDate")));

            List<Training> result = session.createQuery(cq).getResultList();
            LOGGER.debug(LOG_QUERY_RESULT, result.size());
            return result;

        } catch (Exception e) {
            LOGGER.error(LOG_QUERY_ERROR,
                    mainRole, mainUsername, e.getMessage(), e);
            throw new DaoException(
                    String.format(ERROR_FETCH_TEMPLATE, mainRole, mainUsername),
                    e
            );
        }
    }
}
