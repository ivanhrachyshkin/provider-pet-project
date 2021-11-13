package by.hrachyshkin.provider.dao;

public class DaoException extends Exception {

    public DaoException() {
    }

    public DaoException(final String message) {
        super(message);
    }

    public DaoException(final String message,
                        final Throwable cause) {
        super(message, cause);
    }

    public DaoException(final Throwable cause) {
        super(cause);
    }

    public DaoException(final String message,
                        final Throwable cause,
                        final boolean enableSuppression,
                        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
