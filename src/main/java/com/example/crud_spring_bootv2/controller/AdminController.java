package com.example.crud_spring_bootv2.controller;

import com.example.crud_spring_bootv2.exception_handling.NoSuchUserException;
import com.example.crud_spring_bootv2.exception_handling.UserIncorrectData;
import com.example.crud_spring_bootv2.model.Role;
import com.example.crud_spring_bootv2.model.User;
import com.example.crud_spring_bootv2.service.RoleService;
import com.example.crud_spring_bootv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //INDEX
    @GetMapping("/users")
    public  ResponseEntity<List<User>> index(){
        List<User> allUsers = userService.getAllUsers();
        return  allUsers != null &&  !allUsers.isEmpty()
                ? new ResponseEntity<>(allUsers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //GET_USER_BY_EMAIL
    @GetMapping("/currentUser/{email}")
    public User getUserByEmail(@PathVariable("email") String email){
        User user = userService.getUserByEmail(email);

        return user;
    }

    //SHOW
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") long id){
        User user = userService.getUserById(id);

        if (user == null){
            throw new NoSuchUserException("There is no user with ID = " + id + " in database");
        }
        return user;
    }

    //GET_ROLES
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles(){
        List<Role> allRoles = roleService.getAllRoles();

        return  allRoles != null &&  !allRoles.isEmpty()
                ? new ResponseEntity<>(allRoles, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //NEW
    @PostMapping("/users")
    public User addNewUser(@RequestBody User user){
        userService.saveUser(user);
        return user;
    }

    //UPDATE
    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        userService.update(user);
        return user;
    }

    //DELETE
    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") long id) {

        User user = userService.getUserById(id);

        if (user == null){
            throw new NoSuchUserException("There is no user with ID = " + id + " in database");
        }

        userService.removeUser(id);
        return "User with ID = " + id + " was deleted";
    }


}
