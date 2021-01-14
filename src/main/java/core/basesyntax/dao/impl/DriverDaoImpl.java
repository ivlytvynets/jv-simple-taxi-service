package core.basesyntax.dao.impl;

import core.basesyntax.dao.DriverDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Driver;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class DriverDaoImpl implements DriverDao {

    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return driver;
    }

    @Override
    public Optional<Driver> get(Long id) {
        return Storage.drivers
                .stream()
                .filter(driver -> driver.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Driver> getAll() {
        return Storage.drivers;
    }

    @Override
    public Driver update(Driver driver) {
        IntStream.range(0, Storage.drivers.size())
                .filter(i -> Storage.drivers.get(i).getId().equals(driver.getId()))
                .findFirst()
                .ifPresent(i -> Storage.drivers.set(i, driver));
        return driver;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.drivers.removeIf(driver -> driver.getId().equals(id));
    }
}
