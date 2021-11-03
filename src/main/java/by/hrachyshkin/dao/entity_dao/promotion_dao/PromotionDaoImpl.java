package by.hrachyshkin.dao.entity_dao.promotion_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Promotion;
import by.hrachyshkin.entity.Tariff;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromotionDaoImpl extends BaseDao implements PromotionDao {

    private static final String EXISTS_BY_ID_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM promotions " +
                    "WHERE tariff_id = ? AND discount_id = ?" +
                    ")";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO promotions (tariff_id, discount_id) " +
                    "VALUES (?, ?)";

    private static final String FIND_QUERY =
            "SELECT tariff_id, discount_id " +
                    "FROM promotions ";

    private static final String FIND_AND_SORT_QUERY =
            "SELECT tariff_id, discount_id " +
                    "FROM promotions " +
                    "ORDER BY ? ? ";

    private static final String FIND_AND_FILTER_QUERY =
            "SELECT tariff_id, discount_id " +
                    "FROM promotions " +
                    "WHERE ? = ? ";

    private static final String UPDATE_QUERY =
            "INSERT INTO promotions (tariff_id, discount_id) " +
                    "VALUES ?, ?";

    private static final String DELETE_QUERY =
            "DELETE " +
                    "FROM promotions " +
                    "WHERE discount_id = ?";

    public PromotionDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean isExistById(final Integer tariffId, final Integer discountId) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, tariffId);
            statement.setInt(1, discountId);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Can't find tariff by id", e);
        }
    }

    public void add(final Promotion promotion) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {

            statement.setInt(1, promotion.getTariffId());
            statement.setInt(2, promotion.getDiscountId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create tariff", e);
        }
    }

    @Override
    public List<Promotion> find() throws DaoException {

        try (final Connection connection = dataSource.getConnection()) {

            try (final PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
                 final ResultSet resultSet = statement.executeQuery()) {

                final List<Promotion> promotions = new ArrayList<>();
                while (resultSet.next()) {
                    final Promotion promotion = new Promotion(
                            resultSet.getInt(1),
                            resultSet.getInt(2));
                    promotions.add(promotion);
                }

                return promotions;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find required promotions");
        }
    }

    @Override
    public List<Promotion> findAndSort(final Sort sort) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_AND_SORT_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, sort.getColumn());
            statement.setString(2, sort.getDirection().name());

            final List<Promotion> promotions = new ArrayList<>();
            while (resultSet.next()) {
                final Promotion promotion = new Promotion(
                        resultSet.getInt(1),
                        resultSet.getInt(2));
                promotions.add(promotion);
            }

            return promotions;
        } catch (Exception e) {
            throw new DaoException("Can't find or sort promotions");
        }
    }

    @Override
    public List<Promotion> findAndFilter(final Filter filter) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setString(1, filter.getColumn());
            statement.setString(2, filter.getPattern());

            final List<Promotion> promotions = new ArrayList<>();
            while (resultSet.next()) {
                final Promotion promotion = new Promotion(
                        resultSet.getInt(1),
                        resultSet.getInt(2));
                promotions.add(promotion);
            }

            return promotions;
        } catch (Exception e) {
            throw new DaoException("Can't find or filter promotions");
        }
    }


    @Override
    public Promotion findOneById(final Integer id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(final Promotion promotion) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setInt(1, promotion.getTariffId());
            statement.setInt(2, promotion.getDiscountId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add promotion", e);
        }
    }

    @Override
    public void delete(final Integer id) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete tariff", e);
        }
    }
}
