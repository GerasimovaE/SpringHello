package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS users " +
                "(user_id bigint primary key auto_increment, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR (50), " +
                "age tinyint)";

        if (new DataBase().ExexcuteRequestStatement(SQL)) {
            System.out.println("Table Users successfully created...");
        }
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE IF EXISTS users;";
        if (new DataBase().ExexcuteRequestStatement(SQL)) {
            System.out.println("Table Users successfully drop...");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO users VALUES(NULL, ?, ?, ?);";
        if (new DataBase().saveUser(SQL, name, lastName, age)) {
            System.out.println("User with name " + name + " " + lastName + " save in database");
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM users WHERE ('user_id' = ?);";
        if (new DataBase().removeUserById(SQL, id)) {
            System.out.println("Table Users successfully remove user with id = " + id);
        }
    }

    public void cleanUsersTable() {
        String SQL = "TRUNCATE TABLE users;";
        if (new DataBase().ExexcuteRequestStatement(SQL)) {
            System.out.println("Table Users successfully clean...");
        }
    }

    public List<User> getAllUsers() {

        String SQL = "SELECT * FROM users";
        return new DataBase().getAllUsers(SQL);
    }

}
