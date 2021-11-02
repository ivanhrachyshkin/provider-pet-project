package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.*;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
    private final Transaction transactionImpl;
    private final ResourceBundle rb;

    @Override
    public List<Subscription> find() throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method find starts ");
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final List<Subscription> subscriptions = subscriptionDao.find();
            LOGGER.debug("method find finish ");
            transactionImpl.commit();
            return subscriptions;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Subscription> findAndFilterByAccountId(final Integer accountId) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndFilterByAccountId starts ");
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final List<Subscription> subscriptions = subscriptionDao.findAndFilterByAccountId(accountId);
            LOGGER.debug("method findAndFilterByAccountId finish ");
            transactionImpl.commit();
            return subscriptions;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Subscription findOneById(final Integer id) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findOneById starts ");
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final Subscription subscription = subscriptionDao.findOneById(id);
            LOGGER.debug("method findOneById finish ");
            transactionImpl.commit();
            return subscription;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Subscription findOneByAccountIdAndTariffId(final Integer accountId, final Integer tariffId) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findOneByAccountIdAndTariffId starts ");
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final Subscription subscription = subscriptionDao.findOneByAccountIdAndTariffId(accountId, tariffId);
            LOGGER.debug("method findOneByAccountIdAndTariffId finish ");
            transactionImpl.commit();
            return subscription;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public void add(final Subscription subscription) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method add starts ");
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            final TariffDao tariffDao = transactionImpl.createDao(DaoKeys.TARIFF_DAO);

            if (subscriptionDao.isExistByAccountAndTariffId(subscription.getAccountId(), subscription.getTariffId())
                    || !accountDao.isExistById(subscription.getAccountId())
                    || !tariffDao.isExistById(subscription.getTariffId())) {
                LOGGER.error(rb.getString("subscription.add.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("subscription.add.exist.exception"));
            }

            subscriptionDao.add(subscription);
            LOGGER.debug("method add finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void payBillForSubscription(final Integer accountId, final Integer subscriptionIdForBill, final Float value, final LocalDate date) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method payBillForSubscription starts ");
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);

            if (accountId == null || !accountDao.isExistById(accountId)) {
                LOGGER.error(rb.getString("subscription.payBill.account.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("subscription.payBill.account.exist.exception"));
            }

            if (subscriptionIdForBill == null || !billDao.isExists(subscriptionIdForBill, value, date)) {
                LOGGER.error(rb.getString("subscription.payBill.bill.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("subscription.payBill.bill.exist.exception"));
            }

            billDao.updateBillStatus(subscriptionIdForBill, value, date);
            accountDao.updateBalanceForAccountId(accountId, accountDao.findOneById(accountId).getBalance() - value);
            LOGGER.debug("method payBillForSubscription finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(Subscription subscription) throws ServiceException, TransactionException {
        LOGGER.error(rb.getString("subscription.update.unsupported.exception"));
        throw new UnsupportedOperationException(rb.getString("subscription.update.unsupported.exception"));
    }

    @Override
    public void delete(Integer id) throws ServiceException, TransactionException {
        LOGGER.error(rb.getString("subscription.delete.unsupported.exception"));
        throw new UnsupportedOperationException(rb.getString("subscription.delete.unsupported.exception"));
    }

    @Override
    public void delete(final Subscription subscription) throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method delete starts ");
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);
            final TrafficDao trafficDao = transactionImpl.createDao(DaoKeys.TRAFFIC_DAO);

            if (!billDao.isExistsOpenBills(subscription.getId())) {
                LOGGER.error(rb.getString("bill.delete.open.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("bill.delete.open.exception"));
            }

            billDao.delete(subscription.getId());
            trafficDao.delete(subscription.getId());
            subscriptionDao.deleteByAccountIdAndTariffId(subscription.getId());
            LOGGER.debug("method delete finish ");
            transactionImpl.commit();

        } catch (DaoException | TransactionException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
