package by.hrachyshkin.service;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.account_dao.AccountDao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.entity.Account;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AccountServiceImpl implements Service<Account> {

    private final Transaction transactionImpl;

    public boolean isExistByEmailAndPassword(final String email, final String password) throws ServiceException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            return accountDao.isExistByEmailAndPassword(email, password);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Account> find() throws ServiceException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            return accountDao.find();
        } catch (DaoException | TransactionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Account> findAndSortByName() throws ServiceException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            return accountDao.findAndSortByName();
        } catch (DaoException | TransactionException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Account findOneById(final Integer id) throws ServiceException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            if (!accountDao.isExistById(id)) {
                throw new ServiceException();
            }
            return accountDao.findOneById(id);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Account findOneByEmail(final String email) throws ServiceException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            if (!accountDao.isExistByEmail(email)) {
                throw new ServiceException();
            }
            return accountDao.findOneByEmail(email);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Account account) throws ServiceException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            if (accountDao.isExistByEmail(account.getEmail())) {
                throw new ServiceException();
            }
            accountDao.add(account);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Account account) throws ServiceException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            if (!accountDao.isExistById(account.getId())) {
                throw new ServiceException();
            }
            accountDao.update(account);
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
