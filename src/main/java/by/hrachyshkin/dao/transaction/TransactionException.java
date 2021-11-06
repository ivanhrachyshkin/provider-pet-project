package by.hrachyshkin.dao.transaction;

public class TransactionException extends Exception {

    public TransactionException() {
    }

    public TransactionException(final String message) {
        super(message);
    }

    public TransactionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TransactionException(final Throwable cause) {
        super(cause);
    }

    public TransactionException(final String message,
                                final Throwable cause,
                                final boolean enableSuppression,
                                final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
