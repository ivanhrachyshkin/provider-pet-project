package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Account;

import java.util.List;

public interface AccountDao extends Dao<Account> {

    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByEmail(String email) throws DaoException;

    boolean isExistByEmailAndPassword(final String email, final String password) throws DaoException;

    List<Account> findAndSortByName() throws DaoException;

    Account findOneByEmail(final String email) throws DaoException;
}
