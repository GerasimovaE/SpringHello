package web.service;

import web.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void update(User user);

    void removeUser(User user);

    User getUserById(long id);

    List<User> getAllUsers();


}
