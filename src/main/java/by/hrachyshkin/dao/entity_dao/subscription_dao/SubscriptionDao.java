package by.hrachyshkin.dao.entity_dao.subscription_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Subscription;
import by.hrachyshkin.entity.criteria.Filter;

import java.util.List;

public interface SubscriptionDao extends Dao<Subscription> {

    boolean isExistById(final Integer id) throws DaoException;

    boolean isExistByAccountId(Integer accountId) throws DaoException;

    boolean isExistByAccountIdAndTariffId(final Integer accountId, final Integer tariffId) throws DaoException;

    List<Subscription> findAndFilter(Filter filter) throws DaoException;
}
