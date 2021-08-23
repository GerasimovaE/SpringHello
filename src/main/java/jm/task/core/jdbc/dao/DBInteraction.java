package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBInteraction {

    private Connection connection;

    public DBInteraction() {
        this.connection = Util.DBConnection();
    }

    public boolean ExexcuteRequest(String SQL) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<User> ExexcuteRequestGet(String SQL) {

        List<User> arrayUsers = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

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
        }

        return arrayUsers;
    }

}
