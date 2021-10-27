package by.hrachyshkin.dao.entity_dao.user_dao;

import by.hrachyshkin.dao.BaseDAO;
import by.hrachyshkin.dao.DAOException;
import by.hrachyshkin.entity.Account;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends BaseDAO implements UserDAO {

    private static final String CREATE_ACCOUNT_QUERY =
            "INSERT " +
                    "INTO accounts (login, password, role, profile_id, tariff_id, balance_id, blocked) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                    "ON CONFLICT DO NOTHING;";


    private static final String FIND_ALL_ACCOUNTS_QUERY =
            "SELECT id, login, password, role, profile_id, tariff_id, balance_id, blocked " +
                    "FROM accounts";

    private static final String FIND_ALL_ACCOUNTS_SORTED_BY_LOGIN_QUERY =
            "SELECT id, login, password, role, profile_id, tariff_id, balance_id, blocked " +
                    "FROM accounts ORDER BY login";

    private static final String FIND_ACCOUNT_BY_LOGIN_QUERY =
            "SELECT id, login, password, role, profile_id, tariff_id, balance_id, blocked " +
                    "FROM accounts " +
                    "WHERE login = ?";

    public UserDAOImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(Account account) throws DAOException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement userStatement = connection.prepareStatement(CREATE_ACCOUNT_QUERY)) {

            userStatement.setString(1, account.getLogin());
            userStatement.setString(2, encrypt(account.getPassword()));
            userStatement.setInt(3, account.getRole());
            userStatement.setInt(4, account.getProfileId());
            userStatement.setInt(5, account.getTariffId());
            userStatement.setInt(6, account.getBalanceId());
            userStatement.setBoolean(7, account.isBlocked());

            userStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Can't create user", e);
        }
    }

    @Override
    public List<Account> findAll() throws DAOException {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSetUsers = statement.executeQuery(FIND_ALL_ACCOUNTS_QUERY)) {

            final List<Account> accounts = new ArrayList<>();
            while (resultSetUsers.next()) {
                final Account account = new Account(
                        resultSetUsers.getInt(1),
                        resultSetUsers.getString(2),
                        resultSetUsers.getString(3),
                        resultSetUsers.getInt(4),
                        resultSetUsers.getInt(5),
                        resultSetUsers.getInt(6),
                        resultSetUsers.getInt(7),
                        resultSetUsers.getBoolean(8));
                accounts.add(account);
            }
            return accounts;

        } catch (SQLException e) {
            throw new DAOException("Can't find users", e);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    public List<Account> findAllSortedByName() throws DAOException {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSetUsers = statement.executeQuery(FIND_ALL_ACCOUNTS_SORTED_BY_LOGIN_QUERY)) {

            final List<Account> accounts = new ArrayList<>();
            while (resultSetUsers.next()) {
                final Account account = new Account(
                        resultSetUsers.getInt(1),
                        resultSetUsers.getString(2),
                        resultSetUsers.getString(3),
                        resultSetUsers.getInt(4),
                        resultSetUsers.getInt(5),
                        resultSetUsers.getInt(6),
                        resultSetUsers.getInt(7),
                        resultSetUsers.getBoolean(8));
                accounts.add(account);
            }
            return accounts;

        } catch (SQLException e) {
            throw new DAOException("Can't find users", e);
        }
    }

    @Override
    public Account findOneByLogin(final String login) throws DAOException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ACCOUNT_BY_LOGIN_QUERY)) {

            statement.setString(1, login);
            final ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return new Account(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getInt(6),
                    resultSet.getInt(7),
                    resultSet.getBoolean(8));

        } catch (SQLException e) {
            throw new DAOException("Can't find user by login", e);
        }
    }
}
