package edu.epam.training.manager.dao;

import edu.epam.training.manager.constants.ParameterConstants;
import edu.epam.training.manager.dao.impl.TraineeDaoImpl;
import edu.epam.training.manager.dao.interfaces.helpers.TrainingQueryHelper;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    private TraineeDaoImpl traineeDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        traineeDao = new TraineeDaoImpl(sessionFactory);
    }

    @Test
    void testCreate_success() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        doNothing().when(session).persist(trainee);
        doNothing().when(session).flush();
        doNothing().when(session).refresh(trainee);

        Trainee created = traineeDao.create(trainee);

        assertEquals(trainee, created);

        verify(session).persist(trainee);
        verify(session).flush();
        verify(session).refresh(trainee);
    }

    @Test
    void testCreate_throwsDaoException() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setId(1L);
        trainee.setUser(user);

        doThrow(new RuntimeException("DB error")).when(session).persist(trainee);

        DaoException ex = assertThrows(DaoException.class, () -> traineeDao.create(trainee));
        assertTrue(ex.getMessage().contains("Trainee"));
    }

    @Test
    void testFindById_success() {
        Long id = 1L;
        Trainee trainee = new Trainee();
        trainee.setId(id);

        when(session.get(Trainee.class, id)).thenReturn(trainee);

        Optional<Trainee> result = traineeDao.findById(id);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());

        verify(session).get(Trainee.class, id);
    }

    @Test
    void testFindById_notFound() {
        Long id = 2L;

        when(session.get(Trainee.class, id)).thenReturn(null);

        Optional<Trainee> result = traineeDao.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindById_throwsDaoException() {
        Long id = 1L;

        when(session.get(Trainee.class, id)).thenThrow(new RuntimeException("DB error"));

        DaoException ex = assertThrows(DaoException.class, () -> traineeDao.findById(id));
        assertTrue(ex.getMessage().contains("Trainee"));
    }

    @Test
    void testUpdate_success() {
        Trainee trainee = new Trainee();
        trainee.setId(1L);

        when(session.merge(trainee)).thenReturn(trainee);
        doNothing().when(session).flush();
        doNothing().when(session).refresh(trainee);

        Trainee updated = traineeDao.update(trainee);

        assertEquals(trainee, updated);

        verify(session).merge(trainee);
        verify(session).flush();
        verify(session).refresh(trainee);
    }

    @Test
    void testUpdate_throwsDaoException() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setId(1L);
        trainee.setUser(user);

        when(session.merge(trainee)).thenThrow(new RuntimeException("DB error"));

        DaoException ex = assertThrows(DaoException.class, () -> traineeDao.update(trainee));
        assertTrue(ex.getMessage().contains("Trainee"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testDelete_success() {
        Long id = 1L;
        Trainee trainee = new Trainee();
        trainee.setId(id);

        when(session.byId(Trainee.class)).thenReturn(mock(org.hibernate.IdentifierLoadAccess.class));
        org.hibernate.IdentifierLoadAccess<Trainee> loadAccess = session.byId(Trainee.class);
        when(loadAccess.load(id)).thenReturn(trainee);
        doNothing().when(session).remove(trainee);

        traineeDao.delete(id);

        verify(session, times(2)).byId(Trainee.class);
        verify(loadAccess).load(id);
        verify(session).remove(trainee);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testDelete_throwsDaoException() {
        Long id = 1L;

        when(session.byId(Trainee.class)).thenReturn(mock(org.hibernate.IdentifierLoadAccess.class));
        org.hibernate.IdentifierLoadAccess<Trainee> loadAccess = session.byId(Trainee.class);
        when(loadAccess.load(id)).thenThrow(new RuntimeException("DB error"));

        DaoException ex = assertThrows(DaoException.class, () -> traineeDao.delete(id));
        assertTrue(ex.getMessage().contains("Trainee"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testFindByUsername_success() {
        Trainee expectedTrainee = new Trainee();
        expectedTrainee.setId(1L);

        Query<Trainee> query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter(ParameterConstants.USERNAME, "john")).thenReturn(query);
        when(query.uniqueResultOptional()).thenReturn(Optional.of(expectedTrainee));

        Optional<Trainee> result = traineeDao.findByUsername("john");

        assertTrue(result.isPresent());
        assertEquals(expectedTrainee, result.get());
    }

    @Test
    void testGetTraineeTrainings_success() {
        List<Training> expectedTrainings = List.of(new Training(), new Training());

        try (MockedStatic<TrainingQueryHelper> helperMock = mockStatic(TrainingQueryHelper.class)) {
            helperMock.when(() -> TrainingQueryHelper.fetchTrainings(
                    any(), eq(ParameterConstants.ROLE_TRAINEE), eq("alice"),
                    eq(ParameterConstants.ROLE_TRAINER), eq("bob"),
                    eq("Cardio"), any(), any())
            ).thenReturn(expectedTrainings);

            List<Training> result = traineeDao.getTraineeTrainings(
                    "alice",
                    LocalDate.of(2025, 6, 1),
                    LocalDate.of(2025, 6, 30),
                    "bob",
                    "Cardio"
            );

            assertEquals(2, result.size());
        }
    }
}
