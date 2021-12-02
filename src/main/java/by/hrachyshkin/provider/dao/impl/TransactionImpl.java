package by.hrachyshkin.provider.dao.impl;

import by.hrachyshkin.provider.dao.Dao;
import by.hrachyshkin.provider.dao.DaoKeys;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.dao.pool.ConnectionPool;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class TransactionImpl implements Transaction {

    private final Connection connection;
    private final ResourceBundle rb;

    @SuppressWarnings("unchecked")
    public <T extends Dao<?>> T createDao(final DaoKeys daoKeys)
            throws TransactionException {

        try {
            return switch (daoKeys) {
                case ACCOUNT -> (T) new AccountDaoImpl(connection, rb);
                case BILL -> (T) new BillDaoImpl(connection, rb);
                case DISCOUNT -> (T) new DiscountDaoImpl(connection, rb);
                case PROMOTION -> (T) new PromotionDaoImpl(connection, rb);
                case SUBSCRIPTION -> (T) new SubscriptionDaoImpl(connection,
                        rb);
                case TARIFF -> (T) new TariffDaoImpl(connection, rb);
                default -> (T) new TrafficDaoImpl(connection, rb);
            };

        } catch (Exception e) {
            throw new TransactionException(e.getMessage(), e);
        }
    }

    public void commit() throws TransactionException {

        try {
            connection.setAutoCommit(false);
            connection.commit();
            ConnectionPool.getINSTANCE().releaseConnection(connection);

        } catch (SQLException e) {
            throw new TransactionException(e.getMessage(), e);
        }
    }

    public void rollback() throws TransactionException {

        try {
            connection.setAutoCommit(false);
            connection.rollback();
            ConnectionPool.getINSTANCE().releaseConnection(connection);

        } catch (SQLException e) {
            throw new TransactionException(e.getMessage(), e);
        }
    }
}
