package by.hrachyshkin.dao.entity_dao.account_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Account;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.util.List;

public interface AccountDao extends Dao<Account> {

    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByEmailAndPassword(final String email, final String password) throws DaoException;

    boolean isExistByEmail(String email) throws DaoException;

    List<Account> findAndSort(Sort sort) throws DaoException;//hardcode

    List<Account> findAndFilter(Filter filter) throws DaoException;

    List<Account> findAndFilterAndSort(Filter filter, Sort sort) throws DaoException;
}
