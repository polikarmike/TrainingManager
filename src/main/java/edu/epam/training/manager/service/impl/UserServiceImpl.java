package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.interfaces.UserManagementDao;
import edu.epam.training.manager.exception.InvalidStateException;
import edu.epam.training.manager.service.UserService;
import edu.epam.training.manager.utils.generation.UsernameGenerator;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Setter
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String SERVICE_NAME = "UserServiceImpl";

    private static final int MAX_ATTEMPTS = 250;

    private static final String LOG_GENERATE_START     =
            SERVICE_NAME + " - Generating unique username for: firstName={}, lastName={}";
    private static final String LOG_TRY_CANDIDATE      =
            SERVICE_NAME + " - Trying username candidate: {}";
    private static final String LOG_GENERATE_FAIL      =
            SERVICE_NAME + " - Failed to generate unique username after {} attempts";
    private static final String ERR_GENERATE_USERNAME  =
            SERVICE_NAME + ": Could not generate unique username after %d attempts";

    private final UserManagementDao userManagementDao;
    private final UsernameGenerator usernameGenerator;

    public UserServiceImpl(UserManagementDao userManagementDao, UsernameGenerator usernameGenerator) {
        this.userManagementDao = userManagementDao;
        this.usernameGenerator = usernameGenerator;
    }

    @Override
    public String generateUniqueUsername(String firstName, String lastName) {
        LOGGER.debug(LOG_GENERATE_START, firstName, lastName);

        String baseUsername = usernameGenerator.generateBaseUsername(firstName, lastName);
        Set<String> existingUsernames = new HashSet<>(userManagementDao.findUsernamesWithPrefix(baseUsername));

        return usernameGenerator.generateCandidates(baseUsername, MAX_ATTEMPTS).stream()
                .peek(candidate -> LOGGER.debug(LOG_TRY_CANDIDATE, candidate))
                .filter(candidate -> !existingUsernames.contains(candidate))
                .findFirst()
                .orElseThrow(() -> {
                    LOGGER.error(LOG_GENERATE_FAIL, MAX_ATTEMPTS);
                    return new InvalidStateException(
                            String.format(ERR_GENERATE_USERNAME, MAX_ATTEMPTS)
                    );
                });
    }
}
