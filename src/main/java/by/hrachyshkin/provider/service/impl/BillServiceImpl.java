package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.DaoKeys;
import by.hrachyshkin.provider.dao.BillDao;
import by.hrachyshkin.provider.dao.SubscriptionDao;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Bill;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.service.BillService;
import by.hrachyshkin.provider.service.ServiceException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final Transaction transactionImpl;

    @Override
    public List<Bill> find() throws ServiceException, TransactionException {

        try {
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);
            final List<Bill> bills = billDao.find();
            transactionImpl.commit();
            return bills;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Bill> findAndSortByDate() throws ServiceException, TransactionException {

        try {
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);
            final List<Bill> bills = billDao.findAndSortByDate();
            transactionImpl.commit();
            return bills;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Bill> findAndFilterBySubscriptionId(final Integer subscriptionId) throws ServiceException, TransactionException {

        try {
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);
            final List<Bill> bills = billDao.findAndFilterBySubscriptionId(subscriptionId);
            transactionImpl.commit();
            return bills;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Bill> findAndFilterAndSort(final Integer subscriptionId) throws ServiceException, TransactionException {

        try {
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);
            final List<Bill> bills = billDao.findAndFilterAndSort(subscriptionId);
            transactionImpl.commit();
            return bills;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Bill findOneById(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    public List<Bill> findBillsForSubscription(final Integer accountId, final Integer tariffId) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);

            final List<Bill> bills = billDao.find();
            final List<Bill> subscriptionBills = new ArrayList<>();
            final List<Subscription> subscriptions = subscriptionDao.findAndFilterByAccountIdAndTariffId(accountId, tariffId);

            for (Subscription subs : subscriptions) {
                for (Bill bill : bills) {
                    if (subs.getId().equals(bill.getSubscriptionId())) {
                        subscriptionBills.add(bill);
                    }
                }

            }
            transactionImpl.commit();
            return subscriptionBills;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Bill bill) throws ServiceException, TransactionException {

        try {
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);

            if (billDao.isExists(bill.getSubscriptionId(), bill.getValue(), bill.getDate())) {
                transactionImpl.rollback();
                throw new ServiceException("Can't add bill because is already exists");
            }

            billDao.add(bill);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Bill bill) throws ServiceException, TransactionException {
        throw new UnsupportedOperationException("Update operation is not available for bill");
    }

    @Override
    public void delete(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException("Delete operation is not available for bill");
    }
}
