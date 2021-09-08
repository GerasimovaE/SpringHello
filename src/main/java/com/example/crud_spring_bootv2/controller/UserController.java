package com.example.crud_spring_bootv2.controller;

import com.example.crud_spring_bootv2.service.RoleService;
import com.example.crud_spring_bootv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping()
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "/login";
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "showUser";
    }

}
