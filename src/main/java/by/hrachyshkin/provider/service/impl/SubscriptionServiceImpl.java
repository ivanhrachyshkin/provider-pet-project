package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.*;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.SubscriptionService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final Transaction transactionImpl;
    private final ResourceBundle rb;

    @Override
    public List<Subscription> find() throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            List<Subscription> subscriptions = subscriptionDao.find();
            transactionImpl.commit();
            return subscriptions;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Subscription> findAndFilter(final Integer accountId) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final List<Subscription> subscriptions = subscriptionDao.findAndFilterByAccountId(accountId);
            transactionImpl.commit();
            return subscriptions;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Subscription findOneById(final Integer id) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final Subscription subscription = subscriptionDao.findOneById(id);
            transactionImpl.commit();
            return subscription;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public void add(final Subscription subscription) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);

            if (subscriptionDao.isExistByAccountAndTariffId(subscription.getAccountId(), subscription.getTariffId())) {
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("subscription.add.exist.exception"));
            }

            subscriptionDao.add(subscription);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void payBill(final Integer accountId, final Integer subscriptionIdForBill, final Float value, final LocalDate date) throws ServiceException, TransactionException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);

            if (!accountDao.isExistById(accountId)) {
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("subscription.payBill.account.exist.exception"));
            }

            if (!billDao.isExists(subscriptionIdForBill, value, date)) {
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("subscription.payBill.bill.exist.exception"));
            }

            billDao.updateBillStatus(subscriptionIdForBill, value, date);
            accountDao.updateBalanceForAccountId(accountId, accountDao.findOneById(accountId).getBalance() - value);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Subscription subscription) throws ServiceException {
        throw new UnsupportedOperationException(rb.getString("subscription.update.unsupported.exception"));
    }

    @Override
    public void delete(Integer id) throws ServiceException, TransactionException {
        throw new UnsupportedOperationException(rb.getString("subscription.delete.unsupported.exception"));
    }

    @Override
    public void deleteByAccountAndTariffId(final Integer accountId, final Integer tariffId) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            subscriptionDao.deleteByAccountAndTariffId(accountId, tariffId);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
