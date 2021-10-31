package by.hrachyshkin.dao.entity_dao.promotion_dao;

import by.hrachyshkin.dao.BaseDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Promotion;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionDaoImpl extends BaseDao implements PromotionDao {


    private static final String CREATE_PROMOTION_QUERY =
            "INSERT " +
                    "INTO promotions (name, description, discount_percent) " +
                    "VALUES (?, ?, ?) " +
                    "ON CONFLICT DO NOTHING";

    private static final String FIND_ALL_PROMOTIONS_QUERY =
            "SELECT id, name, description, discount_percent " +
                    "FROM promotions";

    private static final String FIND_ALL_SORTED_BY_DISCOUNT_PERCENT_PROMOTIONS_QUERY =
            "SELECT id, name, description, discount_percent " +
                    "FROM promotions ORDER BY discount_percent";

    private static final String FIND_PROMOTION_BY_ID_QUERY =
            "SELECT id, name, description, discount_percent " +
                    "FROM promotions " +
                    "WHERE id = ?";

    private static final String UPDATE_PROMOTION_BY_NAME =
            "INSERT INTO promotions (name, description, discount_percent) " +
                    "VALUES ?, ?, ?" +
                    "WHERE name = ? " +
                    "ON CONFLICT DO UPDATE";


    public PromotionDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void create(final Promotion promotion) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement userStatement = connection.prepareStatement(CREATE_PROMOTION_QUERY)) {

            userStatement.setString(1, promotion.getName());
            userStatement.setString(2, promotion.getText());
            userStatement.setInt(3, promotion.getDiscount_percent());

            userStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create promotion", e);
        }
    }

    @Override
    public List<Promotion> find() throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(FIND_ALL_PROMOTIONS_QUERY)) {

            final List<Promotion> promotions = new ArrayList<>();
            while (resultSet.next()) {
                final Promotion promotion = new Promotion(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                );
                promotions.add(promotion);
            }
            return promotions;

        } catch (SQLException e) {
            throw new DaoException("Can't find promotions", e);
        }
    }

    public Promotion updatePromotionByName(final String name, final Promotion promotion) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(UPDATE_PROMOTION_BY_NAME)) {

            statement.setString(1, promotion.getName());
            statement.setString(2, promotion.getText());
            statement.setInt(3, promotion.getDiscount_percent());
            statement.setString(4, name);

            statement.executeUpdate();

            return new Promotion(
                    promotion.getId(),
                    promotion.getName(),
                    promotion.getText(),
                    promotion.getDiscount_percent()
            );

        } catch (SQLException e) {
            throw new DaoException("User is not registered", e);
        }
    }

    @Override
    public List<Promotion> findAllSortedByDiscountPercent() throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(FIND_ALL_SORTED_BY_DISCOUNT_PERCENT_PROMOTIONS_QUERY)) {

            final List<Promotion> promotions = new ArrayList<>();
            while (resultSet.next()) {
                final Promotion promotion = new Promotion(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                );
                promotions.add(promotion);
            }
            return promotions;

        } catch (SQLException e) {
            throw new DaoException("Can't find promotions", e);
        }
    }

    public Promotion findOneByID(final int id) throws DaoException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_PROMOTION_BY_ID_QUERY)) {

            statement.setInt(1, id);
            final ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            return new Promotion(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4)
            );

        } catch (SQLException e) {
            throw new DaoException("Can't find promotion by id", e);
        }
    }
}
