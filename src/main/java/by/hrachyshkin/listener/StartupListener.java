package by.hrachyshkin.listener;

import by.hrachyshkin.Constants;
import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.DaoFactory;
import by.hrachyshkin.dao.SchemaDao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

    private final SchemaDao schemaDAO = DaoFactory.getINSTANCE().getSchemaDAO();

    @Override
    public void contextInitialized(final ServletContextEvent servletContextEvent) {

        try {
            schemaDAO.init();

        } catch (DaoException e) {
            throw new ListenerException(Constants.STARTUP_LISTENER_INIT_ERROR);
        }
    }

    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
    }
}
