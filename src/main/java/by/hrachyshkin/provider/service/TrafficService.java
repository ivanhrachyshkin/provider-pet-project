package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Traffic;

import java.util.List;

public interface TrafficService extends Service<Traffic> {

    List<Traffic> findAndSortByDate() throws ServiceException, TransactionException;

    List<Traffic> findAndFilterBySubscriptionId(final Integer subscriptionId) throws ServiceException, TransactionException;

    List<Traffic> findAndFilterAndSort(final Integer subscriptionId) throws ServiceException, TransactionException;

    List<Traffic> findTrafficForSubscription(final Integer accountId, final Integer tariffId, Integer offset) throws ServiceException, TransactionException;
}
