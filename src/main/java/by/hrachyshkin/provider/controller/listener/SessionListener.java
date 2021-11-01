package by.hrachyshkin.provider.controller.listener;

import by.hrachyshkin.provider.dao.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionListener implements HttpSessionAttributeListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(SessionListener.class);

    public void attributeRemoved(HttpSessionBindingEvent ev) {
        LOGGER.info("remove: " + ev.getClass().getSimpleName() + " : " + ev.getName()
                + " : " + ev.getValue());
    }

    //TODO refactor sout to logs
    public void attributeAdded(HttpSessionBindingEvent ev) {

        LOGGER.info("add: " + ev.getClass().getSimpleName() + " : " + ev.getName()
                + " : " + ev.getValue());
    }

    public void attributeReplaced(HttpSessionBindingEvent ev) {

        LOGGER.info("replace: " + ev.getClass().getSimpleName() + " : " + ev.getName()
                + " : " + ev.getValue());
    }
}