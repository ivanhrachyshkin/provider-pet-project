package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.entity_dao.DaoKeys;
import by.hrachyshkin.provider.dao.entity_dao.bill_dao.BillDao;
import by.hrachyshkin.provider.dao.entity_dao.subscription_dao.SubscriptionDao;
import by.hrachyshkin.provider.dao.transaction.Transaction;
import by.hrachyshkin.provider.dao.transaction.TransactionException;
import by.hrachyshkin.provider.entity.Bill;
import by.hrachyshkin.provider.entity.Subscription;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BillServiceImpl implements Service<Bill> {

    private final Transaction transaction;

    @Override
    public List<Bill> find() throws ServiceException {

        try {
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            return billDao.find();
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Bill> findAndSortByDate() throws ServiceException {

        try {
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            return billDao.findAndSortByDate();
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Bill> findAndFilterBySubscriptionId(final Integer subscriptionId) throws ServiceException {

        try {
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            return billDao.findAndFilterBySubscriptionId(subscriptionId);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Bill> findAndFilterAndSort(final Integer subscriptionId) throws ServiceException {

        try {
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            return billDao.findAndFilterAndSort(subscriptionId);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Bill findOneById(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    public List<Bill> findBillsForTariffPerAccount(final Integer accountId, final Integer tariffId) throws ServiceException {

        try {
            final SubscriptionDao subscriptionDao = transaction.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);

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
            return subscriptionBills;
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Bill bill) throws ServiceException {

        try {
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            if (billDao.isExists(bill)) {
                throw new ServiceException();
            }
            billDao.add(bill);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Bill bill) throws ServiceException {

        try {
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            if (billDao.isExists(bill) || !bill.getStatus()) {
                billDao.update(bill);
            } else {
                throw new ServiceException();
            }
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
