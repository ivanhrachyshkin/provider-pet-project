package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.bill_dao.BillDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Bill;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;
import lombok.RequiredArgsConstructor;

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
    public void add(final Bill bill) throws ServiceException {

        try {
            BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            billDao.add(bill);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Bill> find(final Filter filter, final Sort sort) throws ServiceException {


        try {
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            if (filter != null && sort != null) {
                return billDao.findAndFilterAndSort(filter, sort);
            } else if (filter != null) {
                return billDao.findAndFilter(filter);
            } else if (sort != null) {
                return billDao.findAndSort(sort);
            } else return billDao.find();

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
            billDao.updateStatus(bill);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {

        try {
            final BillDao billDao = transaction.createDao(DaoKeys.BILL_DAO);
            billDao.delete(id);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
