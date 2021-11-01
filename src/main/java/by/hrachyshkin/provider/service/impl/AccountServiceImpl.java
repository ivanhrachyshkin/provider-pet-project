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

            if (account.getEmail().isEmpty()
                    || account.getPassword().isEmpty()
                    || account.getRole() == null
                    || account.getName().isEmpty()
                    || account.getPhone().isEmpty()
                    || account.getAddress().isEmpty()) {

                System.out.println(account.toString());
                throw new ServiceException("Can't add account because of empty input");
            }

            if (accountDao.isExistByEmail(account.getEmail())) {
                transactionImpl.rollback();
                throw new ServiceException("Can't add account because account is already exist");
            }

            if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).*[A-Za-z0-9].{7,}$",
                    account.getPassword())) {
                transactionImpl.rollback();
                throw new ServiceException("Can't add account check password");
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

            if (account.getId() == null
                    || account.getEmail().isEmpty()
                    || account.getRole() == null
                    || account.getName().isEmpty()
                    || account.getPhone().isEmpty()
                    || account.getAddress().isEmpty()) {
                throw new ServiceException("Can't update account because of empty input");
            }

            if (!accountDao.isExistById(account.getId())) {
                transactionImpl.rollback();
                throw new ServiceException("Can't update account because account doesn't exist exist");
            }

            if (accountDao.isExistByNotIdAndEmail(account.getId(), account.getEmail())) {
                throw new ServiceException("Can't update account because email is used");
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
                throw new ServiceException("Can't deposit because account doesn't exist exist");
            }

            if (!accountDao.isExistById(accountId)) {
                transactionImpl.rollback();
                throw new ServiceException("Can't deposit because account doesn't exist exist");
            }

            if (!Pattern.matches("^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$", card)) {
                throw new ServiceException("Can't deposit because current card number is invalid");
            }

            if (validity.isBefore(LocalDate.now())) {
                throw new ServiceException("Can't deposit because current card had expired");
            }

            if (deposit < 0) {
                throw new ServiceException("Can't deposit because of negative value");
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
        throw new UnsupportedOperationException("Delete operation is not available for account");
    }
}
