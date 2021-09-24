package com.example.crud_spring_bootv2.controller;

import com.example.crud_spring_bootv2.model.Role;
import com.example.crud_spring_bootv2.model.User;
import com.example.crud_spring_bootv2.service.RoleService;
import com.example.crud_spring_bootv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
    public String index(@CurrentSecurityContext(expression = "authentication.principal") User principal, Model model){
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());

        model.addAttribute("showUserProfile",
                model.containsAttribute("user") && !((User) model.getAttribute("user")).isNew());

        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }

        model.addAttribute("userEmail", principal.getUsername());
        model.addAttribute("userRoles", principal.getRoleAllString());

        return "admin";
    }

    //SHOW
    @GetMapping("/users/{id}")
    public String showUserForAdmin(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.getUserById(id));
        return "showUserForAdmin";
    }

    @PostMapping("/users/user")
    public String create(@ModelAttribute("user") User user){
        сompleteRoles(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    //EDIT
    @GetMapping("/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/modal/userForm";
    }

    //UPDATE
    @PostMapping("/users/{id}")
    public String update(@ModelAttribute("user") User user){
        сompleteRoles(user);
        userService.update(user);
        return "redirect:/admin";
    }

    //DELETE
    @DeleteMapping("/users/{id}")
    public String delete(@ModelAttribute("user") User user) {
        userService.removeUser(user);
        return "redirect:/admin";
    }

    private void сompleteRoles(User user){

        List<Role> likedUser = new ArrayList<>();
        try {
            for (Role role: user.getLikedUser()) {
                likedUser.add(roleService.getRoleById(Integer.parseInt(role.getName())));
            }
        }catch (Exception e){
            //ignore
        }
        user.setLikedUser(likedUser);

    }

}
