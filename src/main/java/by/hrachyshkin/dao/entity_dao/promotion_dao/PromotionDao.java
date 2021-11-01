package by.hrachyshkin.dao.entity_dao.promotion_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.entity.Account;
import by.hrachyshkin.entity.Promotion;

public interface PromotionDao extends EntityDao<Promotion> {

    boolean isExist(int tariffId, int discountId) throws DaoException;
}
