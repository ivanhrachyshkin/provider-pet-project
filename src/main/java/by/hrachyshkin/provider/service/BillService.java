package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.entity.Bill;

import java.util.List;

public interface BillService extends Service<Bill> {

    public List<Bill> findAndSortByDate() throws ServiceException;

    List<Bill> findAndFilterBySubscriptionId(final Integer subscriptionId) throws ServiceException;

    List<Bill> findBillsForTariffPerAccount(final Integer accountId, final Integer tariffId) throws ServiceException;

    List<Bill> findAndFilterAndSort(final Integer subscriptionId) throws ServiceException;
}
