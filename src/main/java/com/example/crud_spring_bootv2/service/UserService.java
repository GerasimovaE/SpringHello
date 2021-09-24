package com.example.crud_spring_bootv2.service;


import com.example.crud_spring_bootv2.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void update(User user);

    void removeUser(User user);

    User getUserById(long id);

    User getUserByEmail(String email);

    List<User> getAllUsers();


}
