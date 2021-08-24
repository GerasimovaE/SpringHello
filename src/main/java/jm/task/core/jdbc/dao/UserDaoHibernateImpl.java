package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {

        String SQL = "CREATE TABLE IF NOT EXISTS users " +
                "(user_id bigint primary key auto_increment, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR (50), " +
                "age tinyint)";

        try (Session session = Util.getSessionFactory().openSession();) {
            session.beginTransaction();
            session.createSQLQuery(SQL).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table Users successfully created...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String SQL = "DROP TABLE IF EXISTS users;";
            session.createSQLQuery(SQL).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table Users successfully drop...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User with name " + name + " " + lastName + " save in database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            session.remove(user);
            System.out.println("Table Users successfully remove user with id = " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> arrayUsers = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            arrayUsers = session.createQuery("SELECT a FROM User a", User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayUsers;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table Users successfully clean...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
