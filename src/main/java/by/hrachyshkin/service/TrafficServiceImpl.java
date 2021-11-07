package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.traffic_dao.TrafficDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Traffic;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TrafficServiceImpl extends ServiceImpl implements Service<Traffic> {

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
