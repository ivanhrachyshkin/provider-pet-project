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

    {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            ConnectionPool.getInstance().init(properties.getProperty("db.driver"),
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"),
                    Integer.parseInt(properties.getProperty("pool.startSize")),
                    Integer.parseInt(properties.getProperty("pool.maxSize")),
                    Integer.parseInt(properties.getProperty("checkConnectionTimeout")));
        } catch (PoolException | IOException e) {
            throw new ExceptionInInitializerError();
        }
    }

    public Transaction createTransactionImpl() throws PoolException {
        return new TransactionImpl(ConnectionPool.getInstance().getConnection());
    }
}
