package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.*;
import by.hrachyshkin.provider.model.Promotion;
import by.hrachyshkin.provider.service.PromotionService;
import by.hrachyshkin.provider.service.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PromotionServiceImpl.class);
    private final Transaction transactionImpl;
    private final ResourceBundle rb;

    @Override
    public List<Promotion> find() throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method find starts ");
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);
            final List<Promotion> promotions = promotionDao.find();
            LOGGER.debug("method find finish ");
            transactionImpl.commit();
            return promotions;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Promotion> findAndFilterByTariffId(final Integer tariffId) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndFilterByTariffId starts ");
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);
            final List<Promotion> promotions = promotionDao.findAndFilterByTariffId(tariffId);
            LOGGER.debug("method findAndFilterByTariffId finish ");
            transactionImpl.commit();
            return promotions;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Promotion findOneById(final Integer id) throws ServiceException {
        LOGGER.error(rb.getString("promotion.find.one.by.id.unsupported.exception"));
        throw new UnsupportedOperationException(rb.getString("promotion.find.one.by.id.unsupported.exception"));
    }

    @Override
    public void add(final Promotion promotion) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method add starts ");
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);

            if (promotionDao.isExistByTariffAndDiscountId(promotion.getTariffId(), promotion.getDiscountId())) {
                LOGGER.error(rb.getString("promotion.add.added.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("promotion.add.added.exception"));
            }

            promotionDao.add(promotion);
            LOGGER.debug("method add finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Promotion promotion) throws ServiceException {
        LOGGER.error(rb.getString("promotion.update.unsupported.exception"));
        throw new UnsupportedOperationException(rb.getString("promotion.update.unsupported.exception"));
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        LOGGER.error(rb.getString("promotion.delete.unsupported.exception"));
        throw new UnsupportedOperationException(rb.getString("promotion.delete.unsupported.exception"));
    }

    @Override
    public void deleteByTariffAndDiscount(final Integer tariffId, final Integer discountId) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method deleteByTariffAndDiscount starts ");
            final PromotionDao promotionDao = transactionImpl.createDao(DaoKeys.PROMOTION_DAO);

            if (!promotionDao.isExistByTariffAndDiscountId(tariffId, discountId)) {
                LOGGER.error(rb.getString("promotion.delete.by.tariff.and.discount.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("promotion.delete.by.tariff.and.discount.exception"));
            }

            promotionDao.deleteByTariffAndDiscount(tariffId, discountId);
            LOGGER.debug("method deleteByTariffAndDiscount finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
