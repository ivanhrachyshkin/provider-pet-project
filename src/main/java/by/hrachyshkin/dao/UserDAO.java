package by.hrachyshkin.dao;

import by.hrachyshkin.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO {

    // todo if exist +
    // todo authenthificatable password
    // todo filter/sort changed find all parameters;
    // todo update;
    // todo findById +
    //todo depend - driver postgresql
    //todo lib delete
    //todo find alls pagination

    private static final String INIT_QUERY =
            "CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY," +
                    "login VARCHAR(255) UNIQUE NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                    "name VARCHAR(255) NOT NULL," +
                    "surname VARCHAR(255) NOT NULL," +
                    "email VARCHAR(255)," +
                    "passport VARCHAR(255)" +
                    ")";

    private static final String CREATE_QUERY =
            "INSERT " +
                    "INTO users (login, password, name, surname, email, passport) " +
                    "VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";

    private static final String FIND_ALL_QUERY =
            "SELECT id, login, name, surname, email, passport FROM users";

    private static final String FIND_ONE_BY_LOGIN_QUERY =
            "SELECT id, login, name, surname, email, passport FROM users WHERE login = ?";

    private static final String FIND_ALL_SORTED_BY_SURNAME_QUERY =
            "SELECT id, login, name, surname, email, passport FROM users " +
                    "ORDER BY surname, login";



    public UserDAO(final DataSource dataSource) {
        super(dataSource);
    }

    public void init() throws DAOException {

        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {

            statement.executeUpdate(INIT_QUERY);

        } catch (SQLException e) {
            throw new DAOException("Can't init users", e);
        }
    }

    public void create(final User user) throws DAOException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {

            int i = 1;

            statement.setString(i++, user.getLogin());
            statement.setString(i++, encrypt(user.getPassword()));
            statement.setString(i++, user.getName());
            statement.setString(i++, user.getSurname());
            statement.setString(i++, user.getEmail());
            statement.setString(i, user.getPassport());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can't create user", e);
        }
    }

    public List<User> findAll() throws DAOException {

        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY)) {

            final List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                final User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6));
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            throw new DAOException("Can't find users", e);
        }
    }

    public User findOneByLogin(final String login) throws DAOException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ONE_BY_LOGIN_QUERY)) {

            statement.setString(1, login);
            final ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6));

        } catch (SQLException e) {
            throw new DAOException("Can't create user", e);
        }
    }

    public List<User> findAllSortedBySurname() throws DAOException {

        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(FIND_ALL_SORTED_BY_SURNAME_QUERY)) {

            final List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                final User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6));
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            throw new DAOException("Can't find users", e);
        }
    }
}
