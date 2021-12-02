package by.hrachyshkin.provider.controller.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionListener implements HttpSessionAttributeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            SessionListener.class);

    public void attributeRemoved(final HttpSessionBindingEvent ev) {
        LOGGER.info("remove: " + ev.getClass().getSimpleName()
                + " : " + ev.getName()
                + " : " + ev.getValue());
    }

    public void attributeAdded(final HttpSessionBindingEvent ev) {

        LOGGER.info("add: " + ev.getClass().getSimpleName() + " : "
                + ev.getName()
                + " : " + ev.getValue());
    }

    public void attributeReplaced(final HttpSessionBindingEvent ev) {

        LOGGER.info("replace: " + ev.getClass().getSimpleName() + " : "
                + ev.getName()
                + " : " + ev.getValue());
    }
}
