package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.service.ServiceException;

import java.util.List;

public interface AccountDao extends Dao<Account> {

    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByEmail(String email) throws DaoException;

    boolean isExistByEmailAndPassword(final String email, final String password) throws DaoException;

    boolean isExistByNotIdAndEmail(final Integer id, final String email) throws DaoException;

    List<Account> findAndSortByName(Integer offset) throws DaoException;

    Account findOneByEmail(final String email) throws DaoException;

    void updateBalanceForAccountId(final Integer accountId, final Float sum) throws DaoException;
}
