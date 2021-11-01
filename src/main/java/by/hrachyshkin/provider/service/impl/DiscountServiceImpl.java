package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.*;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.model.Promotion;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final static Logger LOGGER = LoggerFactory.getLogger(DiscountServiceImpl.class);
    private final Transaction transactionImpl;
    private final ResourceBundle rb;

    @Override
    public List<Discount> find() throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method find starts ");
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            final List<Discount> discounts = discountDao.find();
            LOGGER.debug("method find finish ");
            transactionImpl.commit();
            return discounts;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> findAndSortByValue(final Integer offset) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndSortByValue starts ");
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            final List<Discount> discounts = discountDao.findAndSortByValue(offset);
            LOGGER.debug("method findAndSortByValue finish ");
            transactionImpl.commit();
            return discounts;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> findAndFilterByType(final Discount.Type type, int offset) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndFilterByType starts ");
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            final List<Discount> discounts = discountDao.findAndFilterByType(type, offset);
            LOGGER.debug("method findAndFilterByType finish ");
            transactionImpl.commit();
            return discounts;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> findAndFilterByTypeAndSortByValue(final Discount.Type type) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndFilterByTypeAndSortByValue starts ");
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            final List<Discount> discounts = discountDao.findAndFilterAndSort(type);
            LOGGER.debug("method findAndFilterByTypeAndSortByValue finish ");
            transactionImpl.commit();
            return discounts;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Discount findOneById(final Integer id) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findOneById starts ");
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);

            if (!discountDao.isExistById(id)) {
                LOGGER.error(rb.getString("discount.find.one.by.id.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.find.one.by.id.exist.exception"));
            }

            final Discount discount = discountDao.findOneById(id);
            LOGGER.debug("method findOneById finish ");
            transactionImpl.commit();
            return discount;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> findDiscountsForPromotion(final Integer tariffId) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findDiscountsForPromotion starts ");
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);

            final List<Discount> discounts = new ArrayList<>();
            final List<Promotion> promotions = promotionDao.find();

            for (Promotion promotion : promotions) {
                if (promotion.getTariffId().equals(tariffId)) {
                    final Discount discount = discountDao.findOneById(promotion.getDiscountId());
                    discounts.add(discount);
                }
            }

            LOGGER.debug("method findDiscountsForPromotion finish ");
            transactionImpl.commit();
            return discounts;

        } catch (TransactionException | DaoException e) {
            LOGGER.debug(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Discount discount) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method add starts ");
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);

            if (discount.getName().isEmpty()
                    || discount.getType() == null
                    || discount.getValue() == null
                    || discount.getDateFrom() == null
                    || discount.getDateTo() == null) {
                LOGGER.error(rb.getString("discount.add.empty.input.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.add.empty.input.exception"));
            }

            if (discountDao.isExistByName(discount.getName())) {
                LOGGER.error(rb.getString("discount.add.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.add.exist.exception"));
            }

            if (discount.getDateFrom().isAfter(discount.getDateTo())) {
                LOGGER.error(rb.getString("discount.add.date.invalid.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.add.date.invalid.exception"));
            }

            if (discount.getValue() < 0) {
                LOGGER.error(rb.getString("discount.add.negative.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.add.negative.exception"));
            }

            discountDao.add(discount);
            LOGGER.debug("method add finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Discount discount) throws ServiceException, TransactionException {

        try {
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);

            if (discount.getName().isEmpty()
                    || discount.getType() == null
                    || discount.getValue() == null
                    || discount.getDateFrom() == null
                    || discount.getDateTo() == null) {
                LOGGER.debug("method update starts ");
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.update.empty.input.exception"));
            }

            if (!discountDao.isExistById(discount.getId())) {
                LOGGER.error(rb.getString("discount.update.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.update.exist.exception"));
            }

            if (discountDao.isExistByNotIdAndName(discount.getId(), discount.getName())) {
                LOGGER.error(rb.getString("discount.update.exist.not.id.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.update.exist.not.id.exception"));//
            }

            if (discount.getDateFrom().isAfter(discount.getDateTo())) {
                LOGGER.error(rb.getString("discount.update.date.invalid.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.update.date.invalid.exception"));
            }

            if (discount.getValue() < 0) {
                LOGGER.error(rb.getString("discount.update.negative.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.update.negative.exception"));
            }

            discountDao.update(discount);
            LOGGER.debug("method update finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error("method update starts ");
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method delete starts ");
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);

            if (!discountDao.isExistById(id)) {
                LOGGER.error(rb.getString("discount.delete.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.delete.exist.exception"));
            }
            if (promotionDao.isExistByDiscountId(id)) {
                LOGGER.error(rb.getString("discount.delete.promotions.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("discount.delete.promotions.exception"));
            }

            discountDao.delete(id);
            LOGGER.debug("method delete finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
