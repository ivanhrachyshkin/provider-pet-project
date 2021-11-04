package by.hrachyshkin.provider.dao;


import by.hrachyshkin.provider.dao.impl.TransactionImpl;
import by.hrachyshkin.provider.dao.pool.ConnectionPool;
import by.hrachyshkin.provider.dao.pool.PoolException;
import lombok.Getter;

import java.util.ResourceBundle;

public class TransactionFactory {

    @Getter
    private static final TransactionFactory INSTANCE = new TransactionFactory();

    public Transaction createTransactionImpl(final ResourceBundle rb) throws PoolException {
        return new TransactionImpl(ConnectionPool.getInstance().getConnection(), rb);
    }
}
