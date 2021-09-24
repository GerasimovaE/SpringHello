package com.example.crud_spring_bootv2.dao;

import com.example.crud_spring_bootv2.model.User;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User userNew) {
        entityManager.merge(userNew);
    }

    @Override
    public void removeUser(User user) {
        entityManager.createQuery("delete from User where id = :id")
                .setParameter("id", user.getId())
                .executeUpdate();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
       return entityManager.createQuery("select m from User m where m.email = :email", User.class)
               .setParameter("email", email)
               .getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {

        List list = entityManager.createQuery("select m from User m", User.class).getResultList();
        return (List<User>) list;
    }
}













