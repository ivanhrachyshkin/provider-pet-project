package by.hrachyshkin.dao.entity_dao.subscription_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Subscription;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDaoImpl extends BaseDao implements SubscriptionDao {

    private static final String EXISTS_BY_ID_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE id = ?" +
                    ")";

    private static final String EXISTS_BY_ACCOUNT_ID_AND_TARIFF_ID =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE account_id = ? AND tariff_id = ?" +
                    ")";

    private static final String FIND_QUERY =
            "SELECT id, account_id, tariff_id " +
                    "FROM subscriptions ";

    private static final String FIND_AND_SORT_QUERY =
            "SELECT id, account_id, tariff_id " +
                    "FROM subscriptions " +
                    "ORDER BY ? ? ";

    private static final String FIND_AND_FILTER_QUERY =
            "SELECT id, account_id, tariff_id " +
                    "FROM subscriptions " +
                    "WHERE ? LIKE ? ";

    private static final String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT id, account_id, tariff_id " +
                    "FROM subscriptions " +
                    "WHERE ? LIKE ? " +
                    "ORDER BY ? ? ";

    private static final String FIND_ONE_SUBSCRIPTION_QUERY_BY_ID =
            "SELECT id, account_id, tariff_id " +
                    "FROM subscriptions " +
                    "WHERE id = ?";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO subscriptions (account_id, tariff_id) " +
                    "VALUES (?, ?)";

    private static final String UPDATE_QUERY =
            "INSERT INTO subscriptions (account_id, tariff_id) " +
                    "VALUES (?, ?)" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_QUERY =
            "DELETE " +
                    "FROM subscriptions " +
                    "WHERE id = ?";

    public SubscriptionDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean isExistById(final Integer id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Required subscription doesn't exist", e);
        }
    }

    @Override
    public boolean isExistByAccountIdAndTariffId(final Integer accountId, final Integer tariffId) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ACCOUNT_ID_AND_TARIFF_ID);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, accountId);
            statement.setInt(2, tariffId);
            resultSet.next();

            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Required subscription doesn't exist", e);
        }
    }


    @Override
    public void add(final Subscription subscription) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {

            statement.setInt(1, subscription.getAccountId());
            statement.setInt(2, subscription.getAccountId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create subscription", e);
        }
    }

    @Override
    public List<Subscription> find() throws DaoException {

        try (final Connection connection = dataSource.getConnection()) {

            try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
                 final ResultSet resultSet = statement.executeQuery()) {

                final List<Subscription> subscriptions = new ArrayList<>();
                while (resultSet.next()) {
                    final Subscription subscription = new Subscription(
                            resultSet.getInt(1),
                            resultSet.getInt(2));
                    subscriptions.add(subscription);
                }

                return subscriptions;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find subscriptions");
        }
    }

    @Override
    public List<Subscription> findAndFilter(final Filter filter) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, filter.getColumn());
            statement.setString(2, filter.getPattern());

            final List<Subscription> subscriptions = new ArrayList<>();
            while (resultSet.next()) {
                final Subscription subscription = new Subscription(
                        resultSet.getInt(1),
                        resultSet.getInt(2));
                subscriptions.add(subscription);
            }

            return subscriptions;
        } catch (Exception e) {
            throw new DaoException("Can't find or filter subscriptions");
        }
    }

    @Override
    public Subscription findOneById(final Integer id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ONE_SUBSCRIPTION_QUERY_BY_ID);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();

            return new Subscription(
                    resultSet.getInt(1),
                    resultSet.getInt(2));

        } catch (SQLException e) {
            throw new DaoException("Can't find subscription by id", e);
        }
    }

    @Override
    public void update(final Subscription subscription) throws DaoException {

      throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final Integer id) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete subscription", e);
        }
    }
}
