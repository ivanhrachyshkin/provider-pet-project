package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.DaoKeys;
import by.hrachyshkin.provider.dao.DiscountDao;
import by.hrachyshkin.provider.dao.PromotionDao;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.model.Promotion;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.ServiceException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final Transaction transactionImpl;

    @Override
    public List<Discount> find() throws ServiceException, TransactionException {

        try {
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            final List<Discount> discounts = discountDao.find();
            transactionImpl.commit();
            return discounts;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> findAndSortByValue() throws ServiceException, TransactionException {

        try {
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            final List<Discount> discounts = discountDao.findAndSortByValue();
            transactionImpl.commit();
            return discounts;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> findAndFilterByType(final Discount.Type type) throws ServiceException, TransactionException {

        try {
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            final List<Discount> discounts = discountDao.findAndFilterByType(type);
            transactionImpl.commit();
            return discounts;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> findAndFilterAndSort(final Discount.Type type) throws ServiceException, TransactionException {

        try {
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            final List<Discount> discounts = discountDao.findAndFilterAndSort(type);
            transactionImpl.commit();
            return discounts;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Discount findOneById(final Integer id) throws ServiceException, TransactionException {

        try {
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            if (!discountDao.isExistById(id)) {
                transactionImpl.rollback();
                throw new ServiceException("Can't find discount by id");
            }
            final Discount discount = discountDao.findOneById(id);
            transactionImpl.commit();
            return discount;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Discount> findDiscountsForTariff(final Integer tariffId) throws ServiceException, TransactionException {

        try {
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
            transactionImpl.commit();
            return discounts;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Discount discount) throws ServiceException, TransactionException {

        try {
            DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            if (discountDao.isExistByName(discount.getName())) {
                throw new ServiceException("Can't add discount");
            }
            discountDao.add(discount);
            transactionImpl.commit();
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Discount discount) throws ServiceException, TransactionException {

        try {
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            if (!discountDao.isExistById(discount.getId())) {
                throw new ServiceException("Can't update discount");
            }
            if (discountDao.isExistByNotIdAndName(discount.getId(), discount.getName())) {
                throw new ServiceException("Can't update discount");
            }
            discountDao.update(discount);
            transactionImpl.commit();
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException, TransactionException {

        try {
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);
            final DiscountDao discountDao = transactionImpl.createDao(DaoKeys.DISCOUNT_DAO);
            if (!discountDao.isExistById(id) || promotionDao.isExistByDiscountId(id)) {
                throw new ServiceException("Can't delete discount");
            }
            discountDao.delete(id);
            transactionImpl.commit();
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
