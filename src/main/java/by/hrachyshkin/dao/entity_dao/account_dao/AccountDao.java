package by.hrachyshkin.dao.entity_dao.account_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Account;

import java.util.List;

public interface AccountDao extends Dao<Account> {

    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByEmail(String email) throws DaoException;

    boolean isExistByEmailAndPassword(final String email, final String password) throws DaoException;

    List<Account> findAndSortByName() throws DaoException;

    Account findOneByEmail(final String email) throws DaoException;
}
