package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.account_dao.AccountDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Account;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AccountServiceImpl extends ServiceImpl implements Service<Account> {

    private final Transaction transaction;

    public boolean isExist(final Integer id, final String email, final String password) throws ServiceException {

        try {
            final AccountDao accountDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            if (id != null) {
                return accountDao.isExistById(id);
            } else if (password == null) {
                return accountDao.isExistByEmail(email);
            } else return accountDao.isExistByEmailAndPassword(email, password);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Account account) throws ServiceException {

        try {
            AccountDao accountDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            accountDao.add(account);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Account> find(final Filter filter, final Sort sort) throws ServiceException {

        try {
            final AccountDao accountDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            if (filter != null && sort != null) {
                return accountDao.findAndFilterAndSort(filter, sort);
            } else if (filter != null) {
                return accountDao.findAndFilter(filter);
            } else if (sort != null) {
                return accountDao.findAndSort(sort);
            } else return accountDao.find();

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Account findOneById(final Integer id) throws ServiceException {

        try {
            final AccountDao accountDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            return accountDao.findOneById(id);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Account account) throws ServiceException {

        try {
            final AccountDao accountDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            accountDao.updateStatus(account);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {

        try {
            final AccountDao accountDao = transaction.createDao(DaoKeys.ACCOUNT_DAO);
            accountDao.delete(id);

        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
