package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.entity_dao.DaoKeys;
import by.hrachyshkin.provider.dao.entity_dao.promotion_dao.PromotionDao;
import by.hrachyshkin.provider.dao.transaction.Transaction;
import by.hrachyshkin.provider.dao.transaction.TransactionException;
import by.hrachyshkin.provider.entity.Promotion;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PromotionServiceImpl implements Service<Promotion> {

    private final Transaction transaction;

    @Override
    public List<Promotion> find() throws ServiceException {

        try {
            final PromotionDao promotionDao = transaction.createDao(DaoKeys.PROMOTION_DAO);
            return promotionDao.find();
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Promotion> findAndFilterByTariffId(final Integer tariffId) throws ServiceException {

        try {
            final PromotionDao promotionDao = transaction.createDao(DaoKeys.PROMOTION_DAO);
            return promotionDao.findAndFilterByTariffId(tariffId);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Promotion findOneById(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final Promotion promotion) throws ServiceException {

        try {
            final PromotionDao promotionDao = transaction.createDao(DaoKeys.PROMOTION_DAO);
            if (promotionDao.isExistByTariffAndDiscountId(promotion.getTariffId(), promotion.getDiscountId())){
                throw new ServiceException();
            }
            promotionDao.add(promotion);
        } catch (TransactionException | DaoException e) {
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

    public void deleteByTariffAndDiscount(final Integer tariffId, final Integer discountId) throws ServiceException {

        try {
            final PromotionDao promotionDao = transaction.createDao(DaoKeys.PROMOTION_DAO);
            if (!promotionDao.isExistByTariffAndDiscountId(tariffId, discountId)) {
                throw new ServiceException();
            }
            promotionDao.deleteByTariffAndDiscount(tariffId, discountId);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
