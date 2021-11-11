package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.DaoKeys;
import by.hrachyshkin.provider.dao.SubscriptionDao;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.SubscriptionService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final Transaction transactionImpl;

    @Override
    public List<Subscription> find() throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            List<Subscription> subscriptions = subscriptionDao.find();
            transactionImpl.commit();
            return subscriptions;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Subscription> findAndFilter(final Integer accountId) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final List<Subscription> subscriptions = subscriptionDao.findAndFilter(accountId);
            transactionImpl.commit();
            return subscriptions;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Subscription findOneById(final Integer id) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final Subscription subscription = subscriptionDao.findOneById(id);
            transactionImpl.commit();
            return subscription;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public void add(Subscription subscription) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            if (subscriptionDao.isExistByAccountAndTariffId(subscription.getAccountId(), subscription.getTariffId())) {
                transactionImpl.rollback();
                throw new ServiceException();
            }
            subscriptionDao.add(subscription);
            transactionImpl.commit();
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Subscription subscription) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final Integer id) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            subscriptionDao.delete(id);
            transactionImpl.commit();
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
