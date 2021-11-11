package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.entity_dao.DaoKeys;
import by.hrachyshkin.provider.dao.entity_dao.subscription_dao.SubscriptionDao;
import by.hrachyshkin.provider.dao.transaction.Transaction;
import by.hrachyshkin.provider.dao.transaction.TransactionException;
import by.hrachyshkin.provider.entity.Subscription;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final Transaction transaction;

    @Override
    public List<Subscription> find() throws ServiceException {

        try {
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);
            return subscriptionDao.find();
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
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
            if (subscriptionDao.isExistByAccountAndTariffId(subscription.getAccountId(), subscription.getTariffId())) {
                throw new ServiceException();
            }
            subscriptionDao.add(subscription);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Subscription subscription) throws ServiceException {
        throw new UnsupportedOperationException();
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
