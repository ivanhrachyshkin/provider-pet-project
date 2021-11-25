package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Subscription;

import java.util.List;

public interface SubscriptionDao extends Dao<Subscription> {

    boolean isExistById( Integer id) throws DaoException;

    boolean isExistByAccountId(Integer accountId) throws DaoException;

    boolean isExistByTariffId( Integer tariffId) throws DaoException;

    boolean isExistByAccountAndTariffId( Integer accountId,  Integer tariffId) throws DaoException;

    List<Subscription> findAndFilterByAccountId(Integer accountId) throws DaoException;

    Subscription findOneByAccountIdAndTariffId( Integer accountId,  Integer tariffId) throws DaoException;

    void deleteByAccountAndTariffId( Integer accountId,  Integer tariffId) throws DaoException;
}
