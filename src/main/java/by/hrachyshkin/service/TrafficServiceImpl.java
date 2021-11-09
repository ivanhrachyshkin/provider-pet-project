package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.subscription_dao.SubscriptionDao;
import by.hrachyshkin.dao.entity_dao.traffic_dao.TrafficDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Subscription;
import by.hrachyshkin.entity.Traffic;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TrafficServiceImpl implements Service<Traffic> {

    private final Transaction transaction;


    public boolean isExistBySubscriptionId(final Integer subscriptionId) throws ServiceException {

        try {
            final TrafficDao trafficDao = transaction.createDao(DaoKeys.TRAFFIC_DAO);
            return trafficDao.isExistBySubscriptionId(subscriptionId);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Traffic> find() throws ServiceException {

        try {
            final TrafficDao trafficDao = transaction.createDao(DaoKeys.TRAFFIC_DAO);
            return trafficDao.find();
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Traffic> findAndSortByDate() throws ServiceException {

        try {
            final TrafficDao trafficDao = transaction.createDao(DaoKeys.TRAFFIC_DAO);
            return trafficDao.findAndSortByDate();
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Traffic> findAndFilterBySubscriptionId(final Integer subscriptionId) throws ServiceException {

        try {
            final TrafficDao trafficDao = transaction.createDao(DaoKeys.TRAFFIC_DAO);
            return trafficDao.findAndFilterBySubscriptionId(subscriptionId);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Traffic> findAndFilterAndSort(final Integer subscriptionId) throws ServiceException {

        try {
            final TrafficDao trafficDao = transaction.createDao(DaoKeys.TRAFFIC_DAO);
            return trafficDao.findAndFilterAndSort(subscriptionId);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Traffic> findTrafficForTariffPerAccount(final Integer accountId, final Integer tariffId) throws ServiceException {

        try {
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final TrafficDao trafficDao = transaction.createDao(DaoKeys.TRAFFIC_DAO);

            final List<Traffic> traffics = trafficDao.find();
            final List<Traffic> subscriptionTraffics = new ArrayList<>();
            final List<Subscription> subscriptions = subscriptionDao.findAndFilterByAccountIdAndTariffId(accountId, tariffId);

            for (Subscription subs : subscriptions) {
                for (Traffic traffic : traffics) {
                    if (subs.getId().equals(traffic.getSubscriptionId())) {
                        subscriptionTraffics.add(traffic);
                    }
                }

            }
            return subscriptionTraffics;

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Traffic findOneById(final Integer id) throws ServiceException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final Traffic traffic) throws ServiceException {

        try {
            final TrafficDao trafficDao = transaction.createDao(DaoKeys.TRAFFIC_DAO);
            trafficDao.add(traffic);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Traffic traffic) throws ServiceException {

        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final Integer id) throws ServiceException {

        throw new UnsupportedOperationException();
    }
}
