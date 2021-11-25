package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Bill;

import java.time.LocalDate;
import java.util.List;

public interface BillService extends Service<Bill> {

    public List<Bill> findAndSortByDate() throws ServiceException, TransactionException;

    List<Bill> findBillsForSubscription(final Integer accountId, final Integer tariffId, Integer offset) throws ServiceException, TransactionException;

    List<Bill> findAndFilterBySubscriptionIdAndSortByDate(final Integer subscriptionId) throws ServiceException, TransactionException;
}
