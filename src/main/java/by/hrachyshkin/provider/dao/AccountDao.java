package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Account;

import java.util.List;

public interface AccountDao extends Dao<Account> {

    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByEmail(String email) throws DaoException;

    boolean isExistByEmailAndPassword(String email, String password)
            throws DaoException;

    boolean isExistByNotIdAndEmail(Integer id, String email)
            throws DaoException;

    List<Account> findAndSortByName(Integer offset) throws DaoException;

    Account findOneByEmail(String email) throws DaoException;

    void updateBalanceForAccountId(Integer accountId, Float sum)
            throws DaoException;
}
