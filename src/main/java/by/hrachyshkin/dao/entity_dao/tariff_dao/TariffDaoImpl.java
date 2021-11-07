package by.hrachyshkin.dao.entity_dao.tariff_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Tariff;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TariffDaoImpl implements TariffDao {

    private static final String EXISTS_BY_ID_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM tariffs " +
                    "WHERE id = ?" +
                    ")";

    private static final String EXISTS_BY_NAME_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM tariffs " +
                    "WHERE name = ?" +
                    ")";

    private static final String FIND_QUERY =
            "SELECT id, name, type, speed, price " +
                    "FROM tariffs ";

    private static final String FIND_AND_SORT_BY_SPEED_AND_PRICE_QUERY =
            "SELECT id, name, type, speed, price " +
                    "FROM tariffs " +
                    "ORDER BY speed DESC, price ACS";

    private static final String FIND_AND_FILTER_BY_TYPE_QUERY =
            "SELECT id, name, type, speed, price " +
                    "FROM tariffs " +
                    "WHERE type = ? ";

    private static final String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT id, name, type, speed, price " +
                    "FROM tariffs " +
                    "WHERE type = ? " +
                    "ORDER speed DESC, price ACS ";

    private static final String FIND_ONE_TARIFF_QUERY_BY_ID =
            "SELECT id, name, type, speed, price " +
                    "FROM tariffs " +
                    "WHERE id = ?";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO tariffs (name, type, speed, price) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String UPDATE_QUERY =
            "INSERT INTO tariffs (name, type, speed, price) " +
                    "VALUES ?, ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_QUERY =
            "DELETE " +
                    "FROM tariffs " +
                    "WHERE id = ?";

    private final Connection connection;

    public TariffDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isExistById(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID_QUERY)) {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find tariff by id", e);
        }
    }

    @Override
    public boolean isExistByName(final String name) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_NAME_QUERY)) {
            statement.setString(1, name);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find tariff by name", e);
        }
    }

    @Override
    public List<Tariff> find() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {
            final List<Tariff> tariffs = new ArrayList<>();
            while (resultSet.next()) {
                final Tariff tariff = new Tariff(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        Tariff.Type.values()[resultSet.getInt(3)],
                        resultSet.getInt(4),
                        resultSet.getFloat(5));
                tariffs.add(tariff);
            }

            return tariffs;
        } catch (Exception e) {
            throw new DaoException("Can't find required tariffs");
        }
    }

    @Override
    public List<Tariff> findAndSortBySpeedAndPrice() throws DaoException {

            try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_BY_SPEED_AND_PRICE_QUERY);
                 final ResultSet resultSet = statement.executeQuery()) {
                final List<Tariff> tariffs = new ArrayList<>();
                while (resultSet.next()) {
                    final Tariff tariff = new Tariff(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            Tariff.Type.values()[resultSet.getInt(3)],
                            resultSet.getInt(4),
                            resultSet.getFloat(5));
                    tariffs.add(tariff);
                }

                return tariffs;
            } catch (Exception e) {
                throw new DaoException("Can't find required tariffs");
            }
    }

    @Override
    public List<Tariff> findAndFilterByType(final Tariff.Type type) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_BY_TYPE_QUERY)) {
            statement.setInt(1, type.ordinal());

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Tariff> tariffs = new ArrayList<>();
                while (resultSet.next()) {
                    final Tariff tariff = new Tariff(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            Tariff.Type.values()[resultSet.getInt(3)],
                            resultSet.getInt(4),
                            resultSet.getFloat(5));
                    tariffs.add(tariff);
                }
                return tariffs;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find or filter tariffs");
        }
    }

    @Override
    public List<Tariff> findAndFilterAndSort(final Tariff.Type type) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_AND_SORT_QUERY)) {
            statement.setInt(1, type.ordinal());

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Tariff> tariffs = new ArrayList<>();
                while (resultSet.next()) {
                    final Tariff tariff = new Tariff(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            Tariff.Type.values()[resultSet.getInt(3)],
                            resultSet.getInt(4),
                            resultSet.getFloat(5));
                    tariffs.add(tariff);
                }
                return tariffs;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find or filter or sort tariffs");
        }
    }

    @Override
    public Tariff findOneById(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_ONE_TARIFF_QUERY_BY_ID)) {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return new Tariff(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        Tariff.Type.values()[resultSet.getInt(3)],
                        resultSet.getInt(4),
                        resultSet.getFloat(5));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find tariff by id", e);
        }
    }

    @Override
    public void add(final Tariff tariff) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {
            statement.setString(1, tariff.getName());
            statement.setInt(2, tariff.getType().ordinal());
            statement.setInt(3, tariff.getSpeed());
            statement.setDouble(4, tariff.getPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't create tariff", e);
        }
    }

    @Override
    public void updateStatus(final Tariff tariff) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, tariff.getName());
            statement.setInt(2, tariff.getType().ordinal());
            statement.setInt(3, tariff.getSpeed());
            statement.setDouble(4, tariff.getPrice());

            statement.setInt(5, tariff.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't add tariff", e);
        }
    }

    @Override
    public void delete(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Can't delete tariff", e);
        }
    }
}
