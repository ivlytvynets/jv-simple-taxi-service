package core.basesyntax.service;

import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import java.util.List;

public interface CarService {
    Car create(Car car);

    Car get(Long id);

    List<Car> getAll();

    List<Car> getAllByDriver(Long driverId);

    Car update(Car car);

    boolean delete(Long id);

    void addDriverToCar(Driver driver, Car car);

    void removeDriverFromCar(Driver driver, Car car);
}
