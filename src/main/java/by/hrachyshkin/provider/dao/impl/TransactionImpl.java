package by.hrachyshkin.provider.dao.impl;

import by.hrachyshkin.provider.dao.Dao;
import by.hrachyshkin.provider.dao.DaoKeys;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.dao.impl.AccountDaoImpl;
import by.hrachyshkin.provider.dao.impl.BillDaoImpl;
import by.hrachyshkin.provider.dao.impl.DiscountDaoImpl;
import by.hrachyshkin.provider.dao.impl.PromotionDaoImpl;
import by.hrachyshkin.provider.dao.impl.SubscriptionDaoImpl;
import by.hrachyshkin.provider.dao.impl.TariffDaoImpl;
import by.hrachyshkin.provider.dao.impl.TrafficDaoImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public class TransactionImpl implements Transaction {

    private final Connection connection;
    private final ResourceBundle rb;

    @SuppressWarnings("unchecked")
    public <T extends Dao<?>> T createDao(final DaoKeys daoKeys) throws TransactionException {

        try {
            return switch (daoKeys) {
                case ACCOUNT_DAO -> (T) new AccountDaoImpl(connection, rb);
                case BILL_DAO -> (T)new BillDaoImpl(connection, rb);
                case DISCOUNT_DAO -> (T) new DiscountDaoImpl(connection, rb);
                case PROMOTION_DAO -> (T) new PromotionDaoImpl(connection, rb);
                case SUBSCRIPTION_DAO -> (T) new SubscriptionDaoImpl(connection, rb);
                case TARIFF_DAO -> (T) new TariffDaoImpl(connection, rb);
                default -> (T) new TrafficDaoImpl(connection, rb);
            };

        } catch (Exception e) {
            throw new TransactionException(e.getMessage(), e);
        }
    }

    public void commit() throws TransactionException {

        try {
            connection.commit();

        } catch (SQLException e) {
            throw new TransactionException(e.getMessage(), e);
        }
    }

    public void rollback() throws TransactionException {

        try {
            connection.rollback();

        } catch (SQLException e) {
            throw new TransactionException(e.getMessage(), e);
        }
    }
}
