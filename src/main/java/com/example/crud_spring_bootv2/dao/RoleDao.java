package com.example.crud_spring_bootv2.dao;


import com.example.crud_spring_bootv2.model.Role;

import java.util.List;

public interface RoleDao {

    void saveRole(Role role);

    List<Role> getAllRoles();

    Role getRoleById(long id);

    void update(Role role);

    void removeRole(Role role);

}
