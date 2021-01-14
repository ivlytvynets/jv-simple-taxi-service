package core.basesyntax.dao.jdbc;

import core.basesyntax.dao.ManufacturerDao;
import core.basesyntax.lib.Dao;
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
public class ManufacturerDaoJdbcImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String query = "INSERT INTO manufacturers "
                + "(name, country) "
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.execute();
            query = "SELECT * FROM manufacturers WHERE name=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, manufacturer.getName());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                manufacturer.setId(resultSet.getLong("manufacturer_id"));
            }

            return manufacturer;

        } catch (SQLException e) {
            throw new RuntimeException("Can't create manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE manufacturer_id=?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Manufacturer manufacturer = new Manufacturer();

            while (resultSet.next()) {
                manufacturer.setId(resultSet.getLong("manufacturer_id"));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getNString("country"));
            }

            return Optional.of(manufacturer);

        } catch (SQLException e) {
            throw new RuntimeException("Can't get manufacturer with id: " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Manufacturer> manufacturers = new ArrayList<>();

            while (resultSet.next()) {
                Manufacturer current = new Manufacturer();
                current.setId(resultSet.getLong("manufacturer_id"));
                current.setName(resultSet.getString("name"));
                current.setCountry(resultSet.getString("country"));
                manufacturers.add(current);
            }

            return manufacturers;

        } catch (SQLException e) {
            throw new RuntimeException("Can't get all manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name=?, country=? "
                + "WHERE manufacturer_id=?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();

            return manufacturer;

        } catch (SQLException e) {
            throw new RuntimeException("Can't update manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        //TODO: implement this method
        /*String query = "UPDATE manufacturers SET deleted=true WHERE manufacturer_id=?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Can't delete manufacturer with id: " + id, e);
        }*/
        return false;
    }
}
