package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.service.UserService;
import edu.epam.training.manager.utils.generation.UsernameGenerator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
public class UserServiceImpl implements UserService {
    @Autowired
    private TrainerDao trainerDAO;
    @Autowired
    private TraineeDao traineeDAO;
    private UsernameGenerator usernameGenerator;

    @Override
    public String generateUniqueUsername(String firstName, String lastName) {
        return usernameGenerator.generateCandidates(firstName, lastName)
                .filter(this::isUsernameUnique)
                .findFirst()
                .orElseThrow();
    }

    private boolean isUsernameUnique(String username) {
        return trainerDAO.findByUsername(username).isEmpty()
                && traineeDAO.findByUsername(username).isEmpty();
    }
}
