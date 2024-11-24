package by.duzh.junit5.service;

import by.duzh.junit5.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("fast")
@Tag("user")
//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class UserServiceTest {

    private static final User USER_1 = User.builder().id(1).name("user1").password("password1").build();
    private static final User USER_2 = User.builder().id(2).name("user2").password("password2").build();

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void fail() {
        Assertions.fail();
    }

    @Test
    void usersEmptyIfNoUsersAdded() {
        List<User> users = userService.getAll();
        assertTrue(users.isEmpty());
    }

    @Test
    void usersSizeIfUsersAdded() {
        userService.add(USER_1);
        userService.add(USER_2);

        List<User> users = userService.getAll();
        assertEquals(2, users.size());
    }

    @Tag("login")
    @Test
    void loginSuccessIfUserExists() {
        // prepare data
        userService.add(USER_1);

        // run code
        Optional<User> user = userService.login(USER_1.getName(), USER_1.getPassword());

        // assert the result
        assertTrue(user.isPresent());
        user.ifPresent(u -> assertEquals(USER_1, u));
    }

    @Tag("login")
    @Test
    void loginFailIfPasswordIncorrect() {
        userService.add(USER_1);
        Optional<User> user = userService.login(USER_1.getName(), "wrong");
        assertTrue(user.isEmpty());
    }

    @Tag("login")
    @Test
    void loginFailIfUserDoesNotExist() {
        userService.add(USER_1);
        Optional<User> user = userService.login("wrong", USER_1.getPassword());
        assertTrue(user.isEmpty());
    }

    @Tag("login")
    @Test
    void trowExceptionIfPasswordOrPasswordIsNull() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> userService.login(null, "foo")),
                () -> assertThrows(IllegalArgumentException.class, () -> userService.login("bar", null))
        );
    }
}
