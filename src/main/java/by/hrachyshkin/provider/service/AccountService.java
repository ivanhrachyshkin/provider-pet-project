package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;

import java.util.List;

public interface AccountService extends Service<Account> {

    boolean isExistByEmailAndPassword(final String email, final String password) throws ServiceException, TransactionException;

    public List<Account> findAndSortByName() throws ServiceException, TransactionException;

    Account findOneByEmail(final String email) throws ServiceException, TransactionException;
}
