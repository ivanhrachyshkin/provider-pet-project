package by.hrachyshkin.dao.entity_dao.promotion_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Promotion;

import java.util.List;

public interface PromotionDao extends Dao<Promotion> {

    boolean isExistByTariffId(Integer tariffId) throws DaoException;

    boolean isExistByDiscountId(final Integer discountId) throws DaoException;

    List<Promotion> findAndFilterByTariffId(Integer tariffId) throws DaoException;

   void deleteByTariffAndDiscount(final Integer tariffId, final Integer discountId) throws DaoException;
}
