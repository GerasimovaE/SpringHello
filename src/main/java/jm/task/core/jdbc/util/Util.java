package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String HOST = "jdbc:mysql://localhost:3306/mydbtest?useSSL=false";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static Connection connection = null;

    public Connection DBConnection() {

        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
//            System.out.println("Соединение с базой данных установлено!");
        } catch (SQLException e) {
            System.out.println("У нас проблемы с подключением к базе данных");
        }

        return connection;
    }

}
