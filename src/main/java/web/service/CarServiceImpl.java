package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.CarDao;
import web.dao.CarDaoImpl;
import web.model.Car;
import java.util.List;

@Component
public class CarServiceImpl implements CarService{

    private CarDao carDao;
    @Autowired
    public CarServiceImpl(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public List<Car> getCar(int count) {
        return carDao.getCar(count);
    }

    @Override
    public List<Car> getAllCar() {
        return carDao.getAllCar();
    }
}
