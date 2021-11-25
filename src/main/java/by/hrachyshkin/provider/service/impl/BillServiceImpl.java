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

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final Transaction transactionImpl;
    private final ResourceBundle rb;

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
    public List<Bill> findAndFilterBySubscriptionIdAndSortByDate(final Integer subscriptionId) throws ServiceException, TransactionException {

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

    @Override
    public List<Bill> findBillsForSubscription(final Integer accountId, final Integer tariffId, final Integer offset) throws ServiceException, TransactionException {

        try {
            final SubscriptionDao subscriptionDao = transactionImpl.createDao(DaoKeys.SUBSCRIPTION_DAO);
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);

            final List<Bill> bills = billDao.find();
            final Subscription subscription = subscriptionDao.findOneByAccountIdAndTariffId(accountId, tariffId);

            final List<Bill> subscriptionBills = bills
                    .stream()
                    .filter(bill -> bill.getSubscriptionId().equals(subscription.getId()))
                    .collect(Collectors.toList());

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

            if (bill.getValue() == null
                    || bill.getDate() == null) {
                throw new ServiceException(rb.getString("bill.add.empty.input.exception"));
            }

            if (billDao.isExists(bill.getSubscriptionId(), bill.getValue(), bill.getDate())) {
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("bill.add.exist.exception"));
            }

            if (bill.getValue() < 0) {
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("bill.add.negative.exception"));
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
        throw new UnsupportedOperationException(rb.getString("bill.update.unsupported.exception"));
    }

    @Override
    public void delete(final Integer subscriptionId) throws ServiceException, TransactionException {

        try {
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL_DAO);
            final List<Bill> bills = billDao.findAndFilterAndSort(subscriptionId);

            final List<Boolean> billStatuses = bills.stream()
                    .map(Bill::getStatus)
                    .collect(Collectors.toList());

            if (billStatuses.contains(false)) {
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("bill.delete.unpaid.exception"));
            }

            billDao.delete(subscriptionId);
            transactionImpl.commit();

        } catch (DaoException | TransactionException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
