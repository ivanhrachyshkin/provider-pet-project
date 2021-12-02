package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;

import java.time.LocalDate;
import java.util.List;

public interface AccountService extends Service<Account> {

    boolean isExistByEmailAndPassword(String email, String password)
            throws ServiceException, TransactionException;

    List<Account> findAndSortByName(Integer offset)
            throws ServiceException, TransactionException;

    Account findOneByEmail(String email)
            throws ServiceException, TransactionException;

    void deposit(Integer accountId,
                 String card,
                 Float deposit,
                 LocalDate validity)
            throws ServiceException, TransactionException;
}
