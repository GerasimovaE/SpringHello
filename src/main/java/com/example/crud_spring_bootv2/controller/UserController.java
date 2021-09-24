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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    public UserController(UserService userService, RoleService roleService) {
    }

    @GetMapping("")
    public String showUserInfo(@CurrentSecurityContext(expression = "authentication.principal") User principal, Model model) {

        model.addAttribute("user", principal);
        model.addAttribute("userEmail", principal.getUsername());
        model.addAttribute("userRoles", principal.getRoleAllString());

        return "fragments/userInfo";
    }

}
