package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.pool.ConnectionPool;
import by.hrachyshkin.provider.dao.pool.PoolException;
import lombok.Getter;

import java.io.IOException;
import java.util.Properties;

public class DataBaseConnection {

    @Getter
    private final static DataBaseConnection INSTANCE = new DataBaseConnection();

    public void contextInitialized() {

        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
            ConnectionPool.getINSTANCE().init(
                    properties.getProperty("db.driver"),
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"),
                    Integer.parseInt(properties.getProperty("pool.startSize")),
                    Integer.parseInt(properties.getProperty("pool.maxSize")),
                    Integer.parseInt(properties.getProperty("checkConnectionTimeout")));
        } catch (IOException | PoolException e) {
            throw new ExceptionInInitializerError();
        }
    }
}
