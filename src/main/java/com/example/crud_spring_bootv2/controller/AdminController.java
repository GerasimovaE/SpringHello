package com.example.crud_spring_bootv2.controller;

import com.example.crud_spring_bootv2.model.Role;
import com.example.crud_spring_bootv2.model.User;
import com.example.crud_spring_bootv2.service.RoleService;
import com.example.crud_spring_bootv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //INDEX
    @GetMapping()
    public String index(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }


    //SHOW
    @GetMapping("/users/{id}")
    public String showUserForAdmin(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "showUserForAdmin";
    }


    //NEW
    @GetMapping("/users/new")
    public String newPerson(Model model, @ModelAttribute("user") User user){
        model.addAttribute("roles", roleService.getAllRoles());
        return "/new";
    }

    @PostMapping("/users/user")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("likedUser") long[] roleId){

        List<Role> likedUser = new ArrayList<>();
        for (long id:
             roleId) {
            likedUser.add(roleService.getRoleById(id));
        }
        user.setLikedUser(likedUser);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    //EDIT
    @GetMapping("/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/edit";
    }

    //UPDATE
    @PostMapping("/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("likedUser") long[] roleId){
        List<Role> likedUser = new ArrayList<>();
        for (long id:
                roleId) {
            likedUser.add(roleService.getRoleById(id));
        }
        user.setLikedUser(likedUser);

        userService.update(user);
        return "redirect:/admin";
    }

    //DELETE
    @DeleteMapping("/users/{id}")
    public String delete(@ModelAttribute("user") User user) {
        userService.removeUser(user);
        return "redirect:/admin";
    }

}
