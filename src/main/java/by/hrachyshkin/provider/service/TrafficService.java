package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Traffic;

import java.util.List;

public interface TrafficService extends Service<Traffic> {

    List<Traffic> findAndSortByDate() throws ServiceException, TransactionException;

    List<Traffic> findAndFilterBySubscriptionId(final Integer subscriptionId) throws ServiceException, TransactionException;

    List<Traffic> findAndFilterAndSort(final Integer subscriptionId) throws ServiceException, TransactionException;

    List<Traffic> findTrafficForTariffPerAccount(final Integer accountId, final Integer tariffId) throws ServiceException, TransactionException;
}