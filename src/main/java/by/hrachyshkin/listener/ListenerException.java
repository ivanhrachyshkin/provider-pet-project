package by.hrachyshkin.listener;

public class ListenerException extends RuntimeException {

    public ListenerException() {
    }

    public ListenerException(final String message) {
        super(message);
    }

    public ListenerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ListenerException(final Throwable cause) {
        super(cause);
    }

    public ListenerException(final String message,
                             final Throwable cause,
                             final boolean enableSuppression,
                             final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
