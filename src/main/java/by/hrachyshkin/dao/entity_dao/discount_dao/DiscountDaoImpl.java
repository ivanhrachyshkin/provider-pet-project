package by.hrachyshkin.dao.entity_dao.discount_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Discount;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDaoImpl extends BaseDao implements DiscountDao {

    String FIND_QUERY =
            "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts ";

    String FIND_AND_FILTER_QUERY =
            "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts " +
                    "WHERE ? LIKE ? ";

    String FIND_AND_SORT_QUERY =
            "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts " +
                    "ORDER BY ? ? ";

    String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts " +
                    "WHERE ? LIKE ? " +
                    "ORDER BY ? ? ";

    String EXISTS_BY_NAME_QUERY =
            "EXISTS (" +
                    "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts " +
                    "WHERE name = ?" +
                    ")";

    String EXISTS_BY_ID_QUERY =
            "EXISTS (" +
                    "SELECT id, name, type, value, date_from, date_to " +
                    "FROM discounts " +
                    "WHERE id = ?" +
                    ")";

    String ADD_QUERY =
            "INSERT " +
                    "INTO discounts (name, type, value, date_from, date_to) " +
                    "VALUES (?, ?, ?, ?, ?)";

    String UPDATE_QUERY =
            "UPDATE discounts " +
                    "SET name = ?, type = ?, value = ?, date_from = ?, date_to = ? " +
                    "WHERE id = ?";

    String DELETE_QUERY =
            "DELETE " +
                    "FROM discounts " +
                    "WHERE id = ?";


    public DiscountDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    public final List<Discount> find() throws DaoException {
        try {
            return executeQuery(
                    FIND_QUERY,
                    preparedStatement -> prepareFind(preparedStatement),
                    resultSet -> extractDiscounts(resultSet));
        } catch (final Exception e) {
            throw new DaoException("Can't find discounts", e);
        }
    }

    public final List<Discount> find(final Filter filter) throws DaoException {
        try {
            return executeQuery(
                    FIND_AND_FILTER_QUERY,
                    preparedStatement -> prepareFind(preparedStatement, filter),
                    resultSet -> extractDiscounts(resultSet));
        } catch (final Exception e) {
            throw new DaoException("Can't find or filter discounts", e);
        }
    }

    public final List<Discount> find(final Sort sort) throws DaoException {
        try {
            return executeQuery(
                    FIND_AND_SORT_QUERY,
                    preparedStatement -> prepareFind(preparedStatement, sort),
                    resultSet -> extractDiscounts(resultSet));
        } catch (final Exception e) {
            throw new DaoException("Can't find or sort discounts", e);
        }
    }

    public final List<Discount> find(final Filter filter, final Sort sort) throws DaoException {
        try {
            return executeQuery(
                    FIND_AND_FILTER_AND_SORT_QUERY,
                    preparedStatement -> prepareFind(preparedStatement, filter, sort),
                    this::extractDiscounts);
        } catch (final Exception e) {
            throw new DaoException("Can't find or filter or sort discounts", e);
        }
    }

    public final boolean exists(final String name) throws DaoException {
        try {
            return executeQuery(
                    EXISTS_BY_NAME_QUERY,
                    preparedStatement -> prepareExists(preparedStatement, name),
                    this::extractExistence);
        } catch (final Exception e) {
            throw new DaoException("Can't check discount by name", e);
        }
    }

    public final boolean exists(final Integer id) throws DaoException {
        try {
            return executeQuery(
                    EXISTS_BY_ID_QUERY,
                    preparedStatement -> prepareExists(preparedStatement, id),
                    this::extractExistence);
        } catch (final Exception e) {
            throw new DaoException("Can't check discount by id", e);
        }
    }

    public final void add(final Discount discount) throws DaoException {
        try {
            executeUpdate(ADD_QUERY, preparedStatement -> prepareAdd(preparedStatement, discount));
        } catch (final Exception e) {
            throw new DaoException("Can't add discount", e);
        }
    }

    public final void update(final Discount discount) throws DaoException {
        try {
            executeUpdate(UPDATE_QUERY, preparedStatement -> prepareUpdate(preparedStatement, discount));
        } catch (final Exception e) {
            throw new DaoException("Can't update discount", e);
        }
    }

    public final void delete(final BigDecimal id) throws DaoException {
        try {
            executeUpdate(DELETE_QUERY, preparedStatement -> prepareDelete(preparedStatement, id));
        } catch (final Exception e) {
            throw new DaoException("Can't delete discount", e);
        }
    }

    @SneakyThrows
    private void prepareFind(final PreparedStatement preparedStatement) {
    }


    private void prepareFind(final PreparedStatement preparedStatement, final Filter filter) {
        preparedStatement.setString(1, filter.getColumn());
        preparedStatement.setString(2, filter.getPattern());
    }

    @SneakyThrows
    private void prepareFind(final PreparedStatement preparedStatement, final Sort sort) {
        preparedStatement.setString(1, sort.getColumn());
        preparedStatement.setString(2, sort.getDirection().name());
    }

    @SneakyThrows
    private void prepareFind(final PreparedStatement preparedStatement, final Filter filter, final Sort sort) {
        preparedStatement.setString(1, filter.getColumn());
        preparedStatement.setString(2, filter.getPattern());
        preparedStatement.setString(3, sort.getColumn());
        preparedStatement.setString(4, sort.getDirection().name());
    }

    @SneakyThrows
    private List<Discount> extractDiscounts(final ResultSet resultSet) {
        final List<Discount> discounts = new ArrayList<>();
        while (resultSet.next()) {
            final Discount discount = new Discount(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    Discount.Type.values()[resultSet.getInt(3)],
                    resultSet.getInt(4),
                    resultSet.getDate(5),
                    resultSet.getDate(6)
            );
            discounts.add(discount);
        }
        return discounts;
    }

    @SneakyThrows
    private void prepareExists(final PreparedStatement preparedStatement, final String name) {
        preparedStatement.setString(1, name);
    }

    @SneakyThrows
    private void prepareExists(final PreparedStatement preparedStatement, final Integer id) {
        preparedStatement.setInt(1, id);
    }

    @SneakyThrows
    private boolean extractExistence(final ResultSet resultSet) {
        return resultSet.next();
    }

    @SneakyThrows
    private void prepareAdd(final PreparedStatement preparedStatement, final Discount discount) {
        preparedStatement.setString(1, discount.getName());
        preparedStatement.setInt(2,  discount.getType().ordinal());
        preparedStatement.setInt(3, discount.getValue());
        preparedStatement.setDate(4, discount.getDateFrom());
        preparedStatement.setDate(5, discount.getDateTo());
    }

    @SneakyThrows
    private void prepareUpdate(final PreparedStatement preparedStatement, final Discount discount) {
        preparedStatement.setString(1, discount.getName());
        preparedStatement.setInt(2,  discount.getType().ordinal());
        preparedStatement.setInt(3, discount.getValue());
        preparedStatement.setDate(4, discount.getDateFrom());
        preparedStatement.setDate(5, discount.getDateTo());
        preparedStatement.setInt(6, discount.getId());
    }

    @SneakyThrows
    private void prepareDelete(final PreparedStatement preparedStatement, final BigDecimal id) {
        preparedStatement.setBigDecimal(1, id);
    }
}
