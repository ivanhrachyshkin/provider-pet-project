package by.hrachyshkin.listener;

import by.hrachyshkin.Constants;
import by.hrachyshkin.dao.DAOException;
import by.hrachyshkin.dao.DAOFactory;
import by.hrachyshkin.dao.SchemaDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

    private final SchemaDAO schemaDAO = DAOFactory.getINSTANCE().getSchemaDAO();

    @Override
    public void contextInitialized(final ServletContextEvent servletContextEvent) {

        try {
            schemaDAO.init();

        } catch (DAOException e) {
            throw new ListenerException(Constants.STARTUP_LISTENER_INIT_ERROR);
        }
    }

    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
    }
}
