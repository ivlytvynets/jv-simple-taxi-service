package core.basesyntax.dao.jdbc;

import core.basesyntax.dao.ManufacturerDao;
import core.basesyntax.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public class ManufacturerDaoJdbc implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        return null;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
