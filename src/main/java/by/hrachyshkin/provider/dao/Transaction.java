package by.hrachyshkin.provider.dao;

public interface Transaction {

    <T extends Dao<?>> T createDao(final DaoKeys typeDao) throws TransactionException;

    void commit() throws TransactionException;

    void rollback() throws TransactionException;

}
