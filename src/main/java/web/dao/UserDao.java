package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

    void update(User user);

    void removeUser(User user);

    User getUserById(long id);

    List<User> getAllUsers();

}
