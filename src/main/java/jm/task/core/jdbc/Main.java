package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Bib", "Lbib", (byte) 10);
        userService.saveUser("Bob", "Lbob", (byte) 15);
        userService.saveUser("Byb", "Lbyb", (byte) 20);
        userService.saveUser("Bab", "Lbab", (byte) 25);
        List<User> users = userService.getAllUsers();
        for (User user :
                users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
