package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.discount_dao.DiscountDao;
import by.hrachyshkin.dao.entity_dao.promotion_dao.PromotionDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Discount;
import by.hrachyshkin.entity.Promotion;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DiscountServiceImpl implements Service<Discount> {

    private final Transaction transaction;

    public boolean isExist(final Integer id) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            return discountDao.isExistById(id);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> find() throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            return discountDao.find();
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Discount> findAndSortByValue() throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            return discountDao.findAndSortByValue();

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Discount> findAndFilterByType(final Discount.Type type) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            return discountDao.findAndFilterByType(type);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Discount> findAndFilterAndSort(final Discount.Type type) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            return discountDao.findAndFilterAndSort(type);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Discount findOneById(final Integer id) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            return discountDao.findOneById(id);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Discount> findDiscountsForTariff(final Integer tariffId) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.TARIFF_DAO);
            final PromotionDao promotionDao = transaction.createDao(DaoKeys.PROMOTION_DAO);

            final List<Discount> discounts = new ArrayList<>();
            final List<Promotion> promotions = promotionDao.find();

            for (Promotion promotion : promotions) {
                if (promotion.getTariffId().equals(tariffId)) {
                    final Discount discount = discountDao.findOneById(promotion.getTariffId());
                    discounts.add(discount);
                }
            }
            return discounts;
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Discount discount) throws ServiceException {

        try {
            DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            discountDao.add(discount);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Discount discount) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            discountDao.update(discount);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {

        try {
            final DiscountDao discountDao = transaction.createDao(DaoKeys.DISCOUNT_DAO);
            discountDao.delete(id);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
