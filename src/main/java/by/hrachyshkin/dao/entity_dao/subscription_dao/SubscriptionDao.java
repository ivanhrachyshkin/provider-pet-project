package by.hrachyshkin.dao.entity_dao.subscription_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Subscription;

import java.util.List;

public interface SubscriptionDao extends Dao<Subscription> {

    boolean isExistById(final Integer id) throws DaoException;

    boolean isExistByAccountId(Integer accountId) throws DaoException;

    List<Subscription> findAndFilter(Integer accountId) throws DaoException;
}
