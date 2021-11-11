package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.entity.Traffic;

import java.util.List;

public interface TrafficService extends Service<Traffic> {

    List<Traffic> findAndSortByDate() throws ServiceException;

    List<Traffic> findAndFilterBySubscriptionId(final Integer subscriptionId) throws ServiceException;

    List<Traffic> findAndFilterAndSort(final Integer subscriptionId) throws ServiceException;

    List<Traffic> findTrafficForTariffPerAccount(final Integer accountId, final Integer tariffId) throws ServiceException;
}
