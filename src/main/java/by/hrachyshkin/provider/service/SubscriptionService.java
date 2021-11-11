package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.entity.Subscription;

import java.util.List;

public interface SubscriptionService extends Service<Subscription> {

    List<Subscription> findAndFilter(final Integer accountId) throws ServiceException;
}
