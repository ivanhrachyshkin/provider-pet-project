package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Subscription;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SubscriptionServiceImpl extends ServiceImpl implements Service<Subscription> {

    private final Transaction transaction;

    @Override
    public List<Subscription> find() throws ServiceException {

        try {
            Dao<Subscription> accountDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);

            return accountDao.find();

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
