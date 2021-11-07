package by.hrachyshkin.dao.entity_dao.traffic_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Traffic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrafficDaoImpl implements TrafficDao {


    private static final String EXISTS_BY_SUBSCRIPTION_ID_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM traffics " +
                    "WHERE subscription_id = ? " +
                    ")";

    private static final String FIND_QUERY =
            "SELECT subscription_id, value, date " +
                    "FROM traffics ";

    private static final String FIND_AND_SORT_BY_DATE_QUERY =
            "SELECT subscription_id, value, date " +
                    "FROM traffics " +
                    "ORDER BY date ASC";

    private static final String FIND_AND_FILTER_BY_SUBSCRIPTION_ID_QUERY =
            "SELECT subscription_id, value, date " +
                    "FROM traffics " +
                    "WHERE subscription_id = ? ";

    private static final String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT subscription_id, value, date " +
                    "FROM traffics " +
                    "WHERE subscription_id = ? " +
                    "ORDER BY date ASC ";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO traffics (subscription_id, value, date) " +
                    "VALUES (?, ?, ?)";

    private final Connection connection;

    public TrafficDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isExistBySubscriptionId(final Integer subscriptionId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_SUBSCRIPTION_ID_QUERY)) {
            statement.setInt(1, subscriptionId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Traffic doesn't exist", e);
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
    public List<Traffic> findAndSortByDate() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_BY_DATE_QUERY);
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
            throw new DaoException("Can't find or sort traffics");
        }
    }

    @Override
    public List<Traffic> findAndFilterBySubscriptionId(final Integer subscriptionId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_BY_SUBSCRIPTION_ID_QUERY)) {
            statement.setInt(1, subscriptionId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Traffic> traffics = new ArrayList<>();
                while (resultSet.next()) {
                    final Traffic traffic = new Traffic(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getDate(3));
                    traffics.add(traffic);
                }
                return traffics;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find or filter traffics");
        }
    }

    @Override
    public List<Traffic> findAndFilterAndSort(final Integer subscriptionId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_AND_SORT_QUERY)) {
            statement.setInt(1, subscriptionId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Traffic> traffics = new ArrayList<>();
                while (resultSet.next()) {
                    final Traffic traffic = new Traffic(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getDate(3));
                    traffics.add(traffic);
                }
                return traffics;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find or filter traffics");
        }
    }

    @Override
    public Traffic findOneById(Integer id) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final Traffic traffic) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {
            statement.setInt(1, traffic.getSubscriptionId());
            statement.setInt(2, traffic.getValue());
            statement.setDate(3, traffic.getDate());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't add traffic", e);
        }
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
