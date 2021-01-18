package core.basesyntax.service;

import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import java.util.List;

public interface CarService extends GenericService<Car, Long> {
    List<Car> getAllByDriver(Long driverId);

    void addDriverToCar(Driver driver, Car car);

    void removeDriverFromCar(Driver driver, Car car);
}
