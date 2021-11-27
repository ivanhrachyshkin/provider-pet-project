package by.hrachyshkin.provider.controller.listener;

import by.hrachyshkin.provider.dao.pool.ConnectionPool;
import by.hrachyshkin.provider.dao.pool.PoolException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ConnectionListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

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

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getINSTANCE().destroy();
    }
}