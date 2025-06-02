package by.duzh.mockito.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserDao userDao;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = mock(UserDao.class);
        userService = new UserService(userDao);
    }

    @Test
    void shouldDeleteUser() {
        // better as vacation-templates
        doReturn(true).when(userDao).delete(10);
        userService.deleteUser(10);

        when(userDao.delete(10)).thenReturn(true);
        userService.deleteUser(10);

        when(userDao.delete(10))
                .thenReturn(true)
                .thenReturn(false);

        assertTrue(userService.deleteUser(10));
        assertFalse(userService.deleteUser(10));
    }

    @Test
    void testDummyObjects() {
        doReturn(true).when(userDao).delete(anyInt());
        userService.deleteUser(10);

        doReturn(true).when(userDao).delete(any());
        userService.deleteUser(10);
    }
}
