package by.hrachyshkin.dao.entity_dao.account_dao;

import by.hrachyshkin.dao.BaseDao;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public final List<Discount> find(final Page page) {
        try {
            return executeQuery(
                    FIND_QUERY,
                    preparedStatement -> prepareFind(preparedStatement, page),
                    resultSet -> extractDiscounts(resultSet));
        } catch (final Exception e) {
            throw new RepositoryException(FIND_ERROR, e);
        }
    }

    public final List<Discount> find(final Filter filter, final Page page) {
        try {
            return executeQuery(
                    FIND_AND_FILTER_QUERY,
                    preparedStatement -> prepareFind(preparedStatement, filter, page),
                    resultSet -> extractDiscounts(resultSet));
        } catch (final Exception e) {
            throw new RepositoryException(FIND_AND_FILTER_ERROR, e);
        }
    }

    public final List<Discount> find(final Sort sort, final Page page) {
        try {
            return executeQuery(
                    FIND_AND_SORT_QUERY,
                    preparedStatement -> prepareFind(preparedStatement, sort, page),
                    resultSet -> extractDiscounts(resultSet));
        } catch (final Exception e) {
            throw new RepositoryException(FIND_AND_SORT_ERROR, e);
        }
    }

    public final List<Discount> find(final Filter filter, final Sort sort, final Page page) {
        try {
            return executeQuery(
                    FIND_AND_FILTER_AND_SORT_QUERY,
                    preparedStatement -> prepareFind(preparedStatement, filter, sort, page),
                    this::extractDiscounts);
        } catch (final Exception e) {
            throw new RepositoryException(FIND_AND_FILTER_AND_SORT_ERROR, e);
        }
    }

    public final boolean exists(final String name) {
        try {
            return executeQuery(
                    EXISTS_BY_NAME_QUERY,
                    preparedStatement -> prepareExists(preparedStatement, name),
                    this::extractExistence);
        } catch (final Exception e) {
            throw new RepositoryException(EXISTS_BY_NAME_ERROR, e);
        }
    }

    public final boolean exists(final BigDecimal id) {
        try {
            return executeQuery(
                    EXISTS_BY_ID_QUERY,
                    preparedStatement -> prepareExists(preparedStatement, id),
                    this::extractExistence);
        } catch (final Exception e) {
            throw new RepositoryException(EXISTS_BY_ID_ERROR, e);
        }
    }

    public final void add(final Discount discount) {
        try {
            executeUpdate(ADD_QUERY, preparedStatement -> prepareAdd(preparedStatement, discount));
        } catch (final Exception e) {
            throw new RepositoryException(ADD_ERROR, e);
        }
    }

    public final void update(final Discount discount) {
        try {
            executeUpdate(UPDATE_QUERY, preparedStatement -> prepareUpdate(preparedStatement, discount));
        } catch (final Exception e) {
            throw new RepositoryException(UPDATE_ERROR, e);
        }
    }

    public final void delete(final BigDecimal id) {
        try {
            executeUpdate(DELETE_QUERY, preparedStatement -> prepareDelete(preparedStatement, id));
        } catch (final Exception e) {
            throw new RepositoryException(DELETE_ERROR, e);
        }
    }

    @SneakyThrows
    private void prepareFind(final PreparedStatement preparedStatement, final Page page) {
        preparedStatement.setLong(FIND_QUERY__PAGE_SIZE_INDEX, page.getSize());
        preparedStatement.setLong(FIND_QUERY__PAGE_NUMBER_INDEX, page.getNumber());
    }

    @SneakyThrows
    private void prepareFind(final PreparedStatement preparedStatement, final Filter filter, final Page page) {
        preparedStatement.setString(FIND_AND_FILTER_QUERY__FILTER_COLUMN_INDEX, filter.getColumn());
        preparedStatement.setString(FIND_AND_FILTER_QUERY__FILTER_PATTERN_INDEX, filter.getPattern());
        preparedStatement.setLong(FIND_AND_FILTER_QUERY__PAGE_SIZE_INDEX, page.getSize());
        preparedStatement.setLong(FIND_AND_FILTER_QUERY__PAGE_NUMBER_INDEX, page.getNumber());
    }

    @SneakyThrows
    private void prepareFind(final PreparedStatement preparedStatement, final Sort sort, final Page page) {
        preparedStatement.setString(FIND_AND_SORT_QUERY__SORT_COLUMN_INDEX, sort.getColumn());
        preparedStatement.setString(FIND_AND_SORT_QUERY__SORT_DIRECTION_INDEX, sort.getDirection().name());
        preparedStatement.setLong(FIND_AND_SORT_QUERY__PAGE_SIZE_INDEX, page.getSize());
        preparedStatement.setLong(FIND_AND_SORT_QUERY__PAGE_NUMBER_INDEX, page.getNumber());
    }

    @SneakyThrows
    private void prepareFind(final PreparedStatement preparedStatement, final Filter filter, final Sort sort, final Page page) {
        preparedStatement.setString(FIND_AND_FILTER_AND_SORT_QUERY__FILTER_COLUMN_INDEX, filter.getColumn());
        preparedStatement.setString(FIND_AND_FILTER_AND_SORT_QUERY__FILTER_PATTERN_INDEX, filter.getPattern());
        preparedStatement.setString(FIND_AND_FILTER_AND_SORT_QUERY__SORT_COLUMN_INDEX, sort.getColumn());
        preparedStatement.setString(FIND_AND_FILTER_AND_SORT_QUERY__SORT_DIRECTION_INDEX, sort.getDirection().name());
        preparedStatement.setLong(FIND_AND_FILTER_AND_SORT_QUERY__PAGE_SIZE_INDEX, page.getSize());
        preparedStatement.setLong(FIND_AND_FILTER_AND_SORT_QUERY__PAGE_NUMBER_INDEX, page.getNumber());
    }

    @SneakyThrows
    private List<Discount> extractDiscounts(final ResultSet resultSet) {
        final List<Discount> discounts = new ArrayList<>();
        while (resultSet.next()) {
            final Discount discount = new Discount(
                    resultSet.getBigDecimal(DISCOUNT_ID_LABEL),
                    resultSet.getString(DISCOUNT_NAME_LABEL),
                    Type.values()[resultSet.getInt(DISCOUNT_TYPE_LABEL)],
                    resultSet.getInt(DISCOUNT_VALUE_LABEL)
            );
            discounts.add(discount);
        }
        return discounts;
    }

    @SneakyThrows
    private void prepareExists(final PreparedStatement preparedStatement, final String name) {
        preparedStatement.setString(EXISTS_BY_NAME_QUERY__NAME_INDEX, name);
    }

    @SneakyThrows
    private void prepareExists(final PreparedStatement preparedStatement, final BigDecimal id) {
        preparedStatement.setBigDecimal(EXISTS_BY_ID_QUERY__ID_INDEX, id);
    }

    @SneakyThrows
    private boolean extractExistence(final ResultSet resultSet) {
        return resultSet.next();
    }

    @SneakyThrows
    private void prepareAdd(final PreparedStatement preparedStatement, final Discount discount) {
        preparedStatement.setString(ADD_QUERY__NAME_INDEX, discount.getName());
        preparedStatement.setInt(ADD_QUERY__TYPE_INDEX, discount.getType().ordinal());
        preparedStatement.setInt(ADD_QUERY__VALUE_INDEX, discount.getValue());
    }

    @SneakyThrows
    private void prepareUpdate(final PreparedStatement preparedStatement, final Discount discount) {
        preparedStatement.setString(UPDATE_QUERY__NAME_INDEX, discount.getName());
        preparedStatement.setInt(UPDATE_QUERY__TYPE_INDEX,  discount.getType().ordinal());
        preparedStatement.setInt(UPDATE_QUERY__VALUE_INDEX, discount.getValue());
        preparedStatement.setBigDecimal(UPDATE_QUERY__ID_INDEX, discount.getId());
    }

    @SneakyThrows
    private void prepareDelete(final PreparedStatement preparedStatement, final BigDecimal id) {
        preparedStatement.setBigDecimal(DELETE_QUERY__ID_INDEX, id);
    }
}
