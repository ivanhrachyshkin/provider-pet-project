package by.hrachyshkin.dao.entity_dao.traffic_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.pool.PooledConnection;
import by.hrachyshkin.entity.Traffic;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrafficDaoImpl  implements TrafficDao {


    private static final String EXISTS_BY_SUBSCRIPTION_ID_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM traffics " +
                    "WHERE subscription_id = ? " +
                    ")";

    private static final String FIND_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM traffics ";

    private static final String FIND_AND_SORT_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM traffics " +
                    "ORDER BY ? ? ";

    private static final String FIND_AND_FILTER_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM traffics " +
                    "WHERE ? LIKE ? ";

    private static final String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM traffics " +
                    "WHERE ? LIKE ? " +
                    "ORDER BY ? ? ";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO traffics (email, password, role, name, phone, address, balance) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final Connection connection;

    public TrafficDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isExistBySubscriptionId(final Integer subscriptionId) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_SUBSCRIPTION_ID_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, subscriptionId);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Required traffic doesn't exist", e);
        }
    }

    @Override
    public void add(final Traffic traffic) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {

            statement.setInt(1, traffic.getSubscriptionId());
            statement.setInt(2, traffic.getValue());
            statement.setDate(3, traffic.getDate());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create traffic", e);
        }
    }

    @Override
    public List<Traffic> find() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Traffic> traffics = new ArrayList<>();
            while (resultSet.next()) {
                final Traffic traffic = new Traffic(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getDate(3));
                traffics.add(traffic);
            }

            return traffics;
        } catch (Exception e) {
            throw new DaoException("Can't find traffics");
        }
    }

    @Override
    public List<Traffic> findAndSort(final Sort sort) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, sort.getColumn());
            statement.setString(2, sort.getDirection().name());

            final List<Traffic> traffics = new ArrayList<>();
            while (resultSet.next()) {
                final Traffic traffic = new Traffic(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getDate(3));
                traffics.add(traffic);
            }

            return traffics;
        } catch (Exception e) {
            throw new DaoException("Can't find or sort traffics");
        }
    }

    @Override
    public List<Traffic> findAndFilter(final Filter filter) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, filter.getColumn());
            statement.setString(2, filter.getPattern());

            final List<Traffic> traffics = new ArrayList<>();
            while (resultSet.next()) {
                final Traffic traffic = new Traffic(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getDate(3));
                traffics.add(traffic);
            }

            return traffics;
        } catch (Exception e) {
            throw new DaoException("Can't find or filter traffics");
        }
    }

    @Override
    public List<Traffic> findAndFilterAndSort(final Filter filter, final Sort sort) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_AND_SORT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, filter.getColumn());
            statement.setString(2, filter.getPattern());
            statement.setString(3, sort.getColumn());
            statement.setString(4, sort.getDirection().name());

            final List<Traffic> traffics = new ArrayList<>();
            while (resultSet.next()) {
                final Traffic traffic = new Traffic(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getDate(3));
                traffics.add(traffic);
            }

            return traffics;
        } catch (Exception e) {
            throw new DaoException("Can't find or filter or sort traffics");
        }
    }

    @Override
    public Traffic findOneById(Integer id) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Traffic traffic) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final Integer id) {

        throw new UnsupportedOperationException();
    }
}
