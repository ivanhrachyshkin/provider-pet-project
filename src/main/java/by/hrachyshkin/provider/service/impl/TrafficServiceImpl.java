package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.DaoKeys;
import by.hrachyshkin.provider.dao.SubscriptionDao;
import by.hrachyshkin.provider.dao.TrafficDao;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Bill;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.model.Traffic;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.TrafficService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService {

    private final Transaction transactionImpl;
    private final ResourceBundle rb;

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
    public List<Traffic> findAndFilterBySubscriptionIdAndSortByDate(final Integer subscriptionId) throws ServiceException, TransactionException {

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
    public List<Traffic> findTrafficForSubscription(final Integer accountId, final Integer tariffId, final Integer offset) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final TrafficDao trafficDao = transactionImpl.createDao(DaoKeys.TRAFFIC_DAO);

            final List<Traffic> traffics = trafficDao.find();
            final Subscription subscription = subscriptionDao.findOneByAccountIdAndTariffId(accountId, tariffId);

            final List<Traffic> subscriptionTraffics = traffics
                    .stream()
                    .filter(traffic -> traffic.getSubscriptionId().equals(subscription.getId()))
                    .collect(Collectors.toList());

            transactionImpl.commit();
            return subscriptionTraffics;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Traffic findOneById(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException(rb.getString("traffic.find.one.by.id.unsupported.exception"));
    }

    @Override
    public void add(final Traffic traffic) throws ServiceException, TransactionException {

        try {
            final TrafficDao trafficDao = transactionImpl.createDao(DaoKeys.TRAFFIC_DAO);

            if (traffic.getValue() == null
                    || traffic.getDate() == null) {
                throw new ServiceException(rb.getString("traffic.add.empty.input.exception"));
            }

            if (trafficDao.isExists(traffic)) {
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("traffic.add.exist.exception"));
            }

            if (traffic.getValue() < 0) {
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("traffic.add.negative.exception"));
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
        throw new UnsupportedOperationException(rb.getString("traffic.update.unsupported.exception"));
    }

    @Override
    public void delete(final Integer subscriptionId) throws ServiceException, TransactionException {

        try {
            final TrafficDao trafficDao = transactionImpl.createDao(DaoKeys.TRAFFIC_DAO);

            trafficDao.delete(subscriptionId);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
