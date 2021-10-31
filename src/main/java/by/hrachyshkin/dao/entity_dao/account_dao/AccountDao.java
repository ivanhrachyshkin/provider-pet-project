package by.hrachyshkin.dao.entity_dao.account_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.entity.Account;

import java.util.List;

public interface AccountDao extends EntityDao<Account> {

    List<Account> findAllSortedByLogin() throws DaoException;

    Account findOneByLogin(final String login) throws DaoException;

    boolean isRegistered(final Account account) throws DaoException;

    Account updateTariffIdByLoginAndPassword(final int tariffId, final Account account) throws DaoException;
}
