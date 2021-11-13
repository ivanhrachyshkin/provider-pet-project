package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.DaoException;
import by.hrachyshkin.provider.dao.DaoKeys;
import by.hrachyshkin.provider.dao.AccountDao;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final Transaction transactionImpl;

    public boolean isExistByEmailAndPassword(final String email, final String password) throws ServiceException, TransactionException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            final boolean value = accountDao.isExistByEmailAndPassword(email, password);
            transactionImpl.commit();
            return value;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Account> find() throws ServiceException, TransactionException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            final List<Account> accounts = accountDao.find();
            transactionImpl.commit();
            return accounts;

        } catch (DaoException | TransactionException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Account> findAndSortByName() throws ServiceException, TransactionException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);
            List<Account> andSortByName = accountDao.findAndSortByName();
            transactionImpl.commit();
            return andSortByName;

        } catch (DaoException | TransactionException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Account findOneById(final Integer id) throws ServiceException, TransactionException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);

            if (!accountDao.isExistById(id)) {
                transactionImpl.rollback();
                throw new ServiceException("Can't find by id account because account doesn't exist");
            }

            final Account account = accountDao.findOneById(id);
            transactionImpl.commit();
            return account;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Account findOneByEmail(final String email) throws ServiceException, TransactionException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);

            if (!accountDao.isExistByEmail(email)) {
                transactionImpl.rollback();
                throw new ServiceException("Can't find by email account because account doesn't exist");
            }

            final Account account = accountDao.findOneByEmail(email);
            transactionImpl.commit();
            return account;

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Account account) throws ServiceException, TransactionException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);

            if (accountDao.isExistByEmail(account.getEmail())) {
                transactionImpl.rollback();
                throw new ServiceException("Can't add account because account is already exist");
            }

            accountDao.add(account);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Account account) throws ServiceException, TransactionException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);

            if (!accountDao.isExistById(account.getId())) {
                transactionImpl.rollback();
                throw new ServiceException("Can't update account because account doesn't exist exist");
            }

            if (accountDao.isExistByNotIdAndEmail(account.getId(), account.getEmail())) {
                throw new ServiceException("Can't update current account because email is used");
            }

            accountDao.update(account);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deposit(final Integer accountId,
                        String card,
                        Float deposit,
                        LocalDate validity) throws ServiceException, TransactionException {

        try {
            final AccountDao accountDao = transactionImpl.createDao(DaoKeys.ACCOUNT_DAO);

            if (!accountDao.isExistById(accountId)) {
                transactionImpl.rollback();
                throw new ServiceException("Can't update account because account doesn't exist exist");
            }

            if (!Pattern.matches("^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$", card)) {
                throw new ServiceException("Check card number");
            }

            if (validity.isBefore(LocalDate.now())) {
                throw new ServiceException("Current card had expired");
            }

            accountDao.updateBalanceForAccountId(accountId, accountDao.findOneById(accountId).getBalance() + deposit);
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
