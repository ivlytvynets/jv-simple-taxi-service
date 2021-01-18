package core.basesyntax.dao.jdbc;

import core.basesyntax.dao.ManufacturerDao;
import core.basesyntax.exception.DataProcessingException;
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
        String queryInsert = "INSERT INTO manufacturers "
                + "(name, country) "
                + "VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement stInsert = connection.prepareStatement(queryInsert,
                         Statement.RETURN_GENERATED_KEYS)) {
            stInsert.setString(1, manufacturer.getName());
            stInsert.setString(2, manufacturer.getCountry());
            stInsert.execute();
            ResultSet resultSet = stInsert.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject("GENERATED_KEY", Long.class));
            }
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);
        }
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        String query = "SELECT * FROM manufacturers WHERE id=? AND deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement stSelectById = connection.prepareStatement(query)) {
            stSelectById.setLong(1, id);
            ResultSet resultSet = stSelectById.executeQuery();
            Manufacturer manufacturer = null;
            if (resultSet.next()) {
                manufacturer = getManufacturerFromResultSet(resultSet);
            }
            return Optional.of(manufacturer);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get manufacturer with id " + id, e);
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        String query = "SELECT * FROM manufacturers WHERE deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement stSelectAll = connection.prepareStatement(query)) {
            ResultSet resultSet = stSelectAll.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();
            while (resultSet.next()) {
                manufacturers.add(getManufacturerFromResultSet(resultSet));
            }
            return manufacturers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all manufacturers", e);
        }
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name=?, country=? "
                + "WHERE id=? AND deleted=FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement stUpdate = connection.prepareStatement(query)) {
            stUpdate.setString(1, manufacturer.getName());
            stUpdate.setString(2, manufacturer.getCountry());
            stUpdate.setLong(3, manufacturer.getId());
            stUpdate.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update manufacturer " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE manufacturers SET deleted=TRUE WHERE id=?";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement stDelete = connection.prepareStatement(query)) {
            stDelete.setLong(1, id);
            return stDelete.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete manufacturer with id " + id, e);
        }
    }

    private Manufacturer getManufacturerFromResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getObject("id", Long.class));
        manufacturer.setName(resultSet.getObject("name", String.class));
        manufacturer.setCountry(resultSet.getObject("country", String.class));
        return manufacturer;
    }
}
