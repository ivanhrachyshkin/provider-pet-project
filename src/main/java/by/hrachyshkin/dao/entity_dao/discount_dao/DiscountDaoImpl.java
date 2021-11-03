package by.hrachyshkin.dao.entity_dao.discount_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Discount;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDaoImpl extends BaseDao implements DiscountDao {

    private static final String EXISTS_BY_ID_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM discounts " +
                    "WHERE id = ?" +
                    ")";

    private static final String EXISTS_BY_NAME_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM discounts " +
                    "WHERE name = ?" +
                    ")";

    private static final String FIND_QUERY =
            "SELECT id, name, type, value, date_trom, date_to " +
                    "FROM discounts ";

    private static final String FIND_AND_SORT_QUERY =
            "SELECT id, name, type, value, date_trom, date_to " +
                    "FROM discounts " +
                    "ORDER BY ? ? ";

    private static final String FIND_AND_FILTER_QUERY =
            "SELECT id, name, type, value, date_trom, date_to " +
                    "FROM discounts " +
                    "WHERE ? LIKE ? ";

    private static final String FIND_AND_FILTER_AND_SORT_QUERY =
            "SELECT id, name, type, value, date_trom, date_to " +
                    "FROM discounts " +
                    "WHERE ? LIKE ? " +
                    "ORDER BY ? ? ";

    private static final String FIND_ONE_TARIFF_QUERY_BY_ID =
            "SELECT id, name, type, value, date_trom, date_to " +
                    "FROM discounts " +
                    "WHERE id = ?";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO discounts (name, type, speed, price) " +
                    "VALUES (?, ?, ?, ?)";

    private static final String UPDATE_QUERY =
            "INSERT INTO discounts (name, type, value, date_trom, date_to) " +
                    "VALUES ?, ?, ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_QUERY =
            "DELETE " +
                    "FROM discounts " +
                    "WHERE id = ?";

    public DiscountDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean isExistById(final Integer id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Can't find discount by id", e);
        }
    }

    @Override
    public boolean isExistByName(final String name) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_NAME_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, name);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Can't find discount by name", e);
        }
    }

    @Override
    public void add(final Discount discount) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {

            statement.setString(1, discount.getName());
            statement.setInt(2, discount.getType().ordinal());
            statement.setInt(3, discount.getValue());
            statement.setDate(4, discount.getDateFrom());
            statement.setDate(5, discount.getDateTo());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create discount", e);
        }
    }

    @Override
    public List<Discount> find() throws DaoException {

        try (final Connection connection = dataSource.getConnection()) {

            try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
                 final ResultSet resultSet = statement.executeQuery()) {

                final List<Discount> discounts = new ArrayList<>();
                while (resultSet.next()) {
                    final Discount discount = new Discount(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            Discount.Type.values()[resultSet.getInt(3)],
                            resultSet.getInt(4),
                            resultSet.getDate(5),
                            resultSet.getDate(6));
                    discounts.add(discount);
                }

                return discounts;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find required discounts");
        }
    }

    @Override
    public List<Discount> findAndSort(final Sort sort) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, sort.getColumn());
            statement.setString(2, sort.getDirection().name());

            final List<Discount> discounts = new ArrayList<>();
            while (resultSet.next()) {
                final Discount discount = new Discount(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        Discount.Type.values()[resultSet.getInt(3)],
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6));
                discounts.add(discount);
            }

            return discounts;
        } catch (Exception e) {
            throw new DaoException("Can't find or sort discounts");
        }
    }

    @Override
    public List<Discount> findAndFilter(final Filter filter) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, filter.getColumn());
            statement.setString(2, filter.getPattern());

            final List<Discount> discounts = new ArrayList<>();
            while (resultSet.next()) {
                final Discount discount = new Discount(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        Discount.Type.values()[resultSet.getInt(3)],
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6));
                discounts.add(discount);
            }

            return discounts;
        } catch (Exception e) {
            throw new DaoException("Can't find or filter discounts");
        }
    }

    @Override
    public List<Discount> findAndFilterAndSort(final Filter filter, final Sort sort) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_AND_SORT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, filter.getColumn());
            statement.setString(2, filter.getPattern());
            statement.setString(3, sort.getColumn());
            statement.setString(4, sort.getDirection().name());

            final List<Discount> discounts = new ArrayList<>();
            while (resultSet.next()) {
                final Discount discount = new Discount(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        Discount.Type.values()[resultSet.getInt(3)],
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getDate(6));
                discounts.add(discount);
            }

            return discounts;
        } catch (Exception e) {
            throw new DaoException("Can't find or filter or sort discounts");
        }
    }

    @Override
    public Discount findOneById(final Integer id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ONE_TARIFF_QUERY_BY_ID);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();

            return new Discount(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    Discount.Type.values()[resultSet.getInt(3)],
                    resultSet.getInt(4),
                    resultSet.getDate(5),
                    resultSet.getDate(6));

        } catch (SQLException e) {
            throw new DaoException("Can't find discount by id", e);
        }
    }

    @Override
    public void update(final Discount discount) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, discount.getName());
            statement.setInt(2, discount.getType().ordinal());
            statement.setInt(3, discount.getValue());
            statement.setDate(4, discount.getDateFrom());
            statement.setDate(5, discount.getDateTo());

            statement.setInt(6, discount.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add discount", e);
        }
    }

    @Override
    public void delete(final Integer id) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete discount", e);
        }
    }
}
