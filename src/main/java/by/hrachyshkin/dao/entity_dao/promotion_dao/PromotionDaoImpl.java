package by.hrachyshkin.dao.entity_dao.promotion_dao;

import by.hrachyshkin.dao.AbstractDao;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Promotion;
import by.hrachyshkin.entity.criteria.Filter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromotionDaoImpl extends AbstractDao implements PromotionDao {

    private static final String EXISTS_BY_ID_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM promotions " +
                    "WHERE tariff_id = ? AND discount_id = ?" +
                    ")";

    private static final String EXISTS_BY_TARIFF_ID_QUERY =
            "EXISTS (" +
                    "SELECT * " +
                    "FROM promotions " +
                    "WHERE tariff_id = ? " +
                    ")";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO promotions (tariff_id, discount_id) " +
                    "VALUES (?, ?)";

    private static final String FIND_QUERY =
            "SELECT tariff_id, discount_id " +
                    "FROM promotions ";

    private static final String FIND_AND_FILTER_QUERY =
            "SELECT tariff_id, discount_id " +
                    "FROM promotions " +
                    "WHERE ? = ? ";

    private static final String DELETE_QUERY =
            "DELETE " +
                    "FROM promotions " +
                    "WHERE discount_id = ?";

    public PromotionDaoImpl(final Connection connection) {
        super(connection);
    }

    @Override
    public boolean isExistById(final Integer tariffId, final Integer discountId) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, tariffId);
            statement.setInt(1, discountId);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Can't find promotion by id", e);
        }
    }

    @Override
    public boolean isExistByTariffId(final Integer tariffId) throws DaoException {
        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_TARIFF_ID_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            statement.setInt(1, tariffId);
            resultSet.next();
            return resultSet.getBoolean(1);

        } catch (SQLException e) {
            throw new DaoException("Can't find promotion by tariff's id", e);
        }
    }

    public void add(final Promotion promotion) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {

            statement.setInt(1, promotion.getTariffId());
            statement.setInt(2, promotion.getDiscountId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Can't create promotion", e);
        }
    }

    @Override
    public List<Promotion> find() throws DaoException {

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

        } catch (Exception e) {
            throw new DaoException("Can't find required promotions");
        }
    }

    @Override
    public List<Promotion> findAndFilter(final Filter filter) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_QUERY);
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

        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final Integer id) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Can't delete promotion", e);
        }
    }
}
