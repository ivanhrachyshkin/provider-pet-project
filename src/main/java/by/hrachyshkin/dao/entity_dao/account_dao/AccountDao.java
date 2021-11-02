package by.hrachyshkin.dao.entity_dao.account_dao;

import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.entity.Account;

public interface AccountDao extends EntityDao<Account> {

    boolean isExist(String name) throws DaoException;
}
