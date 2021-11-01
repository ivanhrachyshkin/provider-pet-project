package by.hrachyshkin.provider.dao.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.AccountDao;
import by.hrachyshkin.provider.model.Account;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    private static final String EXISTS_BY_NOT_ID_AND_EMAIL_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM accounts " +
                    "WHERE id != ? AND email = ?" +
                    ")";

    private static final String FIND_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts ";

    private static final String FIND_AND_SORT_BY_NAME_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts " +
                    "ORDER BY name ASC " +
                    "LIMIT 5 OFFSET ?";

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

    private static final String UPDATE_BALANCE_FOR_ACCOUNT_ID_QUERY =
            "UPDATE accounts " +
                    "SET balance = ? " +
                    "WHERE id = ?";

    private final Connection connection;
    private final ResourceBundle rb;

    public AccountDaoImpl(Connection connection, ResourceBundle rb) {
        this.connection = connection;
        this.rb = rb;
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
            throw new DaoException(rb.getString("account.exist.exception"), e);
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
            throw new DaoException(rb.getString("account.exist.by.email.exception"), e);
        }
    }

    @Override
    public boolean isExistByEmailAndPassword(final String email, final String password) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, encrypt(password));

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("account.exist.by.email.and.password.exception"), e);
        }
    }

    @Override
    public boolean isExistByNotIdAndEmail(final Integer id, final String email) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_NOT_ID_AND_EMAIL_QUERY)) {
            statement.setInt(1, id);
            statement.setString(2, email);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("account.exist.exception"), e);
        }
    }

    @Override
    public List<Account> find() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                final Account account = buildAccount(resultSet);
                accounts.add(account);
            }
            return accounts;

        } catch (Exception e) {
            throw new DaoException(rb.getString("account.find.exception"), e);
        }
    }

    @Override
    public List<Account> findAndSortByName(final Integer offset) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_BY_NAME_QUERY)) {
            statement.setInt(1, offset);

            try (final ResultSet resultSet = statement.executeQuery()){

                final List<Account> accounts = new ArrayList<>();
                while (resultSet.next()) {
                    final Account account = buildAccount(resultSet);
                    accounts.add(account);
                }
                return accounts;
            }
        } catch (Exception e) {
            throw new DaoException(rb.getString("account.find.or.sort.by.name.exception"), e);
        }
    }


    @Override
    public Account findOneById(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_ONE_ACCOUNT_BY_ID_QUERY)) {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return buildAccount(resultSet);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("account.find.by.id.exception"), e);
        }
    }

    @Override
    public Account findOneByEmail(final String email) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_ONE_ACCOUNT_BY_EMAIL_QUERY)) {
            statement.setString(1, email);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return buildAccount(resultSet);
            }

        } catch (SQLException e) {
            throw new DaoException(rb.getString("account.find.by.email.exception"), e);
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
            throw new DaoException(rb.getString("account.add.exception"), e);
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
            throw new DaoException(rb.getString("account.update.exception"), e);
        }
    }

    @Override
    public void delete(final Integer id) throws DaoException {
        throw new UnsupportedOperationException(rb.getString("account.delete.unsupported.exception"));
    }

    @Override
    public void updateBalanceForAccountId(final Integer accountId, final Float sum) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(UPDATE_BALANCE_FOR_ACCOUNT_ID_QUERY)) {

            statement.setFloat(1, sum);
            statement.setInt(2, accountId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(rb.getString("account.update.balance.exception"), e);
        }
    }

    private String encrypt(final String password) {
        return DigestUtils.md5Hex(password);
    }

    private Account buildAccount(final ResultSet resultSet) throws SQLException {

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
}