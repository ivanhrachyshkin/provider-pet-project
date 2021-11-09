package by.hrachyshkin.dao.entity_dao.account_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.account_dao.AccountDao;
import by.hrachyshkin.entity.Account;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private static final String EXISTS_BY_ID_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM accounts " +
                    "WHERE id = ?" +
                    ")";

    private static final String EXISTS_BY_EMAIL_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM accounts " +
                    "WHERE email = ?" +
                    ")";

    private static final String EXISTS_BY_EMAIL_AND_PASSWORD =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM accounts " +
                    "WHERE email = ? AND password = ?" +
                    ")";

    private static final String FIND_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts ";

    private static final String FIND_AND_SORT_BY_NAME_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts " +
                    "ORDER BY name DESC";

    private static final String FIND_ONE_ACCOUNT_BY_ID_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts " +
                    "WHERE id = ?";

    private static final String FIND_ONE_ACCOUNT_BY_EMAIL_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts " +
                    "WHERE email = ?";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO accounts (email, password, role, name, phone, address, balance) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY =
            "UPDATE accounts " +
                    "SET email =?, role =?, name =?, phone=?, address=?, balance =? " +
                    "WHERE id = ?";

    private final Connection connection;

    public AccountDaoImpl(Connection connection) {
        this.connection = connection;
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
            throw new DaoException("Account doesn't exist", e);
        }
    }

    @Override
    public boolean isExistByEmail(final String name) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_EMAIL_QUERY)) {
            statement.setString(1, name);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Account doesn't exist", e);
        }
    }

    @Override
    public boolean isExistByEmailAndPassword(final String email, final String password) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Account doesn't exist", e);
        }
    }

    @Override
    public List<Account> find() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {
            final List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                final Account account = new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        Account.Role.values()[resultSet.getInt(4)],
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getFloat(8));
                accounts.add(account);
            }
            return accounts;
        } catch (Exception e) {
            throw new DaoException("Can't find accounts");
        }
    }

    @Override
    public List<Account> findAndSortByName() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_BY_NAME_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {
            final List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                final Account account = new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        Account.Role.values()[resultSet.getInt(4)],
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getFloat(8));
                accounts.add(account);
            }
            return accounts;
        } catch (Exception e) {
            throw new DaoException("Can't find or sort accounts");
        }
    }


    @Override
    public Account findOneById(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_ONE_ACCOUNT_BY_ID_QUERY)) {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        Account.Role.values()[resultSet.getInt(4)],
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getFloat(8));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find account by id", e);
        }
    }

    @Override
    public Account findOneByEmail(final String email) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_ONE_ACCOUNT_BY_EMAIL_QUERY)) {
            statement.setString(1, email);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return new Account(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        Account.Role.values()[resultSet.getInt(4)],
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getFloat(8));
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find account by email", e);
        }
    }

    @Override
    public void add(final Account account) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {
            statement.setString(1, account.getEmail());
            statement.setString(2, encrypt(account.getPassword()));
            statement.setInt(3, account.getRole().ordinal());
            statement.setString(4, account.getName());
            statement.setString(5, account.getPhone());
            statement.setString(6, account.getAddress());
            statement.setFloat(7, account.getBalance());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't add account", e);
        }
    }

    @Override
    public void update(final Account account) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, account.getEmail());
            statement.setInt(2, account.getRole().ordinal());
            statement.setString(3, account.getName());
            statement.setString(4, account.getPhone());
            statement.setString(5, account.getAddress());
            statement.setFloat(6, account.getBalance());

            statement.setInt(7, account.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't update account", e);
        }
    }

    @Override
    public void delete(final Integer id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    private String encrypt(final String password) {

        return DigestUtils.md5Hex(password.toUpperCase());
    }
}
