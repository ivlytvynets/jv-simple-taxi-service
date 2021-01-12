package core.basesyntax.service.impl;

import core.basesyntax.dao.CarDao;
import core.basesyntax.exception.NullValueException;
import core.basesyntax.lib.Inject;
import core.basesyntax.lib.Service;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.service.CarService;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        return carDao.get(id).get();
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return carDao.getAllByDriver(driverId);
    }

    @Override
    public Car update(Car car) {
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        if (car.getId() == null) {
            carDao.create(car);
        }
        List<Driver> drivers = car.getDrivers();
        drivers.add(driver);
        car.setDrivers(drivers);
        carDao.update(car);
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        if (car == null) {
            throw new NullValueException("You can't remove driver because car is null");
        }
        car.getDrivers().remove(driver);
        carDao.update(car);
    }
}
