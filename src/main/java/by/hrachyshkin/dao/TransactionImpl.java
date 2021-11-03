package by.hrachyshkin.dao;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;

@RequiredArgsConstructor
public class TransactionImpl {

    private final DataSource dataSource;
    private Connection connection;


    public void startTransaction() throws TransactionException {
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

        } catch (Exception e) {
            throw new TransactionException("Cant's start transaction", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void commit() throws TransactionException {
        try {
            connection.commit();

        } catch (Exception e) {
            throw new TransactionException("Cant's commit transaction", e);
        }
    }

    public void rollback() throws TransactionException {
        try {
            connection.rollback();

        } catch (Exception e) {
            throw new TransactionException("Cant's rollback transaction", e);
        }
    }

    public void endTransaction() throws TransactionException {
        try {
            connection.close();
            connection = null;

        } catch (Exception e) {
            throw new TransactionException("Cant's end transaction", e);
        }
    }
}
