package by.hrachyshkin.provider.controller.action.impl;

public class ActionException extends Exception {

    public ActionException() {
    }

    public ActionException(final String message) {
        super(message);
    }

    public ActionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ActionException(final Throwable cause) {
        super(cause);
    }

    public ActionException(final String message,
                           final Throwable cause,
                           final boolean enableSuppression,
                           final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
