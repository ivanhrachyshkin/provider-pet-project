package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.entity.Account;

import java.util.List;

public interface AccountService extends Service<Account> {

    boolean isExistByEmailAndPassword(final String email, final String password) throws ServiceException;

    public List<Account> findAndSortByName() throws ServiceException;

    Account findOneByEmail(final String email) throws ServiceException;
}
