package by.duzh.mockito.service;

import by.duzh.mockito.dao.UserDao;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean deleteUser(Integer id) {
        return userDao.delete(id);
    }
}

