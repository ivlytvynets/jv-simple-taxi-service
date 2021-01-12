package core.basesyntax.dao.impl;

import core.basesyntax.dao.ManufacturerDao;
import core.basesyntax.db.Storage;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Manufacturer;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        List<Manufacturer> manufacturers = Storage.manufacturers;
        for (Manufacturer manufacturer : manufacturers) {
            if (manufacturer.getId() == id) {
                return Optional.of(manufacturer);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        List<Manufacturer> manufacturers = Storage.manufacturers;
        for (Manufacturer object : manufacturers) {
            if (object.getId() == manufacturer.getId()) {
                Storage.manufacturers.set(Math.toIntExact(object.getId() - 1L), manufacturer);
                return manufacturer;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        List<Manufacturer> manufacturers = Storage.manufacturers;
        for (int i = 0; i < manufacturers.size(); i++) {
            if (manufacturers.get(i).getId() == id) {
                Storage.manufacturers.remove(Math.toIntExact(i));
                return true;
            }
        }
        return false;
    }
}
