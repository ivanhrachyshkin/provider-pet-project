package by.hrachyshkin.provider.dao.entity_dao.subscription_dao;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.entity_dao.Dao;
import by.hrachyshkin.provider.entity.Subscription;

import java.util.List;

public interface SubscriptionDao extends Dao<Subscription> {

    boolean isExistById(final Integer id) throws DaoException;

    boolean isExistByAccountId(Integer accountId) throws DaoException;

    boolean isExistByTariffId(final Integer tariffId) throws DaoException;

    boolean isExistByAccountAndTariffId(final Integer accountId, final Integer tariffId) throws DaoException;

    List<Subscription> findAndFilterByAccountIdAndTariffId(final Integer accountId, final Integer tariffId) throws DaoException;

    List<Subscription> findAndFilter(Integer accountId) throws DaoException;
}
