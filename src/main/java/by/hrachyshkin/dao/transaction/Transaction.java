package by.hrachyshkin.dao.transaction;

import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.entity_dao.DaoKeys;

public interface Transaction {

    <T extends Dao<?>> T createDao(final DaoKeys typeDao) throws TransactionException;

    void commit() throws TransactionException;

    void rollback() throws TransactionException;

}
