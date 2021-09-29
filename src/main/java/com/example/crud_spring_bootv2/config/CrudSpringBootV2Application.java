package com.example.crud_spring_bootv2.config;

import com.example.crud_spring_bootv2.controller.AdminController;
import com.example.crud_spring_bootv2.dao.UserDaoImpl;
import com.example.crud_spring_bootv2.handler.LoginSuccessHandler;
import com.example.crud_spring_bootv2.model.User;
import com.example.crud_spring_bootv2.service.UserDetailsServiceImpl;
import com.example.crud_spring_bootv2.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackageClasses = {AdminController.class, UserServiceImpl.class, UserDaoImpl.class, LoginSuccessHandler.class, SecurityConfig.class})
@EntityScan(basePackageClasses = User.class)
public class CrudSpringBootV2Application {

    public static void main(String[] args) {
        SpringApplication.run(CrudSpringBootV2Application.class, args);
    }

}
