package by.hrachyshkin.provider.controller.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionListener implements HttpSessionAttributeListener {

    public void attributeRemoved(HttpSessionBindingEvent ev) {
    }

    public void attributeAdded(HttpSessionBindingEvent ev) {

        System.out.println("add: " + ev.getClass().getSimpleName() + " : " + ev.getName()
                + " : " + ev.getValue());
    }

    public void attributeReplaced(HttpSessionBindingEvent ev) {

        System.out.println("replace: " + ev.getClass().getSimpleName() + " : " + ev.getName()
                + " : " + ev.getValue());
    }
}