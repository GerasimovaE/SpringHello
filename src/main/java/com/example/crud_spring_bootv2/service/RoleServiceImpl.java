package com.example.crud_spring_bootv2.service;

import com.example.crud_spring_bootv2.dao.RoleDao;
import com.example.crud_spring_bootv2.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleById(long id) {
        return roleDao.getRoleById(id);
    }

    @Transactional
    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Transactional
    @Override
    public void removeRole(Role role) {
       roleDao.removeRole(role);
    }
}
