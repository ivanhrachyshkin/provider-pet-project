package by.hrachyshkin.dao.entity_dao.subscription_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.entity.Subscription;

public interface SubscriptionDao extends EntityDao<Subscription> {

    boolean isExist(int accountId, int tariff_id) throws DaoException;
}
