package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

//   @Autowired
//   private SessionFactory sessionFactory;

   @Autowired
   private EntityManagerFactory entityManagerFactory;

   @Override
   public void add(User user) {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      entityManager.persist(user);
      entityManager.getTransaction().commit();
//      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public User get(String model, int series) {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      Query query = entityManager.createQuery("select m from Car m where m.model = :model and m.series = :series");
      query.setParameter("model", model);
      query.setParameter("series", series);
      List<Car> list = query.getResultList();

      return list.get(0).getUser();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
      List list = entityManager.createQuery("select m from User m", User.class).getResultList();

      return (List<User>) list;
//      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
//      return query.getResultList();
   }

}
