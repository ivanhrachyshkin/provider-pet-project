package by.hrachyshkin.dao.entity_dao.promotion_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Promotion;
import by.hrachyshkin.entity.criteria.Filter;

import java.util.List;

public interface PromotionDao extends Dao<Promotion> {

    boolean isExistById(Integer tariffId, Integer discountId) throws DaoException;

    boolean isExistByTariffId(Integer tariffId) throws DaoException;

    List<Promotion> findAndFilter(Filter filter) throws DaoException;
}
