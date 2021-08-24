package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String HOST = "jdbc:mysql://localhost:3306/mydbtest?useSSL=false";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static Connection connection = null;

    //Hibernate
    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    static {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

        standardServiceRegistry = registryBuilder.applySetting(Environment.URL, "jdbc:mysql://localhost:3306/mydbtest?useSSL=false")
                .applySetting(Environment.USER, "admin")
                .applySetting(Environment.PASS, "admin")
                .applySetting(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect")
                .build();

        MetadataSources sources = new MetadataSources(standardServiceRegistry);
        sources.addAnnotatedClass(User.class);
        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    //JDBC
    public static Connection DBConnection() {

        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
//            System.out.println("Соединение с базой данных установлено!");
        } catch (SQLException e) {
            System.out.println("У нас проблемы с подключением к базе данных");
        }

        return connection;
    }

}
