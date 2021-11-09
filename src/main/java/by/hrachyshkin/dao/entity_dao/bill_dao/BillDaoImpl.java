package by.hrachyshkin.dao.entity_dao.bill_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl implements BillDao {

    private static final String EXISTS_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM bills " +
                    "WHERE subscription_id = ? AND value = ? AND date = ? AND status = ? " +
                    ")";

    private static final String FIND_QUERY =
            "SELECT subscription_id, value, date, status " +
                    "FROM bills ";

    private static final String FIND_AND_SORT_BY_DATE_QUERY =
            "SELECT subscription_id, value, date, status " +
                    "FROM bills " +
                    "ORDER BY date ASC ";

    private static final String FIND_AND_FILTER_BY_SUBSCRIPTION_ID_QUERY =
            "SELECT subscription_id, value, date, status " +
                    "FROM bills " +
                    "WHERE subscription_id = ? ";

    private static final String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT subscription_id, value, date, status " +
                    "FROM bills " +
                    "WHERE subscription_id = ? " +
                    "ORDER BY date ASC ";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO bills (subscription_id, value, date, status) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String UPDATE_BILL_STATUS_QUERY =
            "UPDATE bills " +
                    "SET status = true " +
                    "WHERE subscription_id = ? AND value = ? AND date = ?";

    private final Connection connection;

    public BillDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isExists(final Bill bill) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_QUERY)) {
            statement.setInt(1, bill.getSubscriptionId());
            statement.setFloat(2, bill.getValue());
            statement.setDate(3, java.sql.Date.valueOf(bill.getDate()));
            statement.setBoolean(4, bill.getStatus());

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Bill doesn't exist", e);
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
                        resultSet.getDate(3).toLocalDate(),
                        resultSet.getBoolean(4));
                bills.add(bill);
            }
            return bills;
        } catch (Exception e) {
            throw new DaoException("Can't find bills");
        }
    }

    @Override
    public List<Bill> findAndSortByDate() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_BY_DATE_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {
            final List<Bill> bills = new ArrayList<>();
            while (resultSet.next()) {
                final Bill bill = new Bill(
                        resultSet.getInt(1),
                        resultSet.getFloat(2),
                        resultSet.getDate(3).toLocalDate(),
                        resultSet.getBoolean(4));
                bills.add(bill);
            }
            return bills;
        } catch (Exception e) {
            throw new DaoException("Can't find or sort bills");
        }
    }

    @Override
    public List<Bill> findAndFilterBySubscriptionId(final Integer subscriptionId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_BY_SUBSCRIPTION_ID_QUERY)) {
            statement.setInt(1, subscriptionId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Bill> bills = new ArrayList<>();
                while (resultSet.next()) {
                    final Bill bill = new Bill(
                            resultSet.getInt(1),
                            resultSet.getFloat(2),
                            resultSet.getDate(3).toLocalDate(),
                            resultSet.getBoolean(4));
                    bills.add(bill);
                }
                return bills;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find or filter bills");
        }
    }

    @Override
    public List<Bill> findAndFilterAndSort(final Integer subscriptionId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_AND_SORT_QUERY)) {
            statement.setInt(1, subscriptionId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Bill> bills = new ArrayList<>();
                while (resultSet.next()) {
                    final Bill bill = new Bill(
                            resultSet.getInt(1),
                            resultSet.getFloat(2),
                            resultSet.getDate(3).toLocalDate(),
                            resultSet.getBoolean(4));
                    bills.add(bill);
                }
                return bills;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find or filter or sort bills");
        }
    }

    @Override
    public Bill findOneById(final Integer id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final Bill bill) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {
            statement.setInt(1, bill.getSubscriptionId());
            statement.setFloat(2, bill.getValue());
            statement.setDate(3, java.sql.Date.valueOf(bill.getDate()));
            statement.setBoolean(4, bill.getStatus());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't create bill", e);
        }
    }

    @Override
    public void update(final Bill bill) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(UPDATE_BILL_STATUS_QUERY)) {

            statement.setInt(1, bill.getSubscriptionId());
            statement.setFloat(2, bill.getValue());
            statement.setDate(3, java.sql.Date.valueOf(bill.getDate()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update bill", e);
        }
    }

    @Override
    public void delete(final Integer subscriptionId) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
