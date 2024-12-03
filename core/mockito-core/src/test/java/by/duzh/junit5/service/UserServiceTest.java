package by.duzh.junit5.service;

import by.duzh.mockito.dao.UserDao;
import by.duzh.mockito.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = Mockito.mock(UserDao.class);
        userService = new UserService(userDao);
    }

    @Test
    void shouldDeleteUser() {
        // better as vacation-templates
        Mockito.doReturn(true).when(userDao).delete(10);
        userService.deleteUser(10);

        Mockito.when(userDao.delete(10)).thenReturn(true);
        userService.deleteUser(10);

        Mockito.when(userDao.delete(10))
                .thenReturn(true)
                .thenReturn(false);

        assertTrue(userService.deleteUser(10));
        assertFalse(userService.deleteUser(10));
    }

    @Test
    void testDummyObjects() {
        Mockito.doReturn(true).when(userDao).delete(Mockito.anyInt());
        userService.deleteUser(10);

        Mockito.doReturn(true).when(userDao).delete(Mockito.any());
        userService.deleteUser(10);
    }

}
