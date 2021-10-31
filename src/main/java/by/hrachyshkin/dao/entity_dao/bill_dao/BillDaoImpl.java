package by.hrachyshkin.dao.entity_dao.bill_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Bill;
import by.hrachyshkin.entity.Criteria;
import by.hrachyshkin.entity.Tariff;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl extends BaseDao implements BillDao {


    public BillDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String CREATE_BILL_QUERY =
            "INSERT " +
                    "INTO bills (subscription_id, sum, status) " +
                    "VALUES (?, ?, ?) " +
                    "ON CONFLICT DO NOTHING";

    private static final String FIND_ALL_BILLS_QUERY =
            "SELECT id, subscription_id, sum, status " +
                    "FROM bills";

    private static final String FIND_ALL_BILLS_QUERY_WITH_SORT =
            "SELECT id, subscription_id, sum, status " +
                    "FROM bills " +
                    "ORDER BY ? ?";

    private static final String FIND_ALL_BILLS_QUERY_BY_FILTER =
            "SELECT id, subscription_id, sum, status " +
                    "FROM bills " +
                    "WHERE ? = ?";

    private static final String UPDATE_BILL_QUERY =
            "INSERT INTO accounts (subscription_id, sum, status) " +
                    "VALUES ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_BILL_BY_ID_QUERY =
            "DELETE id, subscription_id, price, sum " +
                    "FROM bills " +
                    "WHERE id = ?";

    @Override
    public void create(final Bill bill) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(CREATE_BILL_QUERY)) {

            statement.setInt(1, bill.getSubscriptionId());
            statement.setDouble(2, bill.getSum());
            statement.setBoolean(3, bill.isStatus());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create tariff", e);
        }
    }

    @Override
    public List<Bill> find(final Criteria criteria) throws DaoException {

        try (final Connection connection = dataSource.getConnection()) {

            String query;
            if (criteria.getSorting() != null) {
                query = FIND_ALL_BILLS_QUERY_WITH_SORT;
            } else if (criteria.getFilter() != null) {
                query = FIND_ALL_BILLS_QUERY_BY_FILTER;
            } else query = FIND_ALL_BILLS_QUERY;

            try (final PreparedStatement statement = connection.prepareStatement(query);
                 final ResultSet resultSet = statement.executeQuery()) {

                if (criteria.getSorting() != null) {
                    statement.setString(1, criteria.getSorting().getColumn());
                    statement.setString(2, criteria.getSorting().getDirection().name());
                } else if (criteria.getFilter() != null) {
                    statement.setString(1, criteria.getFilter().getColumn());
                    statement.setString(2, criteria.getFilter().getPattern());
                }

                final List<Bill> bills = new ArrayList<>();
                while (resultSet.next()) {
                    final Bill tariff = new Bill(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getDouble(3),
                            resultSet.getBoolean(4));
                    bills.add(tariff);
                }

                return bills;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find required discounts");
        }
    }

    @Override
    public void update(final Bill bill) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_BILL_QUERY)) {

            statement.setInt(1, bill.getSubscriptionId());
            statement.setDouble(2, bill.getSum());
            statement.setBoolean(3, bill.isStatus());

            statement.setInt(4, bill.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add tariff", e);
        }
    }

    @Override
    public void delete(final int id) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_BILL_BY_ID_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete tariff", e);
        }
    }
}
