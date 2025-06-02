package by.duzh.junit5.assertj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserServiceAssertJTest {

    private static final User USER_1 = User.builder().id(1).name("user1").password("password1").build();
    private static final User USER_2 = User.builder().id(2).name("user2").password("password2").build();

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void usersEmptyIfNoUsersAdded() {
        List<User> users = userService.getAll();
        assertThat(users).isEmpty();
    }

    @Test
    void usersSizeIfUsersAdded() {
        userService.add(USER_1, USER_2);
        List<User> users = userService.getAll();
        assertThat(users).hasSize(2);
    }

    @Test
    void loginSuccessIfUserExists() {
        userService.add(USER_1);
        Optional<User> user = userService.login(USER_1.getName(), USER_1.getPassword());
        assertThat(user).isPresent();
        user.ifPresent(u -> assertThat(u).isEqualTo(USER_1));
    }

    @Test
    void loginFailIfPasswordIncorrect() {
        userService.add(USER_1);
        Optional<User> user = userService.login(USER_1.getName(), "wrong");
        assertThat(user).isEmpty();
    }

    @Test
    void loginFailIfUserDoesNotExist() {
        userService.add(USER_1);
        Optional<User> user = userService.login("wrong", USER_1.getPassword());
        assertThat(user).isEmpty();
    }

    @Test
    void usersConvertedToMapById() {
        userService.add(USER_1, USER_2);
        var users = userService.getAllConvertedToMapById();

        assertAll(
                () -> assertThat(users).containsKeys(USER_1.getId(), USER_2.getId()),
                () -> assertThat(users).containsValues(USER_1, USER_2)
        );
    }
}
