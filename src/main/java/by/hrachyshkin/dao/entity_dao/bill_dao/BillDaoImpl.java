package by.hrachyshkin.dao.entity_dao.bill_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Bill;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class BillDaoImpl extends BaseDao implements BillDao {


    public BillDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String CREATE_BALANCE_QUERY =
            "INSERT " +
                    "INTO balances (bill, last_deposite_date, traffic_quantity) " +
                    "VALUES (?, ?, ?) " +
                    "ON CONFLICT DO NOTHING";

    private static final String FIND_BALANCE_BY_ID_QUERY =
            "SELECT id, bill, last_deposite_date, traffic_quantity " +
                    "FROM balances " +
                    "WHERE id = ?";

    @Override
    public void create(final Bill bill) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement userStatement = connection.prepareStatement(CREATE_BALANCE_QUERY)) {

            userStatement.setDouble(1, bill.getBill());
            userStatement.setString(2, String.valueOf(bill.getLatsDepositDate())); //todo DATE
            userStatement.setInt(3, bill.getTraffic_quantity());

            userStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create promotion", e);
        }
    }

    @Override
    public List<Bill> find() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bill findOneByID(final int id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_BALANCE_BY_ID_QUERY)) {

            statement.setInt(1, id);
            final ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return new Bill(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getTimestamp(3),
                    resultSet.getInt(4)
            );

        } catch (SQLException e) {
            throw new DaoException("Can't find promotion by id", e);
        }
    }


}
