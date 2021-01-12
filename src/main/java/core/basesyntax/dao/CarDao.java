package core.basesyntax.dao;

import core.basesyntax.model.Car;
import java.util.List;
import java.util.Optional;

public interface CarDao {
    Car create(Car car);

    Optional<Car> get(Long id);

    List<Car> getAll();

    List<Car> getAllByDriver(Long driverId);

    Car update(Car car);

    boolean delete(Long id);
}
