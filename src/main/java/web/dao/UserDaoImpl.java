package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        User user1 = entityManager.find(User.class, user.getId());
        entityManager.remove(user1);
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {

        List list = entityManager.createQuery("select m from User m", User.class).getResultList();
        return (List<User>) list;
    }
}













