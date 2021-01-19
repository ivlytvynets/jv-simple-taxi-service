package core.basesyntax.dao.jdbc;

import core.basesyntax.dao.CarDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.lib.Dao;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.model.Manufacturer;
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
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String queryInsert = "INSERT INTO cars (model, manufacturer_id) "
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement stInsert = connection.prepareStatement(queryInsert,
                        Statement.RETURN_GENERATED_KEYS)) {
            stInsert.setString(1, car.getModel());
            stInsert.setLong(2, car.getManufacturer().getId());
            stInsert.execute();
            ResultSet resultSet = stInsert.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject("GENERATED_KEY", Long.class));
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert car " + car, e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String querySelect = "SELECT c.id, c.model, c.manufacturer_id, m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m ON m.id=c.manufacturer_id "
                + "WHERE c.deleted=FALSE AND c.id=?";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement stSelectById = connection.prepareStatement(querySelect)) {
            stSelectById.setLong(1, id);
            ResultSet resultSet = stSelectById.executeQuery();
            if (resultSet.next()) {
                car = getCarFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car with id " + id, e);
        }
        if (car != null) {
            car.setDrivers(getDriversForCar(car.getId()));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        String querySelect = "SELECT c.id, c.model, c.manufacturer_id, m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN manufacturers m ON m.id=c.manufacturer_id "
                + "WHERE c.deleted=FALSE";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement stSelectAll = connection.prepareStatement(querySelect)) {
            ResultSet resultSet = stSelectAll.executeQuery();
            while (resultSet.next()) {
                cars.add(getCarFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
        for (Car car : cars) {
            car.setDrivers(getDriversForCar(car.getId()));
        }
        return cars;
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String querySelectCars = "SELECT c.id, c.model, c.manufacturer_id, m.name, m.country "
                + "FROM cars c "
                + "INNER JOIN cars_drivers cd ON cd.car_id=c.id "
                + "INNER JOIN manufacturers m ON m.id=c.manufacturer_id "
                + "WHERE cd.driver_id=? AND c.deleted=FALSE";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectCars = connection.prepareStatement(querySelectCars)) {
            selectCars.setLong(1, driverId);
            ResultSet resultSet = selectCars.executeQuery();
            while (resultSet.next()) {
                cars.add(getCarFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get cars by driver id " + driverId, e);
        }
        for (Car car : cars) {
            car.setDrivers(getDriversForCar(car.getId()));
        }
        return cars;
    }

    @Override
    public Car update(Car car) {
        String queryUpdateCar = "UPDATE cars SET model=?, manufacturer_id=? WHERE id=? "
                + "AND deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement stUpdateCar = connection
                        .prepareStatement(queryUpdateCar)) {
            stUpdateCar.setString(1, car.getModel());
            stUpdateCar.setLong(2, car.getManufacturer().getId());
            stUpdateCar.setLong(3, car.getId());
            stUpdateCar.executeUpdate();
            stUpdateCar.close();
            deleteOldDrivers(car, connection);
            insertNewDrivers(car, connection);
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car " + car, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String queryDelete = "UPDATE cars SET deleted=TRUE WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement stDelete = connection.prepareStatement(queryDelete)) {
            stDelete.setLong(1, id);
            return stDelete.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car with id " + id, e);
        }
    }

    private void deleteOldDrivers(Car car, Connection connection) throws SQLException {
        String queryDeleteOldDrivers = "DELETE FROM cars_drivers WHERE car_id=?";
        try (PreparedStatement stDeleteDrivers = connection
                .prepareStatement(queryDeleteOldDrivers)) {
            stDeleteDrivers.setLong(1, car.getId());
            stDeleteDrivers.executeUpdate();
        }
    }

    private void insertNewDrivers(Car car, Connection connection) throws SQLException {
        String queryInsertNewDrivers = "INSERT INTO cars_drivers (driver_id, car_id)"
                + "VALUES(?, ?)";
        try (PreparedStatement stInsertDrivers = connection
                .prepareStatement(queryInsertNewDrivers)) {
            stInsertDrivers.setLong(2, car.getId());
            for (Driver driver : car.getDrivers()) {
                stInsertDrivers.setLong(1, driver.getId());
                stInsertDrivers.executeUpdate();
            }
        }
    }

    private Car getCarFromResultSet(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getObject("id", Long.class));
        car.setModel(resultSet.getString("model"));
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("manufacturer_id",
                Long.class));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        car.setManufacturer(manufacturer);
        return car;
    }

    private List<Driver> getDriversForCar(Long carId) {
        String querySelectDrivers = "SELECT cd.driver_id, d.name, d.licence_number "
                + "FROM cars_drivers cd "
                + "INNER JOIN drivers d ON d.id=cd.driver_id "
                + "WHERE cd.car_id=? AND d.deleted=FALSE";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement stSelectDrivers = connection
                        .prepareStatement(querySelectDrivers)) {
            stSelectDrivers.setLong(1, carId);
            ResultSet resultSet = stSelectDrivers.executeQuery();
            while (resultSet.next()) {
                Driver driver = new Driver();
                driver.setId(resultSet.getObject("driver_id", Long.class));
                driver.setName(resultSet.getString("name"));
                driver.setLicenceNumber(resultSet.getString("licence_number"));
                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get drivers by carId " + carId, e);
        }
        return drivers;
    }
}
