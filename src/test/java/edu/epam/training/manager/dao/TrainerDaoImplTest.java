package edu.epam.training.manager.dao;

import edu.epam.training.manager.constants.ParameterConstants;
import edu.epam.training.manager.dao.impl.TrainerDaoImpl;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.TrainingType;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    private TrainerDaoImpl trainerDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        trainerDao = new TrainerDaoImpl(sessionFactory);
    }

    @Test
    void testCreate_success() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);

        doNothing().when(session).persist(trainer);
        doNothing().when(session).flush();
        doNothing().when(session).refresh(trainer);

        Trainer created = trainerDao.create(trainer);

        assertEquals(trainer, created);

        verify(session).persist(trainer);
        verify(session).flush();
        verify(session).refresh(trainer);
    }

    @Test
    void testCreate_throwsDaoException() {
        Trainer trainer = Trainer.builder().specialization(new TrainingType()).build();
        User user = new User();
        user.setId(1L);
        trainer.setUser(user);

        doThrow(new RuntimeException("DB error")).when(session).persist(trainer);

        DaoException ex = assertThrows(DaoException.class, () -> trainerDao.create(trainer));
        assertTrue(ex.getMessage().contains("Trainer"));
    }

    @Test
    void testFindById_success() {
        Long id = 1L;
        Trainer trainer = new Trainer();
        trainer.setId(id);

        when(session.get(Trainer.class, id)).thenReturn(trainer);

        Optional<Trainer> result = trainerDao.findById(id);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());

        verify(session).get(Trainer.class, id);
    }

    @Test
    void testFindById_notFound() {
        Long id = 2L;

        when(session.get(Trainer.class, id)).thenReturn(null);

        Optional<Trainer> result = trainerDao.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindById_throwsDaoException() {
        Long id = 1L;

        when(session.get(Trainer.class, id)).thenThrow(new RuntimeException("DB error"));

        DaoException ex = assertThrows(DaoException.class, () -> trainerDao.findById(id));
        assertTrue(ex.getMessage().contains("Trainer"));
    }

    @Test
    void testUpdate_success() {
        Trainer trainer = new Trainer();
        trainer.setId(1L);

        when(session.merge(trainer)).thenReturn(trainer);
        doNothing().when(session).flush();
        doNothing().when(session).refresh(trainer);

        Trainer updated = trainerDao.update(trainer);

        assertEquals(trainer, updated);

        verify(session).merge(trainer);
        verify(session).flush();
        verify(session).refresh(trainer);
    }

    @Test
    void testUpdate_throwsDaoException() {
        Trainer trainer = Trainer.builder().specialization(new TrainingType()).build();
        User user = new User();
        user.setId(1L);
        trainer.setUser(user);

        when(session.merge(trainer)).thenThrow(new RuntimeException("DB error"));

        DaoException ex = assertThrows(DaoException.class, () -> trainerDao.update(trainer));
        assertTrue(ex.getMessage().contains("Trainer"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFindByUsername_success() {
        Trainer expectedTrainer = new Trainer();
        expectedTrainer.setId(1L);

        Query<Trainer> query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter(ParameterConstants.USERNAME, "john")).thenReturn(query);
        when(query.uniqueResultOptional()).thenReturn(Optional.of(expectedTrainer));

        Optional<Trainer> result = trainerDao.findByUsername("john");

        assertTrue(result.isPresent());
        assertEquals(expectedTrainer, result.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFindUnassignedTrainers_success() {
        String traineeUsername = "testUser";
        Trainer trainer1 = new Trainer();
        trainer1.setId(1L);
        Trainer trainer2 = new Trainer();
        trainer2.setId(2L);
        List<Trainer> mockResult = List.of(trainer1, trainer2);

        Query<Trainer> query = mock(Query.class);
        when(session.createQuery(HqlQueryConstants.HQL_TRAINER_FIND_UNASSIGNED_BY_TRAINEE, Trainer.class))
                .thenReturn(query);
        when(query.setParameter("username", traineeUsername)).thenReturn(query);
        when(query.getResultList()).thenReturn(mockResult);

        List<Trainer> result = trainerDao.findUnassignedTrainers(traineeUsername);

        assertEquals(2, result.size());
        assertTrue(result.contains(trainer1));
        assertTrue(result.contains(trainer2));

        verify(session).createQuery(HqlQueryConstants.HQL_TRAINER_FIND_UNASSIGNED_BY_TRAINEE, Trainer.class);
        verify(query).setParameter("username", traineeUsername);
        verify(query).getResultList();
    }

    @Test
    void testFindUnassignedTrainers_throwsDaoException() {
        when(session.createQuery(anyString(), eq(Trainer.class)))
                .thenThrow(new RuntimeException("DB error"));

        DaoException ex = assertThrows(DaoException.class, () -> trainerDao.findUnassignedTrainers("any.user"));
        assertTrue(ex.getMessage().contains("Error fetching unassigned trainers"));
    }
}
