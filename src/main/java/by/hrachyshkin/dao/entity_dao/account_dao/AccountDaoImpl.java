package by.hrachyshkin.dao.entity_dao.account_dao;

import by.hrachyshkin.dao.AbstractDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Account;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl extends AbstractDao implements AccountDao {


    private static final String EXISTS_BY_ID_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM accounts " +
                    "WHERE id = ?" +
                    ")";

    private static final String EXISTS_BY_EMAIL_AND_PASSWORD =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM accounts " +
                    "WHERE email = ? AND password = ?" +
                    ")";

    private static final String EXISTS_BY_EMAIL_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM accounts " +
                    "WHERE email = ?" +
                    ")";

    private static final String FIND_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts ";

    private static final String FIND_AND_SORT_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts " +
                    "ORDER BY ? ? ";

    private static final String FIND_AND_FILTER_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts " +
                    "WHERE ? LIKE ? ";

    private static final String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts " +
                    "WHERE ? LIKE ? " +
                    "ORDER BY ? ? ";

    private static final String FIND_ONE_ACCOUNT_QUERY_BY_ID =
            "SELECT id, email, password, role, name, phone, address, balance " +
                    "FROM accounts " +
                    "WHERE id = ?";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO accounts (email, password, role, name, phone, address, balance) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY =
            "INSERT INTO accounts (email, password, role, name, phone, address, balance) " +
                    "VALUES ?, ?, ?, ?, ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_QUERY =
            "DELETE " +
                    "FROM accounts " +
                    "WHERE id = ?";

    public AccountDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public boolean isExistById(final Integer id) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Required account doesn't exist", e);
        }
    }

    @Override
    public boolean isExistByEmailAndPassword(final String email, final String password) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_EMAIL_AND_PASSWORD);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, email);
            statement.setString(2, password);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Required account doesn't exist", e);
        }
    }

    @Override
    public boolean isExistByEmail(final String name) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_EMAIL_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, name);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Required account doesn't exist", e);
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
            throw new DaoException("Can't create account", e);
        }
    }

    @Override
    public List<Account> find() throws DaoException {

            try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
                 final ResultSet resultSet = statement.executeQuery()) {

                final List<Account> accounts = new ArrayList<>();
                while (resultSet.next()) {
                    final Account account = new Account(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            Account.Role.values()[resultSet.getInt(3)],
                            resultSet.getString(4),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getFloat(6));
                    accounts.add(account);
                }

                return accounts;
        } catch (Exception e) {
            throw new DaoException("Can't find accounts");
        }
    }

    @Override
    public List<Account> findAndSort(final Sort sort) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, sort.getColumn());
            statement.setString(2, sort.getDirection().name());

            final List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                final Account account = new Account(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        Account.Role.values()[resultSet.getInt(3)],
                        resultSet.getString(4),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getFloat(6));
                accounts.add(account);
            }

            return accounts;
        } catch (Exception e) {
            throw new DaoException("Can't find or sort accounts");
        }
    }

    @Override
    public List<Account> findAndFilter(final Filter filter) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, filter.getColumn());
            statement.setString(2, filter.getPattern());

            final List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                final Account account = new Account(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        Account.Role.values()[resultSet.getInt(3)],
                        resultSet.getString(4),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getFloat(6));
                accounts.add(account);
            }

            return accounts;
        } catch (Exception e) {
            throw new DaoException("Can't find or filter accounts");
        }
    }

    @Override
    public List<Account> findAndFilterAndSort(final Filter filter, final Sort sort) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_AND_SORT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, filter.getColumn());
            statement.setString(2, filter.getPattern());
            statement.setString(3, sort.getColumn());
            statement.setString(4, sort.getDirection().name());

            final List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                final Account account = new Account(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        Account.Role.values()[resultSet.getInt(3)],
                        resultSet.getString(4),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getFloat(6));
                accounts.add(account);
            }

            return accounts;
        } catch (Exception e) {
            throw new DaoException("Can't find or filter or sort accounts");
        }
    }

    @Override
    public Account findOneById(final Integer id) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(FIND_ONE_ACCOUNT_QUERY_BY_ID);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();

            return new Account(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Account.Role.values()[resultSet.getInt(3)],
                    resultSet.getString(4),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getFloat(6));

        } catch (SQLException e) {
            throw new DaoException("Can't find account by id", e);
        }
    }

    @Override
    public void update(final Account account) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, account.getEmail());
            statement.setString(2, account.getPassword());
            statement.setInt(3, account.getRole().ordinal());
            statement.setString(4, account.getName());
            statement.setString(5, account.getPhone());
            statement.setString(6, account.getAddress());
            statement.setFloat(7, account.getBalance());

            statement.setInt(8, account.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add account", e);
        }
    }

    @Override
    public void delete(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete account", e);
        }
    }
}
