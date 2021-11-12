package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Subscription;

import java.util.List;

public interface SubscriptionService extends Service<Subscription> {

    List<Subscription> findAndFilter(final Integer accountId) throws ServiceException, TransactionException;

    void deleteByAccountAndTariffId(final Integer accountId, final Integer tariffId) throws ServiceException, TransactionException;
}
