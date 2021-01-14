package core.basesyntax.dao.jdbc;

import core.basesyntax.dao.DriverDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Driver;
import core.basesyntax.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DriverDaoJdbcImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String queryInsert = "INSERT INTO drivers (driver_name, driver_licence_number)"
                + "VALUES (?, ?)";
        String querySelect = "SELECT * FROM drivers WHERE driver_name=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement insert = connection.prepareStatement(queryInsert);
            insert.setString(1, driver.getName());
            insert.setString(1, driver.getLicenceNumber());
            insert.execute();
            insert.close();
            PreparedStatement select = connection.prepareStatement(querySelect);
            select.setString(1, driver.getName());
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject("manufacturer_id", Long.class));
            }



        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert driver " + driver, e);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Driver> getAll() {
        return null;
    }

    @Override
    public Driver update(Driver driver) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
