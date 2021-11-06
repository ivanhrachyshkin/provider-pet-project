package by.hrachyshkin.dao.entity_dao.bill_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.pool.PooledConnection;
import by.hrachyshkin.entity.Bill;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl  implements BillDao {

    private static final String EXISTS_BY_SUBSCRIPTION_ID_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM bills " +
                    "WHERE subscription_id = ?" +
                    ")";

    private static final String FIND_QUERY =
            "SELECT id, subscription_id, value, date, status " +
                    "FROM bills ";

    private static final String FIND_AND_SORT_QUERY =
            "SELECT id, subscription_id, value, date, status " +
                    "FROM bills " +
                    "ORDER BY ? ? ";

    private static final String FIND_AND_FILTER_QUERY =
            "SELECT id, subscription_id, value, date, status " +
                    "FROM bills " +
                    "WHERE ? LIKE ? ";

    private static final String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT id, subscription_id, value, date, status " +
                    "FROM bills " +
                    "WHERE ? LIKE ? " +
                    "ORDER BY ? ? ";

    private static final String FIND_ONE_BILL_QUERY_BY_ID =
            "SELECT id, subscription_id, value, date, status " +
                    "FROM bills " +
                    "WHERE id = ?";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO bills (subscription_id, value, date, status) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String UPDATE_QUERY =
            "INSERT INTO bills (subscription_id, value, date, status) " +
                    "VALUES ?, ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_QUERY =
            "DELETE " +
                    "FROM bills " +
                    "WHERE id = ?";

    private final Connection connection;

    public BillDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isExistBySubscriptionId(Integer subscriptionId) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_SUBSCRIPTION_ID_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, subscriptionId);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Required subscription doesn't exist", e);
        }
    }


    @Override
    public void add(final Bill bill) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {

            statement.setInt(1, bill.getSubscriptionId());
            statement.setFloat(2, bill.getValue());
            statement.setDate(3, bill.getDate());
            statement.setBoolean(4, bill.getStatus());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create bill", e);
        }
    }

    @Override
    public List<Bill> find() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Bill> bills = new ArrayList<>();
            while (resultSet.next()) {
                final Bill bill = new Bill(
                        resultSet.getInt(1),
                        resultSet.getFloat(2),
                        resultSet.getDate(3),
                        resultSet.getBoolean(4));
                bills.add(bill);
            }

            return bills;
        } catch (Exception e) {
            throw new DaoException("Can't find bills");
        }
    }

    @Override
    public List<Bill> findAndSort(Sort sort) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, sort.getColumn());
            statement.setString(2, sort.getDirection().name());

            final List<Bill> bills = new ArrayList<>();
            while (resultSet.next()) {
                final Bill bill = new Bill(
                        resultSet.getInt(1),
                        resultSet.getFloat(2),
                        resultSet.getDate(3),
                        resultSet.getBoolean(4));
                bills.add(bill);
            }

            return bills;
        } catch (Exception e) {
            throw new DaoException("Can't find or sort accounts");
        }
    }

    @Override
    public List<Bill> findAndFilter(final Filter filter) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, filter.getColumn());
            statement.setString(2, filter.getPattern());

            final List<Bill> bills = new ArrayList<>();
            while (resultSet.next()) {
                final Bill bill = new Bill(
                        resultSet.getInt(1),
                        resultSet.getFloat(2),
                        resultSet.getDate(3),
                        resultSet.getBoolean(4));
                bills.add(bill);
            }

            return bills;
        } catch (Exception e) {
            throw new DaoException("Can't find or filter bills");
        }
    }

    @Override
    public List<Bill> findAndFilterAndSort(Filter filter, Sort sort) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_AND_SORT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, filter.getColumn());
            statement.setString(2, filter.getPattern());
            statement.setString(3, sort.getColumn());
            statement.setString(4, sort.getDirection().name());

            final List<Bill> bills = new ArrayList<>();
            while (resultSet.next()) {
                final Bill bill = new Bill(
                        resultSet.getInt(1),
                        resultSet.getFloat(2),
                        resultSet.getDate(3),
                        resultSet.getBoolean(4));
                bills.add(bill);
            }

            return bills;
        } catch (Exception e) {
            throw new DaoException("Can't find or filter or sort accounts");
        }
    }

    @Override
    public Bill findOneById(final Integer id) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(FIND_ONE_BILL_QUERY_BY_ID);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();

            return new Bill(
                    resultSet.getInt(1),
                    resultSet.getFloat(2),
                    resultSet.getDate(3),
                    resultSet.getBoolean(4));

        } catch (SQLException e) {
            throw new DaoException("Can't find bill by id", e);
        }
    }

    @Override
    public void update(final Bill bill) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setInt(1, bill.getSubscriptionId());
            statement.setFloat(2, bill.getValue());
            statement.setDate(3, bill.getDate());
            statement.setBoolean(4, bill.getStatus());

            statement.setInt(5, bill.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add bill", e);
        }
    }

    @Override
    public void delete(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete bill", e);
        }
    }
}
