package by.hrachyshkin.provider.dao;


import by.hrachyshkin.provider.dao.pool.ConnectionPool;
import by.hrachyshkin.provider.dao.pool.PoolException;
import by.hrachyshkin.provider.dao.impl.TransactionImpl;
import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

public class TransactionFactory {

    @Getter
    private static final TransactionFactory INSTANCE = new TransactionFactory();

    public Transaction createTransactionImpl() throws PoolException {
        return new TransactionImpl(ConnectionPool.getInstance().getConnection());
    }
}
