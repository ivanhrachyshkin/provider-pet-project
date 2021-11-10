package by.hrachyshkin.provider.dao.entity_dao.promotion_dao;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.entity.Promotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromotionDaoImpl implements PromotionDao {

    private static final String EXISTS_BY_TARIFF_ID_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM promotions " +
                    "WHERE tariff_id = ? " +
                    ")";

    private static final String EXISTS_BY_DISCOUNT_ID_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM promotions " +
                    "WHERE discount_id = ? " +
                    ")";

    private static final String EXISTS_BY_TARIFF_AND_DISCOUNT_ID_QUERY =
            "SELECT EXISTS (" +
                    "SELECT * " +
                    "FROM promotions " +
                    "WHERE tariff_id = ? AND discount_id = ? " +
                    ")";

    private static final String FIND_QUERY =
            "SELECT tariff_id, discount_id " +
                    "FROM promotions ";

    private static final String FIND_AND_FILTER_QUERY =
            "SELECT tariff_id, discount_id " +
                    "FROM promotions " +
                    "WHERE tariff_id = ? ";

    private static final String ADD_QUERY =
            "INSERT " +
                    "INTO promotions (tariff_id, discount_id) " +
                    "VALUES (?, ?)";

    private static final String DELETE_QUERY =
            "DELETE " +
                    "FROM promotions " +
                    "WHERE tariff_id = ? AND discount_id = ? ";

    private final Connection connection;

    public PromotionDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isExistByTariffId(final Integer tariffId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_TARIFF_ID_QUERY)) {
            statement.setInt(1, tariffId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Promotion doesn't exist", e);
        }
    }

    @Override
    public boolean isExistByDiscountId(final Integer discountId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_DISCOUNT_ID_QUERY)) {
            statement.setInt(1, discountId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Promotion doesn't exist", e);
        }
    }

    @Override
    public boolean isExistByTariffAndDiscountId(final Integer tariffId, final Integer discountId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(EXISTS_BY_TARIFF_AND_DISCOUNT_ID_QUERY)) {
            statement.setInt(1, tariffId);
            statement.setInt(2, discountId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Promotion doesn't exist", e);
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
            throw new DaoException("Can't find promotions");
        }
    }

    @Override
    public List<Promotion> findAndFilterByTariffId(final Integer tariffId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(FIND_AND_FILTER_QUERY)) {
            statement.setInt(1, tariffId);

            try (final ResultSet resultSet = statement.executeQuery()) {
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
            throw new DaoException("Can't find or filter promotions");
        }
    }

    @Override
    public Promotion findOneById(final Integer id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final Promotion promotion) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(ADD_QUERY)) {
            statement.setInt(1, promotion.getTariffId());
            statement.setInt(2, promotion.getDiscountId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't add promotion", e);
        }
    }

    @Override
    public void update(final Promotion promotion) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteByTariffAndDiscount(final Integer tariffId, final Integer discountId) throws DaoException {

        try (final PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, tariffId);
            statement.setInt(2, discountId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't delete promotion", e);
        }
    }
}
