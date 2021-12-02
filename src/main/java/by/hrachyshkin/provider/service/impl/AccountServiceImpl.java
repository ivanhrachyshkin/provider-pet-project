package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.*;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AccountServiceImpl.class);
    private final Transaction transactionImpl;
    private final ResourceBundle rb;

    public boolean isExistByEmailAndPassword(final String email,
                                             final String password)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method isExistByEmailAndPassword starts ");
            final AccountDao accountDao =
                    transactionImpl.createDao(DaoKeys.ACCOUNT);
            final boolean value =
                    accountDao.isExistByEmailAndPassword(email, password);
            transactionImpl.commit();
            LOGGER.debug("method isExistByEmailAndPassword finish ");
            return value;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Account> find() throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method find starts ");
            final AccountDao accountDao =
                    transactionImpl.createDao(DaoKeys.ACCOUNT);
            final List<Account> accounts = accountDao.find();
            transactionImpl.commit();
            LOGGER.debug("method find finish ");
            return accounts;

        } catch (DaoException | TransactionException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Account> findAndSortByName(final Integer offset)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndSortByName starts ");
            final AccountDao accountDao =
                    transactionImpl.createDao(DaoKeys.ACCOUNT);
            final List<Account> accounts = accountDao.findAndSortByName(offset);
            transactionImpl.commit();
            LOGGER.debug("method findAndSortByName finish ");
            return accounts;

        } catch (DaoException | TransactionException e) {
            transactionImpl.rollback();
            LOGGER.error(e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Account findOneById(final Integer id)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findOneById starts ");
            final AccountDao accountDao =
                    transactionImpl.createDao(DaoKeys.ACCOUNT);

            if (!accountDao.isExistById(id)) {
                transactionImpl.rollback();
                LOGGER.error(rb.getString("account.find.by.id.exist"
                        + ".exception"));
                throw new ServiceException(rb.getString("account.find.by"
                        + ".id.exist.exception"));
            }

            transactionImpl.commit();
            LOGGER.debug("method findOneById finish");
            return accountDao.findOneById(id);

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Account findOneByEmail(final String email)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findOneByEmail starts ");
            final AccountDao accountDao =
                    transactionImpl.createDao(DaoKeys.ACCOUNT);

            if (!accountDao.isExistByEmail(email)) {
                LOGGER.error(rb.getString("account.find.one.by.email"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("account.find.one"
                        + ".by.email.exception"));
            }

            transactionImpl.commit();
            LOGGER.debug("method findOneByEmail finish");
            return accountDao.findOneByEmail(email);

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void add(final Account account)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method add starts");
            final AccountDao accountDao =
                    transactionImpl.createDao(DaoKeys.ACCOUNT);

            if (account.getEmail().isEmpty()
                    || account.getPassword().isEmpty()
                    || account.getRole() == null
                    || account.getName() == null
                    || account.getName().isEmpty()
                    || account.getPhone().isEmpty()
                    || account.getAddress().isEmpty()) {
                LOGGER.error(rb.getString("account.add.empty.input"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("account.add"
                        + ".empty.input.exception"));
            }

            if (accountDao.isExistByEmail(account.getEmail())) {
                LOGGER.error(rb.getString("account.add.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(
                        rb.getString("account.add.exist.exception"));
            }

            if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])"
                            + "(?=.*[A-Z])(?=\\S+$).*[A-Za-z0-9].{7,}$",
                    account.getPassword())) {
                LOGGER.error(
                        rb.getString("account.add.password.exception"));
                transactionImpl.rollback();
                throw new ServiceException(
                        rb.getString("account.add.password.exception"));
            }

            accountDao.add(account);
            LOGGER.debug("method add finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Account account)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method update starts ");
            final AccountDao accountDao =
                    transactionImpl.createDao(DaoKeys.ACCOUNT);

            if (account.getId() == null
                    || account.getEmail().isEmpty()
                    || account.getRole() == null
                    || account.getName() == null
                    || account.getName().isEmpty()
                    || account.getPhone().isEmpty()
                    || account.getAddress().isEmpty()) {
                LOGGER.error(rb.getString("account.update.empty.input"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("account.update"
                        + ".empty.input.exception"));
            }

            if (!accountDao.isExistById(account.getId())) {
                transactionImpl.rollback();
                LOGGER.error(
                        rb.getString("account.update.not.exist.exception"));
                throw new ServiceException(
                        rb.getString("account.update.not.exist.exception"));
            }

            if (accountDao.isExistByNotIdAndEmail(
                    account.getId(), account.getEmail())) {
                transactionImpl.rollback();
                LOGGER.error(rb.getString("account.update.used.email"
                        + ".exception"));
                throw new ServiceException(rb.getString("account.update"
                        + ".used.email.exception"));
            }

            accountDao.update(account);
            LOGGER.debug("method add finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deposit(final Integer accountId,
                        final String card,
                        final Float deposit,
                        final LocalDate validity)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method deposit starts ");
            final AccountDao accountDao =
                    transactionImpl.createDao(DaoKeys.ACCOUNT);

            if (card.isEmpty()
                    || deposit == null
                    || validity == null) {
                transactionImpl.rollback();
                LOGGER.error(rb.getString("account.deposit.empty.input"
                        +
                        ".exception"));
                throw new ServiceException(rb.getString("account.deposit"
                        + ".empty.input.exception"));
            }

            if (!accountDao.isExistById(accountId)) {
                LOGGER.error(rb.getString("account.deposit.not"
                        + ".exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("account.deposit"
                        + ".not.exist.exception"));
            }

            if (!Pattern.matches(
                    "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$",
                    card)) {
                LOGGER.error(
                        rb.getString("account.deposit.card.number"
                                + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("account.deposit"
                        + ".card.number.exception"));
            }

            if (validity.isBefore(LocalDate.now())) {
                LOGGER.error(rb.getString("account.deposit.card.expired"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("account.deposit"
                        + ".card.expired.exception"));
            }

            if (deposit < 0) {
                LOGGER.error(rb.getString("account.deposit.negative"
                        + ".deposit.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("account.deposit"
                        + ".negative.deposit.exception"));
            }

            accountDao.updateBalanceForAccountId(accountId,
                    accountDao.findOneById(accountId).getBalance()
                            + deposit);
            LOGGER.debug("method deposit finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.debug(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(final Integer id)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method delete starts ");
            final AccountDao accountDao =
                    transactionImpl.createDao(DaoKeys.ACCOUNT);

            if (!accountDao.isExistById(id)) {
                LOGGER.error(rb.getString("account.delete.exist"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("account.delete"
                        + ".exist.exception"));
            }

            accountDao.delete(id);
            LOGGER.debug("method delete finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
