package by.hrachyshkin.dao.entity_dao.promotion_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.entity.Promotion;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromotionDaoImpl extends BaseDao implements PromotionDao {

    private static final String IS_EXIST_PROMOTION_QUERY =
            "SELECT EXISTS(SELECT 1 FROM accounts WHERE tariff_id = ? AND discount_id = ?) ";

    private static final String CREATE_PROMOTION_QUERY =
            "INSERT " +
                    "INTO promotions (tariff_id, discount_id, date_from, date_to) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT DO NOTHING";

    private static final String FIND_ALL_PROMOTIONS_QUERY =
            "SELECT id, tariff_id, discount_id, date_from, date_to " +
                    "FROM promotions";

    private static final String FIND_ALL_PROMOTIONS_QUERY_WITH_SORT =
            "SELECT id, tariff_id, discount_id, date_from, date_to " +
                    "FROM promotions " +
                    "ORDER BY ? ?";

    private static final String FIND_ALL_PROMOTIONS_QUERY_BY_FILTER =
            "SELECT id, tariff_id, discount_id, date_from, date_to " +
                    "FROM promotions " +
                    "WHERE ? LIKE ?%";

    private static final String FIND_ONE_PROMOTION_QUERY_BY_ID =
            "SELECT id, tariff_id, discount_id, date_from, date_to " +
                    "FROM promotions " +
                    "WHERE id = ?";

    private static final String UPDATE_PROMOTION_QUERY =
            "INSERT INTO promotions (tariff_id, discount_id, date_from, date_to) " +
                    "VALUES ?, ?, ?, ?, ?, ?" +
                    "WHERE id = ? " +
                    "ON CONFLICT DO UPDATE";

    private static final String DELETE_PROMOTION_BY_ID_QUERY =
            "DELETE id, tariff_id, discount_id, date_from, date_to " +
                    "FROM promotions " +
                    "WHERE id = ?";

    public PromotionDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public boolean isExist(int tariffId, int discountId) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(IS_EXIST_PROMOTION_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, tariffId);
            statement.setInt(2, discountId);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Can't find account by id", e);
        }
    }

    @Override
    public void create(final Promotion promotion) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(CREATE_PROMOTION_QUERY)) {

            statement.setInt(1, promotion.getTariffId());
            statement.setInt(2, promotion.getDiscountId());
            statement.setDate(3, promotion.getDateFrom());
            statement.setDate(4, promotion.getDateTo());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create promotion", e);
        }
    }

    @Override
    public List<Promotion> find(final Criteria criteria) throws DaoException {

        try (final Connection connection = dataSource.getConnection()) {

            String query;
            if (criteria.getSorting() != null) {
                query = FIND_ALL_PROMOTIONS_QUERY_WITH_SORT;
            } else if (criteria.getFilter() != null) {
                query = FIND_ALL_PROMOTIONS_QUERY_BY_FILTER;
            } else query = FIND_ALL_PROMOTIONS_QUERY;

            try (final PreparedStatement statement = connection.prepareStatement(query);
                 final ResultSet resultSet = statement.executeQuery()) {

                if (criteria.getSorting() != null) {
                    statement.setString(1, criteria.getSorting().getColumn());
                    statement.setString(2, criteria.getSorting().getDirection().name());
                } else if (criteria.getFilter() != null) {
                    statement.setString(1, criteria.getFilter().getColumn());
                    statement.setString(2, criteria.getFilter().getPattern());
                }

                final List<Promotion> promotions = new ArrayList<>();
                while (resultSet.next()) {
                    final Promotion promotion
                            = new Promotion(
                            resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getDate(3),
                            resultSet.getDate(4));
                    promotions.add(promotion);
                }

                return promotions;
            }
        } catch (Exception e) {
            throw new DaoException("Can't find required promotions", e);
        }
    }

    @Override
    public Promotion findOneById(int id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_ONE_PROMOTION_QUERY_BY_ID);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, id);
            resultSet.next();

            return new Promotion(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getDate(3),
                    resultSet.getDate(4));

        } catch (SQLException e) {
            throw new DaoException("Can't find promotion by id", e);
        }
    }

    @Override
    public void update(final Promotion promotion) throws DaoException {

        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_PROMOTION_QUERY)) {

            statement.setInt(1, promotion.getTariffId());
            statement.setInt(2, promotion.getDiscountId());
            statement.setDate(3, promotion.getDateFrom());
            statement.setDate(4, promotion.getDateTo());

            statement.setInt(5, promotion.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't add promotion", e);
        }
    }

    @Override
    public void delete(final int id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(DELETE_PROMOTION_BY_ID_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete promotion", e);
        }
    }
}
