package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Bill;

import java.util.List;

public interface BillService extends Service<Bill> {

    public List<Bill> findAndSortByDate() throws ServiceException, TransactionException;

    List<Bill> findAndFilterBySubscriptionId(final Integer subscriptionId) throws ServiceException, TransactionException;

    List<Bill> findBillsForTariffPerAccount(final Integer accountId, final Integer tariffId) throws ServiceException, TransactionException;

    List<Bill> findAndFilterAndSort(final Integer subscriptionId) throws ServiceException, TransactionException;
}
