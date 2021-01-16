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
        String queryInsert = "INSERT INTO cars (car_model, manufacturer_id) "
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
        String querySelect = "SELECT * FROM cars WHERE car_id=? AND deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement stSelectById = connection.prepareStatement(querySelect,
                        Statement.RETURN_GENERATED_KEYS)) {
            stSelectById.setLong(1, id);
            ResultSet resultSet = stSelectById.executeQuery();
            Car car = null;
            if (resultSet.next()) {
                car = getCarFromResultSet(resultSet, connection);
            }
            return Optional.of(car);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car with id " + id, e);
        }
    }

    @Override
    public List<Car> getAll() {
        String querySelect = "SELECT * FROM cars WHERE deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                Statement stSelectAll = connection.createStatement()) {
            ResultSet resultSet = stSelectAll.executeQuery(querySelect);
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(getCarFromResultSet(resultSet, connection));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all cars", e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String querySelectCars = "SELECT c.car_id, c.car_model, c.manufacturer_id "
                + "FROM cars c INNER JOIN cars_drivers cd ON cd.car_id=c.car_id "
                + "INNER JOIN drivers d ON cd.driver_id=d.driver_id "
                + "WHERE cd.driver_id=? AND c.deleted=FALSE AND d.deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement selectCars = connection.prepareStatement(querySelectCars)) {
            selectCars.setLong(1, driverId);
            ResultSet resultSet = selectCars.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (resultSet.next()) {
                cars.add(getCarFromResultSet(resultSet, connection));
            }
            return cars;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get cars by driver id " + driverId, e);
        }
    }

    @Override
    public Car update(Car car) {
        String queryUpdateManufacturer = "UPDATE cars SET car_model=? WHERE car_id=? "
                + "AND deleted=FALSE";
        String queryDeleteOldDrivers = "DELETE FROM cars_drivers WHERE car_id=?";
        String queryInsertNewDrivers = "INSERT INTO cars_drivers (driver_id, car_id)"
                + "VALUES(?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement stUpdateManufacturer = connection
                        .prepareStatement(queryUpdateManufacturer);
                    PreparedStatement stDeleteDrivers = connection
                            .prepareStatement(queryDeleteOldDrivers);
                        PreparedStatement stInsertDrivers = connection
                                .prepareStatement(queryInsertNewDrivers)) {
            stUpdateManufacturer.setString(1, car.getModel());
            stUpdateManufacturer.setLong(2, car.getId());
            stUpdateManufacturer.executeUpdate();
            stUpdateManufacturer.close();
            stDeleteDrivers.setLong(1, car.getId());
            stDeleteDrivers.executeUpdate();
            stDeleteDrivers.close();
            stInsertDrivers.setLong(2, car.getId());
            for (Driver driver : car.getDrivers()) {
                stInsertDrivers.setLong(1, driver.getId());
                stInsertDrivers.executeUpdate();
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update car " + car, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String queryDelete = "UPDATE cars SET deleted=TRUE WHERE car_id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement stDelete = connection.prepareStatement(queryDelete)) {
            stDelete.setLong(1, id);
            return stDelete.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car with id " + id, e);
        }
    }

    private Car getCarFromResultSet(ResultSet resultSet, Connection connection)
            throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getObject("car_id", Long.class));
        car.setModel(resultSet.getString("car_model"));
        car.setManufacturer(getManufacturerById(resultSet.getObject("manufacturer_id",
                Long.class)));
        String querySelectDrivers = "SELECT d.driver_id,"
                + "FROM drivers d INNER JOIN cars_drivers cd ON cd.driver_id=d.driver_id,"
                + "WHERE cd.car_id=?";
        try (PreparedStatement selectDrivers = connection
                .prepareStatement(querySelectDrivers)) {
            selectDrivers.setLong(1, car.getId());
            ResultSet resultSetDrivers = selectDrivers.executeQuery();
            List<Driver> drivers = new ArrayList<>();
            while (resultSetDrivers.next()) {
                Long currentDriverId = resultSetDrivers.getObject("driver_id", Long.class);
                drivers.add(getDriverById(currentDriverId));
            }
            car.setDrivers(drivers);

        } catch (SQLException e) {
            car.setDrivers(new ArrayList<>());
        }
        return car;
    }

    private Manufacturer getManufacturerById(Long id) {
        String querySelect = "SELECT * FROM manufacturers WHERE manufacturer_id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement stSelectById = connection.prepareStatement(querySelect)) {
            stSelectById.setLong(1, id);
            ResultSet resultSet = stSelectById.executeQuery();
            Manufacturer manufacturer = new Manufacturer();
            if (resultSet.next()) {
                manufacturer.setId(id);
                manufacturer.setName(resultSet.getString("manufacturer_name"));
                manufacturer.setCountry(resultSet.getString("manufacturer_country"));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer with id " + id, e);
        }
    }

    private Driver getDriverById(Long id) {
        String querySelect = "SELECT * FROM drivers WHERE driver_id=? AND deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement select = connection.prepareStatement(querySelect)) {
            select.setLong(1, id);
            ResultSet resultSet = select.executeQuery();
            Driver driver = new Driver();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject("driver_id", Long.class));
                driver.setName(resultSet.getString("driver_name"));
                driver.setLicenceNumber(resultSet.getString("driver_licence_number"));
            }
            return driver;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get driver with id " + id, e);
        }
    }
}
