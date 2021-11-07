package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.bill_dao.BillDao;
import by.hrachyshkin.dao.entity_dao.subscription_dao.SubscriptionDao;
import by.hrachyshkin.dao.entity_dao.tariff_dao.TariffDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Bill;
import by.hrachyshkin.entity.Subscription;
import by.hrachyshkin.entity.Tariff;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BillServiceImpl extends ServiceImpl implements Service<Bill> {

    private final Transaction transaction;

    public boolean isExistBySubscriptionId(final Integer subscriptionId) throws ServiceException {

        try {
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            return billDao.isExistBySubscriptionId(subscriptionId);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

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


    @Override
    public void update(final Bill bill) throws ServiceException {

        try {
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            billDao.update(bill);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Bill bill) throws ServiceException {

        try {
            BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            billDao.add(bill);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
