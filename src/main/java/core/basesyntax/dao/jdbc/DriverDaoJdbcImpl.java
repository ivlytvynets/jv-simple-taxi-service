package core.basesyntax.dao.jdbc;

import core.basesyntax.dao.DriverDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Driver;
import core.basesyntax.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class DriverDaoJdbcImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        String queryInsert = "INSERT INTO drivers (name, licence_number)"
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insert = connection.prepareStatement(queryInsert,
                        Statement.RETURN_GENERATED_KEYS)) {
            insert.setString(1, driver.getName());
            insert.setString(2, driver.getLicenceNumber());
            insert.execute();
            ResultSet resultSet = insert.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject("GENERATED_KEY", Long.class));
            }
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert driver " + driver, e);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        String querySelect = "SELECT * FROM drivers WHERE id=? AND deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement select = connection.prepareStatement(querySelect)) {
            select.setLong(1, id);
            ResultSet resultSet = select.executeQuery();
            Driver driver = null;
            if (resultSet.next()) {
                driver = getDriverFromResultSet(resultSet);
            }
            return Optional.of(driver);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver with id " + id, e);
        }
    }

    @Override
    public List<Driver> getAll() {
        String querySelect = "SELECT * FROM drivers WHERE deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement select = connection.prepareStatement(querySelect)) {
            ResultSet resultSet = select.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(getDriverFromResultSet(resultSet));
            }
            return drivers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all drivers", e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        String queryUpdate = "UPDATE drivers SET name=?, licence_number=? "
                + "WHERE id=? AND deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement select = connection.prepareStatement(queryUpdate);) {
            select.setString(1, driver.getName());
            select.setString(2, driver.getLicenceNumber());
            select.setLong(3, driver.getId());
            select.executeUpdate();
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update driver " + driver, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String queryDelete = "UPDATE drivers SET deleted=TRUE WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement delete = connection.prepareStatement(queryDelete)) {
            delete.setLong(1, id);
            int updatedRows = delete.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete driver with id " + id, e);
        }
    }

    private Driver getDriverFromResultSet(ResultSet resultSet) throws SQLException {
        Driver driver = new Driver();
        driver.setId(resultSet.getObject("id", Long.class));
        driver.setName(resultSet.getString("name"));
        driver.setLicenceNumber(resultSet.getString("licence_number"));
        return driver;
    }
}
