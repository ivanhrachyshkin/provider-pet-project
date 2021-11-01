package by.hrachyshkin.dao.entity_dao.account_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Account;
import by.hrachyshkin.entity.Criteria;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl extends BaseDao implements AccountDao {

    private static final String IS_EXIST_ACCOUNT_QUERY =
            "SELECT EXISTS(SELECT 1 FROM accounts WHERE name = ?) ";

    private static final String CREATE_ACCOUNT_QUERY =
            "INSERT " +
                    "INTO accounts (name, email, password, phone, role, balance) " +
                    "VALUES (?, ?, ?, ?, ?, ?) " +
                    "ON CONFLICT DO NOTHING";

    private static final String FIND_ALL_ACCOUNTS_QUERY =
            "SELECT id, name, email, password, phone, role, balance " +
                    "FROM accounts";

    private static final String FIND_ALL_ACCOUNTS_QUERY_WITH_SORT =
            "SELECT id, name, email, password, phone, role, balance " +
                    "FROM accounts " +
                    "ORDER BY ? ?";

    private static final String FIND_ALL_ACCOUNTS_QUERY_BY_FILTER =
            "SELECT id, name, email, password, phone, role, balance " +
                    "FROM accounts " +
                    "WHERE ? LIKE ?%";

    private static final String FIND_ONE_ACCOUNT_QUERY_BY_ID =
            "SELECT id, name, email, password, phone, role, balance " +
                    "FROM accounts " +
                    "WHERE id = ?";

    private static final String UPDATE_ACCOUNT_QUERY =
            "INSERT INTO accounts (name, email, password, phone, role, balance) " +
                    "VALUES ?, ?, ?, ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_ACCOUNT_BY_ID_QUERY =
            "DELETE id, name, email, password, phone, role, balance " +
                    "FROM accounts " +
                    "WHERE id = ?";

    public AccountDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean isExist(final String name) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(IS_EXIST_ACCOUNT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, name);
            resultSet.next();
           return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Can't find account by id", e);
        }
    }

    @Override
    public void create(final Account account) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(CREATE_ACCOUNT_QUERY)) {

            statement.setString(1, account.getName());
            statement.setString(2, account.getEmail());
            statement.setString(3, encrypt(account.getPassword()));
            statement.setString(4, account.getPhone());
            statement.setInt(5, account.getRole().ordinal());
            statement.setDouble(6, account.getBalance());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create account", e);
        }
    }

    @Override
    public List<Account> find(final Criteria criteria) throws DaoException {

        try (final Connection connection = dataSource.getConnection()) {

            String query;
            if (criteria.getSorting() != null) {
                query = FIND_ALL_ACCOUNTS_QUERY_WITH_SORT;
            } else if (criteria.getFilter() != null) {
                query = FIND_ALL_ACCOUNTS_QUERY_BY_FILTER;
            } else query = FIND_ALL_ACCOUNTS_QUERY;

            try (final PreparedStatement statement = connection.prepareStatement(query);
                 final ResultSet resultSet = statement.executeQuery()) {

                if (criteria.getSorting() != null) {
                    statement.setString(1, criteria.getSorting().getColumn());
                    statement.setString(2, criteria.getSorting().getDirection().name());
                } else if (criteria.getFilter() != null) {
                    statement.setString(1, criteria.getFilter().getColumn());
                    statement.setString(2, criteria.getFilter().getPattern());
                }


                final List<Account> accounts = new ArrayList<>();
                while (resultSet.next()) {
                    final Account account = new Account(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            Account.Role.values()[resultSet.getInt(6)],
                            resultSet.getDouble(5));
                    accounts.add(account);
                }

                return accounts;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find required accounts");
        }
    }

    @Override
    public Account findOneById(final int id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ONE_ACCOUNT_QUERY_BY_ID);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();

            return new Account(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    Account.Role.values()[resultSet.getInt(6)],
                    resultSet.getDouble(5));

        } catch (SQLException e) {
            throw new DaoException("Can't find account by id", e);
        }
    }

    @Override
    public void update(final Account account) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT_QUERY)) {

            statement.setString(1, account.getName());
            statement.setString(2, account.getEmail());
            statement.setString(3, account.getPassword());
            statement.setString(4, account.getPhone());
            statement.setInt(5, account.getRole().ordinal());
            statement.setDouble(6, account.getBalance());

            statement.setInt(7, account.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add account", e);
        }
    }

    @Override
    public void delete(final int id) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_ACCOUNT_BY_ID_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete account", e);
        }
    }
}
