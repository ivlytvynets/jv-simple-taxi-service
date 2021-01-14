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
        String queryInsert = "INSERT INTO drivers (driver_name, driver_licence_number)"
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement insert = connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, driver.getName());
            insert.setString(2, driver.getLicenceNumber());
            insert.execute();
            ResultSet resultSet = insert.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class)); //TODO: replace 1
            }
            insert.close();
            connection.close();

            return driver;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert driver " + driver, e);
        }
    }

    @Override
    public Optional<Driver> get(Long id) {
        String querySelect = "SELECT * FROM drivers WHERE driver_id=? AND deleted=FALSE";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement select = connection.prepareStatement(querySelect);
            select.setLong(1, id);
            ResultSet resultSet = select.executeQuery();
            Driver driver = new Driver();
            if (resultSet.next()) {
                driver = getDriverFromResultSet(resultSet);
            }

            select.close();
            connection.close();

            return Optional.ofNullable(driver);

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver with id " + id, e);
        }
    }

    @Override
    public List<Driver> getAll() {
        String querySelect = "SELECT * FROM drivers WHERE deleted=FALSE";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement select = connection.createStatement();
            ResultSet resultSet = select.executeQuery(querySelect);
            List<Driver> drivers = new ArrayList<>();
            while (resultSet.next()) {
                drivers.add(getDriverFromResultSet(resultSet));
            }

            select.close();
            connection.close();

            return drivers;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all drivers", e);
        }
    }

    @Override
    public Driver update(Driver driver) {
        String queryUpdate = "UPDATE drivers SET driver_name=?, driver_licence_number=? "
                + "WHERE driver_id=? AND deleted=FALSE";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement select = connection.prepareStatement(queryUpdate);
            select.setString(1, driver.getName());
            select.setString(2, driver.getLicenceNumber());
            select.setLong(3, driver.getId());
            select.executeUpdate();

            select.close();
            connection.close();

            return driver;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't update driver " + driver, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String queryDelete = "UPDATE drivers SET deleted=TRUE WHERE driver_id=?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement delete = connection.prepareStatement(queryDelete);
            delete.setLong(1, id);
            int updatedRows = delete.executeUpdate();

            delete.close();
            connection.close();

            return updatedRows > 0;

        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete driver with id " + id, e);
        }
    }

    private Driver getDriverFromResultSet(ResultSet resultSet) throws SQLException {
        Driver driver = new Driver();
        driver.setId(resultSet.getObject("driver_id", Long.class));
        driver.setName(resultSet.getString("driver_name"));
        driver.setLicenceNumber(resultSet.getString("driver_licence_number"));
        return driver;
    }
}
