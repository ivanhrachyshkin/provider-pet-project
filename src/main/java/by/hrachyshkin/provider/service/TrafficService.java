package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Traffic;

import java.util.List;

public interface TrafficService extends Service<Traffic> {

    List<Traffic> findAndSortByDate()
            throws ServiceException, TransactionException;

    List<Traffic> findAndFilterBySubscriptionId(Integer subscriptionId)
            throws ServiceException, TransactionException;

    List<Traffic> findAndFilterAndSortOffset(Integer subscriptionId, int offset)
            throws ServiceException, TransactionException;
}
