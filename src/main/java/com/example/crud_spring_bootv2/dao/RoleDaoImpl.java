package com.example.crud_spring_bootv2.dao;

import com.example.crud_spring_bootv2.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> getAllRoles() {
        List list = entityManager.createQuery("select m from Role m", Role.class).getResultList();
        return (List<Role>) list;
    }

    @Override
    public Role getRoleById(long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public void update(Role newRole) {
        entityManager.merge(newRole);
    }

    @Override
    public void removeRole(Role role) {
        entityManager.createQuery("delete from Role where id = :id").setParameter("id", role.getId()).executeUpdate();
    }

}
