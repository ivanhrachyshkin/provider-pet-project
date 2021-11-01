package by.hrachyshkin.provider.dao.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.BillDao;
import by.hrachyshkin.provider.model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BillDaoImpl implements BillDao {

    private static final String EXISTS_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM bills " +
                    "WHERE subscription_id = ? AND value = ? AND date = ?" +
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

    private static final String FIND_AND_FILTER_AND_SORT_OFFSET_QUERY =
            "SELECT subscription_id, value, date, status " +
                    "FROM bills " +
                    "WHERE subscription_id = ? " +
                    "ORDER BY date ASC " +
                    "LIMIT 5 OFFSET ?";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO bills (subscription_id, value, date, status) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String UPDATE_BILL_STATUS_QUERY =
            "UPDATE bills " +
                    "SET status = true " +
                    "WHERE subscription_id = ? AND value = ? AND date = ?";

    private static final String DELETE_BY_SUBSCRIPTION_ID_QUERY =
            "DELETE FROM bills " +
                    "WHERE subscription_id = ?";

    private final Connection connection;
    private final ResourceBundle rb;

    public BillDaoImpl(Connection connection, ResourceBundle rb) {
        this.connection = connection;
        this.rb = rb;
    }

    @Override
    public boolean isExists(final Integer subscriptionId, final Float value, final LocalDate date) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_QUERY)) {
            statement.setInt(1, subscriptionId);
            statement.setFloat(2, value);
            statement.setDate(3, java.sql.Date.valueOf(date));

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("bill.exist.exception"), e);
        }
    }

    @Override
    public List<Bill> find() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Bill> bills = new ArrayList<>();
            while (resultSet.next()) {
                final Bill bill = buildBill(resultSet);
                bills.add(bill);
            }
            return bills;

        } catch (Exception e) {
            throw new DaoException(rb.getString("bill.find.exception"));
        }
    }

    @Override
    public List<Bill> findAndSortByDate() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_BY_DATE_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Bill> bills = new ArrayList<>();
            while (resultSet.next()) {
                final Bill bill = buildBill(resultSet);
                bills.add(bill);
            }
            return bills;

        } catch (Exception e) {
            throw new DaoException(rb.getString("bill.find.or.sort.by.date.exception"));
        }
    }

    @Override
    public List<Bill> findAndFilterBySubscriptionId(final Integer subscriptionId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_BY_SUBSCRIPTION_ID_QUERY)) {
            statement.setInt(1, subscriptionId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Bill> bills = new ArrayList<>();
                while (resultSet.next()) {
                    final Bill bill = buildBill(resultSet);
                    bills.add(bill);
                }
                return bills;
            }

        } catch (Exception e) {
            throw new DaoException(rb.getString("bill.find.or.filter.by.subscription.id.exception"));
        }
    }

    @Override
    public List<Bill> findAndFilterAndSortOffset(final Integer subscriptionId, final int offset) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_AND_SORT_OFFSET_QUERY)) {
            statement.setInt(1, subscriptionId);
            statement.setInt(2, offset);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Bill> bills = new ArrayList<>();
                while (resultSet.next()) {
                    final Bill bill = buildBill(resultSet);
                    bills.add(bill);
                }
                return bills;
            }

        } catch (Exception e) {
            throw new DaoException(rb.getString("bill.find.or.sort.by.date.or.filter.by.subscription.id.exception"));
        }
    }

    @Override
    public Bill findOneById(final Integer id) throws DaoException {
        throw new UnsupportedOperationException(rb.getString("bill.find.one.by.id.unsupported.exception"));
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
            throw new DaoException(rb.getString("bill.add.exception"), e);
        }
    }

    @Override
    public void update(final Bill bill) throws DaoException {

        throw new UnsupportedOperationException(rb.getString("bill.update.unsupported.exception"));
    }


    @Override
    public void delete(final Integer subscriptionId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(DELETE_BY_SUBSCRIPTION_ID_QUERY)) {
            statement.setInt(1, subscriptionId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(rb.getString("bill.delete.exception"), e);
        }
    }

    @Override
    public void updateBillStatus(final Integer subscriptionId, final Float value, final LocalDate date) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(UPDATE_BILL_STATUS_QUERY)) {
            statement.setInt(1, subscriptionId);
            statement.setFloat(2, value);
            statement.setDate(3, java.sql.Date.valueOf(date));

            statement.executeUpdate();

        } catch (Exception e) {
            throw new DaoException(rb.getString("bill.update.status.exception"), e);
        }
    }

    private Bill buildBill(final ResultSet resultSet) throws SQLException {

        return new Bill(
                resultSet.getInt(1),
                resultSet.getFloat(2),
                resultSet.getDate(3).toLocalDate(),
                resultSet.getBoolean(4));
    }
}
