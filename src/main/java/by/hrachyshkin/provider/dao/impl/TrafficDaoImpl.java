package by.hrachyshkin.provider.dao.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.TrafficDao;
import by.hrachyshkin.provider.model.Traffic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TrafficDaoImpl implements TrafficDao {

    private static final String EXISTS_BY_SUBSCRIPTION_ID_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM traffics " +
                    "WHERE subscription_id = ? AND value = ? AND date = ? " +
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

    private static final String FIND_AND_FILTER_AND_SORT_OFFSET_QUERY =
            "SELECT subscription_id, value, date " +
                    "FROM traffics " +
                    "WHERE subscription_id = ? " +
                    "ORDER BY date ASC " +
                    "LIMIT 5 OFFSET ?";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO traffics (subscription_id, value, date) " +
                    "VALUES (?, ?, ?)";

    private static final String DELETE_BY_SUBSCRIPTION_ID_QUERY =
            "DELETE " +
                    "FROM traffics WHERE subscription_id = ? ";

    private final Connection connection;
    private final ResourceBundle rb;

    public TrafficDaoImpl(Connection connection, ResourceBundle rb) {
        this.connection = connection;
        this.rb = rb;
    }

    @Override
    public boolean isExists(final Traffic traffic) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_SUBSCRIPTION_ID_QUERY)) {
            statement.setInt(1, traffic.getSubscriptionId());
            statement.setInt(2, traffic.getValue());
            statement.setDate(3, java.sql.Date.valueOf(traffic.getDate()));

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("traffic.exist.exception"), e);
        }
    }

    @Override
    public List<Traffic> find() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Traffic> traffics = new ArrayList<>();
            while (resultSet.next()) {
                final Traffic traffic = buildTraffic(resultSet);
                traffics.add(traffic);
            }
            return traffics;

        } catch (Exception e) {
            throw new DaoException(rb.getString("traffic.find.exception"));
        }
    }

    @Override
    public List<Traffic> findAndSortByDate() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_BY_DATE_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Traffic> traffics = new ArrayList<>();
            while (resultSet.next()) {
                final Traffic traffic = buildTraffic(resultSet);
                traffics.add(traffic);
            }
            return traffics;

        } catch (Exception e) {
            throw new DaoException(rb.getString("traffic.find.or.sort.by.date.exception"));
        }
    }

    @Override
    public List<Traffic> findAndFilterBySubscriptionId(final Integer subscriptionId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_BY_SUBSCRIPTION_ID_QUERY)) {
            statement.setInt(1, subscriptionId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Traffic> traffics = new ArrayList<>();
                while (resultSet.next()) {
                    final Traffic traffic = buildTraffic(resultSet);
                    traffics.add(traffic);
                }
                return traffics;
            }

        } catch (Exception e) {
            throw new DaoException(rb.getString("traffic.find.or.filter.by.subscription.id.exception"));
        }
    }

    @Override
    public List<Traffic> findAndFilterAndSortOffset(final Integer subscriptionId, final int offset) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_AND_SORT_OFFSET_QUERY)) {
            statement.setInt(1, subscriptionId);
            statement.setInt(2, offset);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Traffic> traffics = new ArrayList<>();
                while (resultSet.next()) {
                    final Traffic traffic = buildTraffic(resultSet);
                    traffics.add(traffic);
                }
                return traffics;
            }

        } catch (Exception e) {
            throw new DaoException(rb.getString("traffic.find.or.filter.by.subscription.id.or.sort.by.date.exception"));
        }
    }

    @Override
    public Traffic findOneById(Integer id) {

        throw new UnsupportedOperationException(rb.getString("traffic.find.one.by.id.unsupported.exception"));
    }

    @Override
    public void add(final Traffic traffic) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {
            statement.setInt(1, traffic.getSubscriptionId());
            statement.setInt(2, traffic.getValue());
            statement.setDate(3, java.sql.Date.valueOf(traffic.getDate()));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(rb.getString("traffic.add.exception"), e);
        }
    }

    @Override
    public void update(final Traffic traffic) {
        throw new UnsupportedOperationException(rb.getString("traffic.update.unsupported.exception"));
    }

    @Override
    public void delete(final Integer subscriptionId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(DELETE_BY_SUBSCRIPTION_ID_QUERY)) {
            statement.setInt(1, subscriptionId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(rb.getString("traffic.delete.exception"), e);
        }
    }


    private Traffic buildTraffic(final ResultSet resultSet) throws SQLException {

        return new Traffic(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getDate(3).toLocalDate());
    }
}
