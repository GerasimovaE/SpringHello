package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

       List<Car> cars = new ArrayList<>();
       User user1 = new User("User1", "Lastname1", "user1@mail.ru");
       Car car1 = new Car("Ford", 1);
       user1.setCar(car1);
       car1.setUser(user1);
       cars.add(car1);
       userService.add(user1);

       User user2 = new User("User2", "Lastname2", "user2@mail.ru");
       Car car2 = new Car("Ford2", 2);
       user2.setCar(car2);
       car2.setUser(user2);
       cars.add(car2);
       userService.add(user2);

       User user3 = new User("User3", "Lastname3", "user3@mail.ru");
       Car car3 = new Car("Ford3", 3);
       user3.setCar(car3);
       car3.setUser(user3);
       cars.add(car3);
       userService.add(user3);

       for (Car car : cars) {
           User userGet = userService.get(car.getModel(), car.getSeries());

           System.out.println("Id = "+userGet.getId());
           System.out.println("First Name = " + userGet.getFirstName());
           System.out.println("Last Name = " + userGet.getLastName());
           System.out.println("Email = " + userGet.getEmail());
           System.out.println("Car model = " + car.getModel());
           System.out.println("Car series = " + car.getSeries());
           System.out.println();
       }

      context.close();
   }
}
