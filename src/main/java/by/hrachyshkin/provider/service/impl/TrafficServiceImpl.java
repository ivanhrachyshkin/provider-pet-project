package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.DaoKeys;
import by.hrachyshkin.provider.dao.SubscriptionDao;
import by.hrachyshkin.provider.dao.TrafficDao;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.model.Traffic;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.TrafficService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService {

    private final Transaction transactionImpl;

    @Override
    public List<Traffic> find() throws ServiceException, TransactionException {

        try {
            final TrafficDao trafficDao = transactionImpl.createDao(DaoKeys.TRAFFIC_DAO);
            final List<Traffic> traffic = trafficDao.find();
            transactionImpl.commit();
            return traffic;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Traffic> findAndSortByDate() throws ServiceException, TransactionException {

        try {
            final TrafficDao trafficDao = transactionImpl.createDao(DaoKeys.TRAFFIC_DAO);
            List<Traffic> traffics = trafficDao.findAndSortByDate();
            transactionImpl.commit();
            return traffics;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Traffic> findAndFilterBySubscriptionId(final Integer subscriptionId) throws ServiceException, TransactionException {

        try {
            final TrafficDao trafficDao = transactionImpl.createDao(DaoKeys.TRAFFIC_DAO);
            final List<Traffic> traffics = trafficDao.findAndFilterBySubscriptionId(subscriptionId);
            transactionImpl.commit();
            return traffics;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Traffic> findAndFilterAndSort(final Integer subscriptionId) throws ServiceException, TransactionException {

        try {
            final TrafficDao trafficDao = transactionImpl.createDao(DaoKeys.TRAFFIC_DAO);
            List<Traffic> traffics = trafficDao.findAndFilterAndSort(subscriptionId);
            transactionImpl.commit();
            return traffics;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Traffic> findTrafficForTariffPerAccount(final Integer accountId, final Integer tariffId) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final TrafficDao trafficDao = transactionImpl.createDao(DaoKeys.TRAFFIC_DAO);

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
            transactionImpl.commit();
            return subscriptionTraffics;
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Traffic findOneById(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final Traffic traffic) throws ServiceException, TransactionException {

        try {
            final TrafficDao trafficDao = transactionImpl.createDao(DaoKeys.TRAFFIC_DAO);
            if (trafficDao.isExists(traffic)) {
                transactionImpl.rollback();
                throw new ServiceException();
            }
            trafficDao.add(traffic);
            transactionImpl.commit();
        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
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
