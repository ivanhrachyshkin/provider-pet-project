package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.DaoKeys;
import by.hrachyshkin.provider.dao.PromotionDao;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Promotion;
import by.hrachyshkin.provider.service.PromotionService;
import by.hrachyshkin.provider.service.ServiceException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final Transaction transactionImpl;

    @Override
    public List<Promotion> find() throws ServiceException, TransactionException {

        try {
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);
            final List<Promotion> promotions = promotionDao.find();
            transactionImpl.commit();
            return promotions;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Promotion> findAndFilterByTariffId(final Integer tariffId) throws ServiceException, TransactionException {

        try {
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);
            final List<Promotion> promotions = promotionDao.findAndFilterByTariffId(tariffId);
            transactionImpl.commit();
            return promotions;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Promotion findOneById(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final Promotion promotion) throws ServiceException, TransactionException {

        try {
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);

            if (promotionDao.isExistByTariffAndDiscountId(promotion.getTariffId(), promotion.getDiscountId())) {
                transactionImpl.rollback();
                throw new ServiceException("Discount is already added to current tariff");
            }

            promotionDao.add(promotion);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Promotion promotion) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteByTariffAndDiscount(final Integer tariffId, final Integer discountId) throws ServiceException, TransactionException {

        try {
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);

            if (!promotionDao.isExistByTariffAndDiscountId(tariffId, discountId)) {
                transactionImpl.rollback();
                throw new ServiceException("There is no such discount for current tariff");
            }

            promotionDao.deleteByTariffAndDiscount(tariffId, discountId);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
