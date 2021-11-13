package by.hrachyshkin.provider.dao.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.DiscountDao;
import by.hrachyshkin.provider.model.Discount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountDaoImpl implements DiscountDao {

    private static final String EXISTS_BY_ID_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM discounts " +
                    "WHERE id = ?" +
                    ")";

    private static final String EXISTS_BY_NAME_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM discounts " +
                    "WHERE name = ?" +
                    ")";

    private static final String EXISTS_BY_NOT_ID_AND_NAME_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM discounts " +
                    "WHERE id != ? AND name = ?" +
                    ")";

    private static final String FIND_QUERY =
            "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts ";

    private static final String FIND_AND_SORT_BY_VALUE_QUERY =
            "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts " +
                    "ORDER BY value DESC";

    private static final String FIND_AND_FILTER_BY_TYPE_QUERY =
            "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts " +
                    "WHERE type = ? ";

    private static final String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts " +
                    "WHERE type = ? " +
                    "ORDER BY value DESC ";

    private static final String FIND_ONE_BY_ID_QUERY =
            "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts " +
                    "WHERE id = ?";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO discounts (name, type, value, date_from, date_to) " +
                    "VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY =
            "UPDATE discounts " +
                    "SET name = ?, type  = ?, value  = ?, date_from  = ?, date_to = ? " +
                    "WHERE id = ?";

    private static final String DELETE_QUERY =
            "DELETE " +
                    "FROM discounts " +
                    "WHERE id = ?";

    private final Connection connection;

    public DiscountDaoImpl(Connection connection) {
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
            throw new DaoException("Discount doesn't exist by id", e);
        }
    }

    @Override
    public boolean isExistByName(final String name) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_NAME_QUERY)) {
            statement.setString(1, name);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException("Discount doesn't exist by name", e);
        }
    }

    @Override
    public boolean isExistByNotIdAndName(final Integer id, final String name) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_NOT_ID_AND_NAME_QUERY)) {
            statement.setInt(1, id);
            statement.setString(2, name);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException("Discount doesn't exist", e);
        }
    }


    @Override
    public List<Discount> find() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Discount> discounts = new ArrayList<>();
            while (resultSet.next()) {
                final Discount discount = buildDiscount(resultSet);
                discounts.add(discount);
            }
            return discounts;

        } catch (Exception e) {
            throw new DaoException("Can't find discounts");
        }
    }

    public List<Discount> findAndSortByValue() throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_BY_VALUE_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Discount> discounts = new ArrayList<>();
            while (resultSet.next()) {
                final Discount discount = buildDiscount(resultSet);
                discounts.add(discount);
            }
            return discounts;

        } catch (Exception e) {
            throw new DaoException("Can't find or sort by value discounts");
        }
    }

    @Override
    public List<Discount> findAndFilterByType(final Discount.Type type) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_BY_TYPE_QUERY)) {
            statement.setInt(1, type.ordinal());

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Discount> discounts = new ArrayList<>();
                while (resultSet.next()) {
                    final Discount discount = buildDiscount(resultSet);
                    discounts.add(discount);
                }
                return discounts;
            }

        } catch (Exception e) {
            throw new DaoException("Can't find or filter by type discounts");
        }
    }

    @Override
    public List<Discount> findAndFilterAndSort(final Discount.Type type) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_AND_SORT_QUERY)) {
            statement.setInt(1, type.ordinal());

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Discount> discounts = new ArrayList<>();
                while (resultSet.next()) {
                    final Discount discount = buildDiscount(resultSet);
                    discounts.add(discount);
                }
                return discounts;
            }

        } catch (Exception e) {
            throw new DaoException("Can't find or filter by type or sort by value discounts");
        }
    }

    @Override
    public Discount findOneById(final Integer id) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(FIND_ONE_BY_ID_QUERY)) {
            statement.setInt(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return buildDiscount(resultSet);
            }

        } catch (SQLException e) {
            throw new DaoException("Can't find discount by id", e);
        }
    }

    @Override
    public void add(final Discount discount) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {

            statement.setString(1, discount.getName());
            statement.setInt(2, discount.getType().ordinal());
            statement.setInt(3, discount.getValue());
            statement.setDate(4, java.sql.Date.valueOf(discount.getDateFrom()));
            statement.setDate(5, java.sql.Date.valueOf(discount.getDateTo()));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add discount", e);
        }
    }

    @Override
    public void update(final Discount discount) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, discount.getName());
            statement.setInt(2, discount.getType().ordinal());
            statement.setInt(3, discount.getValue());
            statement.setDate(4, Date.valueOf(discount.getDateFrom()));
            statement.setDate(5, Date.valueOf(discount.getDateTo()));

            statement.setInt(6, discount.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't update discount", e);
        }
    }

    @Override
    public void delete(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't delete discount", e);
        }
    }

    private Discount buildDiscount(final ResultSet resultSet) throws SQLException {

        return new Discount(
                resultSet.getInt(1),
                resultSet.getString(2),
                Discount.Type.values()[resultSet.getInt(3)],
                resultSet.getInt(4),
                resultSet.getDate(5).toLocalDate(),
                resultSet.getDate(6).toLocalDate());
    }
}
