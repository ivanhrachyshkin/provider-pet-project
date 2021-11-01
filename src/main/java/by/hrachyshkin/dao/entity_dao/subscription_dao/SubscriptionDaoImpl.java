package by.hrachyshkin.dao.entity_dao.subscription_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Criteria;
import by.hrachyshkin.entity.Subscription;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDaoImpl extends BaseDao implements SubscriptionDao {

    private static final String IS_EXIST_SUBSCRIPTION_QUERY =
            "SELECT EXISTS(SELECT 1 FROM accounts WHERE account_id = ? AND tariff_id = ?) ";

    private static final String CREATE_SUBSCRIPTION_QUERY =
            "INSERT " +
                    "INTO subscriptions (account_id, tariff_id, date_from, date_to) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT DO NOTHING";

    private static final String FIND_ALL_SUBSCRIPTIONS_QUERY =
            "SELECT id, account_id, tariff_id, date_from, date_to " +
                    "FROM subscriptions";

    private static final String FIND_ALL_SUBSCRIPTIONS_QUERY_WITH_SORT =
            "SELECT id, account_id, tariff_id, date_from, date_to " +
                    "FROM subscriptions " +
                    "ORDER BY ? ?";

    private static final String FIND_ALL_SUBSCRIPTIONS_QUERY_BY_FILTER =
            "SELECT id, account_id, tariff_id, date_from, date_to " +
                    "FROM subscriptions " +
                    "WHERE ? LIKE ?%";

    private static final String FIND_ONE_SUBSCRIPTION_QUERY_BY_ID =
            "SELECT id, tariff_id, discount_id, date_from, date_to " +
                    "FROM subscriptions " +
                    "WHERE id = ?";

    private static final String UPDATE_SUBSCRIPTION_QUERY =
            "INSERT INTO subscriptions (account_id, tariff_id, date_from, date_to) " +
                    "VALUES ?, ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_SUBSCRIPTION_BY_ID_QUERY =
            "DELETE id, account_id, tariff_id, date_from, date_to " +
                    "FROM subscriptions " +
                    "WHERE id = ?";

    public SubscriptionDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean isExist(int accountId, int tariffId) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(IS_EXIST_SUBSCRIPTION_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, accountId);
            statement.setInt(2, tariffId);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Can't find account by id", e);
        }
    }

    @Override
    public void create(final Subscription subscription) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(CREATE_SUBSCRIPTION_QUERY)) {

            statement.setInt(1, subscription.getAccountId());
            statement.setInt(2, subscription.getTariffId());
            statement.setDate(3, subscription.getDateFrom());
            statement.setDate(4, subscription.getDateTo());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create subscription", e);
        }
    }

    @Override
    public List<Subscription> find(final Criteria criteria) throws DaoException {

        try (final Connection connection = dataSource.getConnection()) {

            String query;
            if (criteria.getSorting() != null) {
                query = FIND_ALL_SUBSCRIPTIONS_QUERY_WITH_SORT;
            } else if (criteria.getFilter() != null) {
                query = FIND_ALL_SUBSCRIPTIONS_QUERY_BY_FILTER;
            } else query = FIND_ALL_SUBSCRIPTIONS_QUERY;

            try (final PreparedStatement statement = connection.prepareStatement(query);
                 final ResultSet resultSet = statement.executeQuery()) {

                if (criteria.getSorting() != null) {
                    statement.setString(1, criteria.getSorting().getColumn());
                    statement.setString(2, criteria.getSorting().getDirection().name());
                } else if (criteria.getFilter() != null) {
                    statement.setString(1, criteria.getFilter().getColumn());
                    statement.setString(2, criteria.getFilter().getPattern());
                }

                final List<Subscription> subscriptions = new ArrayList<>();
                while (resultSet.next()) {
                    final Subscription subscription = new Subscription(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getDate(3),
                            resultSet.getDate(4));
                    subscriptions.add(subscription);
                }

                return subscriptions;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find required subscriptions");
        }
    }

    @Override
    public Subscription findOneById(int id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ONE_SUBSCRIPTION_QUERY_BY_ID);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();

            return new Subscription(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getDate(3),
                    resultSet.getDate(4));

        } catch (SQLException e) {
            throw new DaoException("Can't find subscription by id", e);
        }
    }

    @Override
    public void update(final Subscription subscription) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_SUBSCRIPTION_QUERY)) {

            statement.setInt(1, subscription.getAccountId());
            statement.setInt(2, subscription.getTariffId());
            statement.setDate(3, subscription.getDateFrom());
            statement.setDate(4, subscription.getDateTo());

            statement.setInt(5, subscription.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add subscription", e);
        }
    }

    @Override
    public void delete(final int id) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_SUBSCRIPTION_BY_ID_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete subscription", e);
        }
    }
}
