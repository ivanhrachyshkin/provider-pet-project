package by.hrachyshkin.dao.pool;

public class PoolException extends Exception {

    public PoolException() {
    }

    public PoolException(final String message) {
        super(message);
    }

    public PoolException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PoolException(final Throwable cause) {
        super(cause);
    }

    public PoolException(final String message,
                         final Throwable cause,
                         final boolean enableSuppression,
                         final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
