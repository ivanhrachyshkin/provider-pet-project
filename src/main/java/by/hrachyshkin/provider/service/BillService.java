package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Bill;

import java.util.List;

public interface BillService extends Service<Bill> {

    List<Bill> findAndSortByDate()
            throws ServiceException, TransactionException;

    List<Bill> findAndFilterBySubscriptionId(Integer subscriptionId)
            throws ServiceException, TransactionException;

    List<Bill> findAndFilterAndSortOffset(Integer subscriptionId, int offset)
            throws ServiceException, TransactionException;
}
