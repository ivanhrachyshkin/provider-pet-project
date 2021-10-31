package by.hrachyshkin.dao.entity_dao.account_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Account;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl extends BaseDao implements AccountDao {

    private static final String CREATE_ACCOUNT_QUERY =
            "INSERT " +
                    "INTO accounts (login, password, role, profile_id, tariff_id, balance_id, blocked) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                    "ON CONFLICT DO NOTHING";


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

    private static final String CHECK_ACCOUNT_IF_REGISTERED =
            "SELECT login, password FROM accounts WHERE login = ? AND password = ?";

    private static final String UPDATE_TARIFF_ID_BY_LOGIN =
            "INSERT INTO accounts (tariff_id) " +
                    "VALUES ?" +
                    "WHERE login = ? " +
                    "ON CONFLICT DO UPDATE";

    public AccountDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(final Account account) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(CREATE_ACCOUNT_QUERY)) {

            statement.setString(1, account.getLogin());
            statement.setString(2, encrypt(account.getPassword()));
            statement.setInt(3, account.getRole());
            statement.setInt(4, account.getProfileId());
            statement.setInt(5, account.getTariffId());
            statement.setInt(6, account.getBalanceId());
            statement.setBoolean(7, account.isBlocked());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create user", e);
        }
    }

    @Override
    public List<Account> find() throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(FIND_ALL_ACCOUNTS_QUERY)) {

            final List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                final Account account = new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getBoolean(8));
                accounts.add(account);
            }
            return accounts;

        } catch (SQLException e) {
            throw new DaoException("Can't find users", e);
        }
    }

    public List<Account> findAllSortedByLogin() throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(FIND_ALL_ACCOUNTS_SORTED_BY_LOGIN_QUERY)) {

            final List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                final Account account = new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getBoolean(8));
                accounts.add(account);
            }
            return accounts;

        } catch (SQLException e) {
            throw new DaoException("Can't find users", e);
        }
    }

    @Override
    public Account findOneByLogin(final String login) throws DaoException {
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
            throw new DaoException("Can't find user by login", e);
        }
    }

    @Override
    public boolean isRegistered(final Account account) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(CHECK_ACCOUNT_IF_REGISTERED)) {

            statement.setString(1, account.getLogin());
            statement.setString(2, account.getPassword());
            final ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new DaoException("User is not registered");
            }

            return true;

        } catch (SQLException e) {
            throw new DaoException("User is not registered", e);
        }
    }

    //updateTariffIdByLoginAndPassword
    @Override
    public Account updateTariffIdByLoginAndPassword(final int tariffId, final Account account) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_TARIFF_ID_BY_LOGIN)) {

            statement.setInt(1, tariffId);
            statement.setString(2, account.getLogin());

            statement.executeUpdate();

            return new Account(account.getLogin(),
                    account.getPassword(),
                    account.getRole(),
                    account.getProfileId(),
                    tariffId,
                    account.getBalanceId(),
                    account.isBlocked());

        } catch (SQLException e) {
            throw new DaoException("User is not registered", e);
        }
    }
}
