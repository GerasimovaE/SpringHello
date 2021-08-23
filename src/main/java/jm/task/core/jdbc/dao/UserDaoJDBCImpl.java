package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    private Connection connection = null;

    public UserDaoJDBCImpl() {

    }

    private boolean connectDataBase(String SQL) {

        connection = Util.DBConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS users " +
                "(user_id int primary key auto_increment, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR (50), " +
                "age tinyint)";

        if (connectDataBase(SQL)){
            System.out.println("Table Users successfully created...");
        }
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE IF EXISTS users;";
        if (connectDataBase(SQL)){
            System.out.println("Table Users successfully drop...");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = String.format("INSERT INTO users VALUES(NULL,'%s','%s',%d);", name, lastName, age);
        if (connectDataBase(SQL)) {
            System.out.println("User with name " + name + " " + lastName + " save in database");
        }
    }

    public void removeUserById(long id) {
        String SQL = String.format("DELETE FROM users WHERE ('user_id' = '%d');", id);
        if (connectDataBase(SQL)){
            System.out.println("Table Users successfully remove user with id = " + id);
        }
    }

    public List<User> getAllUsers() {

        List<User> arrayUsers = new ArrayList<>();
        String SQL = "SELECT * FROM users";
        connection = Util.DBConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery(SQL);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);

                User user = new User(name, lastName, age);
                user.setId((long) id);

                arrayUsers.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return arrayUsers;
    }

    public void cleanUsersTable() {
        String SQL = "TRUNCATE TABLE users;";
        if (connectDataBase(SQL)){
            System.out.println("Table Users successfully clean...");
        }
    }
}
