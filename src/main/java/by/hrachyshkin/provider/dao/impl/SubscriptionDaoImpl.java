package by.hrachyshkin.provider.dao.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.SubscriptionDao;
import by.hrachyshkin.provider.model.Subscription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubscriptionDaoImpl implements SubscriptionDao {

    private static final String EXISTS_BY_ID_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE id = ?" +
                    ")";

    private static final String EXISTS_BY_ACCOUNT_ID_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE account_id = ? " +
                    ")";

    private static final String EXISTS_BY_TARIFF_ID_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE tariff_id = ? " +
                    ")";

    private static final String EXISTS_BY_ACCOUNT_AND_TARIFF_ID_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM subscriptions " +
                    "WHERE account_id = ? AND tariff_id = ? " +
                    ")";

    private static final String FIND_QUERY =
            "SELECT id, account_id, tariff_id " +
                    "FROM subscriptions ";

    private static final String FIND_AND_FILTER_BY_ACCOUNT_ID_QUERY =
            "SELECT id, account_id, tariff_id " +
                    "FROM subscriptions " +
                    "WHERE account_id = ? ";


    private static final String FIND_ONE_SUBSCRIPTION_QUERY_BY_ID =
            "SELECT id, account_id, tariff_id " +
                    "FROM subscriptions " +
                    "WHERE id = ?";

    private static final String FIND_ONE_SUBSCRIPTION_BY_ACCOUNT_AND_TARIFF_ID_QUERY =
            "SELECT id, account_id, tariff_id " +
                    "FROM subscriptions " +
                    "WHERE account_id = ? and tariff_id = ?";
    ;

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO subscriptions (account_id, tariff_id) " +
                    "VALUES (?, ?)";

    private static final String DELETE_QUERY =
            "DELETE " +
                    "FROM subscriptions " +
                    "WHERE id = ?";

    private static final String DELETE_BY_ID_QUERY =
            "DELETE " +
                    "FROM subscriptions " +
                    "WHERE id = ?";

    private final Connection connection;
    private final ResourceBundle rb;

    public SubscriptionDaoImpl(Connection connection, ResourceBundle rb) {
        this.connection = connection;
        this.rb = rb;
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
            throw new DaoException(rb.getString("subscription.exist.by.id.exception"), e);
        }
    }

    @Override
    public boolean isExistByAccountId(final Integer accountId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ACCOUNT_ID_QUERY)) {
            statement.setInt(1, accountId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("subscription.exist.by.account.id.exception"), e);
        }
    }

    @Override
    public boolean isExistByTariffId(final Integer tariffId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_TARIFF_ID_QUERY)) {
            statement.setInt(1, tariffId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("subscription.exist.by.tariff.id.exception"), e);
        }
    }

    @Override
    public boolean isExistByAccountAndTariffId(final Integer accountId, final Integer tariffId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ACCOUNT_AND_TARIFF_ID_QUERY)) {
            statement.setInt(1, accountId);
            statement.setInt(2, tariffId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("subscription.exist.by.account.id.and.tariff.id.exception"), e);
        }
    }

    @Override
    public List<Subscription> find() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Subscription> subscriptions = new ArrayList<>();
            while (resultSet.next()) {
                final Subscription subscription = buildSubscription(resultSet);
                subscriptions.add(subscription);
            }
            return subscriptions;

        } catch (Exception e) {
            throw new DaoException(rb.getString("subscription.find.exception"));
        }
    }

    @Override
    public List<Subscription> findAndFilterByAccountId(Integer accountId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_BY_ACCOUNT_ID_QUERY)) {
            statement.setInt(1, accountId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Subscription> subscriptions = new ArrayList<>();
                while (resultSet.next()) {
                    final Subscription subscription = buildSubscription(resultSet);
                    subscriptions.add(subscription);
                }
                return subscriptions;
            }

        } catch (Exception e) {
            throw new DaoException(rb.getString("subscription.find.or.filter.by.account.id.exception"));
        }
    }

    @Override
    public Subscription findOneById(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_ONE_SUBSCRIPTION_QUERY_BY_ID)) {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return buildSubscription(resultSet);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("subscription.find.one.by.id.exception"), e);
        }
    }

    @Override
    public Subscription findOneByAccountIdAndTariffId(final Integer accountId, final Integer tariffId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_ONE_SUBSCRIPTION_BY_ACCOUNT_AND_TARIFF_ID_QUERY)) {
            statement.setInt(1, accountId);
            statement.setInt(2, tariffId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return buildSubscription(resultSet);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("subscription.find.one.by.id.exception"), e);
        }
    }

    @Override
    public void add(final Subscription subscription) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {
            statement.setInt(1, subscription.getAccountId());
            statement.setInt(2, subscription.getTariffId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(rb.getString("subscription.add.exception"), e);
        }
    }

    @Override
    public void update(final Subscription subscription) throws DaoException {
        throw new UnsupportedOperationException(rb.getString("subscription.update.unsupported.exception"));
    }

    @Override
    public void delete(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(rb.getString("subscription.delete.exception"), e);
        }
    }

    @Override
    public void deleteByAccountIdAndTariffId(final Integer subscriptionId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY)) {
            statement.setInt(1, subscriptionId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(rb.getString("subscription.delete.by.account.id.and.tariff.id.exception"), e);
        }
    }

    private Subscription buildSubscription(final ResultSet resultSet) throws SQLException {

        return new Subscription(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getInt(3));
    }
}
