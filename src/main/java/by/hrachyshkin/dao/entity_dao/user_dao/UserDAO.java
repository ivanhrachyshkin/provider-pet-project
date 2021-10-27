package by.hrachyshkin.dao.entity_dao.user_dao;

import by.hrachyshkin.dao.DAOException;
import by.hrachyshkin.dao.entity_dao.DAO;
import by.hrachyshkin.entity.Account;

import java.util.List;

public interface UserDAO extends DAO<Account> {

    List<Account> findAllSortedByName() throws DAOException;

    Account findOneByLogin(final String login) throws DAOException;
}
