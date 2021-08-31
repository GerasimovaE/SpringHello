package web.dao;

import web.model.Car;

import java.util.List;

public interface CarDao {

    List<Car> getCar(int count);

    public List<Car> getAllCar();

}
