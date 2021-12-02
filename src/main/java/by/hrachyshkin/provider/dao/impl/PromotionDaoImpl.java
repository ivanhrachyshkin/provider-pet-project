package by.hrachyshkin.provider.dao.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.PromotionDao;
import by.hrachyshkin.provider.model.Promotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PromotionDaoImpl implements PromotionDao {

    private static final String EXISTS_BY_TARIFF_ID_QUERY =
            "SELECT EXISTS ("
                    + "SELECT * "
                    + "FROM promotions "
                    + "WHERE tariff_id = ? "
                    + ")";

    private static final String EXISTS_BY_DISCOUNT_ID_QUERY =
            "SELECT EXISTS ("
                    + "SELECT * "
                    + "FROM promotions "
                    + "WHERE discount_id = ? "
                    + ")";

    private static final String EXISTS_BY_TARIFF_AND_DISCOUNT_ID_QUERY =
            "SELECT EXISTS ("
                    + "SELECT * "
                    + "FROM promotions "
                    + "WHERE tariff_id = ? AND discount_id = ? "
                    + ")";

    private static final String FIND_QUERY =
            "SELECT tariff_id, discount_id "
                    + "FROM promotions ";

    private static final String FIND_AND_FILTER_QUERY =
            "SELECT tariff_id, discount_id "
                    + "FROM promotions "
                    + "WHERE tariff_id = ? ";

    private static final String ADD_QUERY =
            "INSERT "
                    + "INTO promotions (tariff_id, discount_id) "
                    + "VALUES (?, ?)";

    private static final String DELETE_QUERY =
            "DELETE "
                    + "FROM promotions "
                    + "WHERE tariff_id = ? AND discount_id = ? ";

    private final Connection connection;
    private final ResourceBundle rb;

    public PromotionDaoImpl(final Connection connection,
                            final ResourceBundle rb) {
        this.connection = connection;
        this.rb = rb;
    }

    @Override
    public boolean isExistByTariffId(final Integer tariffId)
            throws DaoException {

        try (final PreparedStatement statement =
                     connection.prepareStatement(EXISTS_BY_TARIFF_ID_QUERY)) {
            statement.setInt(1, tariffId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(
                    rb.getString("promotion.exist.by.tariff.id"
                            + ".exception"), e);
        }
    }

    @Override
    public boolean isExistByDiscountId(final Integer discountId)
            throws DaoException {

        try (final PreparedStatement statement =
                     connection.prepareStatement(EXISTS_BY_DISCOUNT_ID_QUERY)) {
            statement.setInt(1, discountId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(
                    rb.getString("promotion.exist.by.discount.id"
                            + ".exception"), e);
        }
    }

    @Override
    public boolean isExistByTariffAndDiscountId(final Integer tariffId,
                                                final Integer discountId)
            throws DaoException {

        try (final PreparedStatement statement = connection
                .prepareStatement(EXISTS_BY_TARIFF_AND_DISCOUNT_ID_QUERY)) {
            statement.setInt(1, tariffId);
            statement.setInt(2, discountId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new DaoException(
                    rb.getString("promotion.exist.by.tariff.id."
                            + "and.discount.id.exception"), e);
        }
    }

    @Override
    public List<Promotion> find() throws DaoException {

        try (final PreparedStatement statement = connection
                .prepareStatement(FIND_QUERY);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<Promotion> promotions = new ArrayList<>();
            while (resultSet.next()) {
                final Promotion promotion = buildPromotion(resultSet);
                promotions.add(promotion);
            }
            return promotions;

        } catch (Exception e) {
            throw new DaoException(
                    rb.getString("promotion.find.exception"), e);
        }
    }

    @Override
    public List<Promotion> findAndFilterByTariffId(final Integer tariffId)
            throws DaoException {

        try (final PreparedStatement statement = connection
                .prepareStatement(FIND_AND_FILTER_QUERY)) {
            statement.setInt(1, tariffId);

            try (final ResultSet resultSet = statement.executeQuery()) {
                final List<Promotion> promotions = new ArrayList<>();
                while (resultSet.next()) {
                    final Promotion promotion = buildPromotion(resultSet);
                    promotions.add(promotion);
                }
                return promotions;
            }

        } catch (Exception e) {
            throw new DaoException(
                    rb.getString("promotion.find.or.filter.by.tariff.id"
                            + ".exception"), e);
        }
    }

    @Override
    public Promotion findOneById(final Integer id) throws DaoException {
        throw new UnsupportedOperationException(
                rb.getString("promotion.find.one.by.id.unsupported"
                        + ".exception"));
    }

    @Override
    public void add(final Promotion promotion) throws DaoException {

        try (final PreparedStatement statement = connection
                .prepareStatement(ADD_QUERY)) {
            statement.setInt(1, promotion.getTariffId());
            statement.setInt(2, promotion.getDiscountId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(
                    rb.getString("promotion.add.exception"), e);
        }
    }

    @Override
    public void update(final Promotion promotion) throws DaoException {
        throw new UnsupportedOperationException(
                rb.getString("promotion.update.unsupported.exception"));
    }

    @Override
    public void delete(Integer id) throws DaoException {
        throw new UnsupportedOperationException(
                rb.getString("promotion.delete.unsupported.exception"));
    }

    @Override
    public void deleteByTariffAndDiscount(final Integer tariffId,
                                          final Integer discountId)
            throws DaoException {

        try (final PreparedStatement statement =
                     connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, tariffId);
            statement.setInt(2, discountId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(
                    rb.getString("promotion.delete.by.tariff.and.discount"
                            + ".exception"), e);
        }
    }

    private Promotion buildPromotion(final ResultSet resultSet)
            throws SQLException {

        return new Promotion(
                resultSet.getInt(1),
                resultSet.getInt(2));
    }
}
