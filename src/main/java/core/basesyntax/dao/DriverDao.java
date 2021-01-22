package core.basesyntax.dao;

import core.basesyntax.model.Driver;
import java.util.Optional;

public interface DriverDao extends GenericDao<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
