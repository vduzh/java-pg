package by.duzh.mockito.dao;

public class UserDao {
    public boolean delete(Integer id) {
        System.out.println("User with id " + id + " deleted");
        return true;
    }
}
