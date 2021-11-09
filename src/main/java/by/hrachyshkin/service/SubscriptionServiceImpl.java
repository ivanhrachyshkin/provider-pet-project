package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.subscription_dao.SubscriptionDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Subscription;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SubscriptionServiceImpl implements Service<Subscription> {

    private final Transaction transaction;

    public boolean isExist(final Integer id, final Integer accountId, final Integer tariffId) throws ServiceException {

        try {
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);
            if (id != null) {
                return subscriptionDao.isExistById(id);
            } else if (accountId != null) {
                return subscriptionDao.isExistByAccountId(accountId);
            } else return subscriptionDao.isExistByTariffId(tariffId);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Subscription> find() throws ServiceException {

        try {
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);
            return subscriptionDao.find();
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Subscription> findAndFilter(final Integer accountId) throws ServiceException {

        try {
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);
            return subscriptionDao.findAndFilter(accountId);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Subscription findOneById(final Integer id) throws ServiceException {

        try {
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);
            return subscriptionDao.findOneById(id);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public void add(Subscription subscription) throws ServiceException {

        try {
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);
            subscriptionDao.add(subscription);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Subscription subscription) throws ServiceException {

        try {
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);
            subscriptionDao.update(subscription);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {

        try {
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);
            subscriptionDao.delete(id);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
