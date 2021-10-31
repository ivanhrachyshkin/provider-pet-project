package by.hrachyshkin.dao.entity_dao.promotion_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.entity.Promotion;

import java.util.List;

public interface PromotionDao extends EntityDao<Promotion> {

    List<Promotion> findAllSortedByDiscountPercent() throws DaoException;

    Promotion findOneByID(int id) throws DaoException;

    Promotion updatePromotionByName(final String name, final Promotion promotion ) throws DaoException;
}
