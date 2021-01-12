package core.basesyntax.dao.impl;

import core.basesyntax.dao.CarDao;
import core.basesyntax.db.Storage;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Dao
public class CarDaoImpl implements CarDao {
    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        return Storage.cars
                .stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return Storage.cars
                .stream()
                .filter(car -> car.getDrivers()
                            .stream()
                            .map(Driver::getId)
                            .collect(Collectors.toList())
                            .contains(driverId))
                .collect(Collectors.toList());
    }

    @Override
    public Car update(Car car) {
        IntStream.range(0, Storage.cars.size())
                .filter(i -> Storage.cars.get(i).getId().equals(car.getId()))
                .findFirst()
                .ifPresent(i -> Storage.cars.set(i, car));
        return car;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars.removeIf(car -> car.getId().equals(id));
    }
}
