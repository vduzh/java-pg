package by.duzh.junit5.assertj;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class UserService {
    private final List<User> users = new ArrayList<>();


    public List<User> getAll() {
        return users;
    }

    public boolean add(User ...users) {
        return this.users.addAll(Arrays.asList(users));
    }

    public Optional<User> login(Object name, String password) {
        if (name == null || password == null) {
            throw new IllegalArgumentException("User name or password cannot be null.");
        }

        return users.stream()
                .filter(u -> u.getName().equals(name))
                .filter(u -> u.getPassword().equals(password))
                .findFirst();
    }

    public Map<Integer, User> getAllConvertedToMapById() {
        return users.stream()
                .collect(toMap(User::getId, Function.identity()));
    }

    public void fail() {
        throw new IllegalArgumentException("This method is expected to fail");
    }
}
