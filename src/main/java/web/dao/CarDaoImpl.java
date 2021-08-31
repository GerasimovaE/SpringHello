package web.dao;

import org.springframework.stereotype.Component;
import web.model.Car;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarDaoImpl implements CarDao {

    private static List<Car> list;

    static {
        list = new ArrayList<>();

        list.add(new Car("Ford", 123, Color.BLACK));
        list.add(new Car("Ferrari", 456, Color.WHITE));
        list.add(new Car("DS", 789, Color.BLUE));
        list.add(new Car("Iveco", 056, Color.GREEN));
        list.add(new Car("Nissan", 987, Color.DARK_GRAY));
    }

    public List<Car> getCar(int count) {return  list.stream().limit(count).collect(Collectors.toList());}

    @Override
    public List<Car> getAllCar() {return list;}
}
