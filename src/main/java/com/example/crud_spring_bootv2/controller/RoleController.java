package com.example.crud_spring_bootv2.controller;

import com.example.crud_spring_bootv2.model.Role;
import com.example.crud_spring_bootv2.service.RoleService;
import com.example.crud_spring_bootv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class RoleController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RoleController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //INDEX
    @GetMapping("/roles")
    public String indexRole(Model model){
        model.addAttribute("roles", roleService.getAllRoles());
        return "adminRole";
    }

    //SHOW
    @GetMapping("/role/{id}")
    public String showRole(@PathVariable("id") long id, Model model){
        model.addAttribute("role", roleService.getRoleById(id));
        return "/showRole";
    }

    //NEW
    @GetMapping("/newRole")
    public String newRole(@ModelAttribute("role") Role role){
        return "/newRole";
    }

    @PostMapping("/role")
    public String createRole(@ModelAttribute("role") Role role){
        role.setName("ROLE_" + role.getName());
        roleService.saveRole(role);
        return "redirect:/admin/roles";
    }

    //EDIT
    @GetMapping("/roles/{id}/edit")
    public String editRole(Model model, @PathVariable("id") long id){
        model.addAttribute("role", roleService.getRoleById(id));
        return "/editRole";
    }

    //UPDATE
    @PostMapping("/roles/{id}")
    public String update(@ModelAttribute("role") Role role){
        roleService.update(role);
        return "redirect:/admin/roles";
    }

    //DELETE
    @DeleteMapping("/roles/{id}")
    public String delete(@ModelAttribute("role") Role role) {
        roleService.removeRole(role);
        return "redirect:/admin/roles";
    }

}
