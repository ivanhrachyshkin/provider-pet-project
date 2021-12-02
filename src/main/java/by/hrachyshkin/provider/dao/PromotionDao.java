package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Promotion;

import java.util.List;

public interface PromotionDao extends Dao<Promotion> {

    boolean isExistByTariffId(Integer tariffId) throws DaoException;

    boolean isExistByDiscountId(Integer discountId) throws DaoException;

    boolean isExistByTariffAndDiscountId(Integer tariffId, Integer discountId)
            throws DaoException;

    List<Promotion> findAndFilterByTariffId(Integer tariffId)
            throws DaoException;

    void deleteByTariffAndDiscount(Integer tariffId, Integer discountId)
            throws DaoException;
}
