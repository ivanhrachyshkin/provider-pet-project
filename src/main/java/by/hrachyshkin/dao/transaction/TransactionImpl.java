package by.hrachyshkin.dao.transaction;

import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.account_dao.AccountDaoImpl;
import by.hrachyshkin.dao.entity_dao.bill_dao.BillDaoImpl;
import by.hrachyshkin.dao.entity_dao.discount_dao.DiscountDaoImpl;
import by.hrachyshkin.dao.entity_dao.promotion_dao.PromotionDaoImpl;
import by.hrachyshkin.dao.entity_dao.subscription_dao.SubscriptionDaoImpl;
import by.hrachyshkin.dao.entity_dao.tariff_dao.TariffDaoImpl;
import by.hrachyshkin.dao.entity_dao.traffic_dao.TrafficDaoImpl;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class TransactionImpl implements Transaction {

    private final Connection connection;

    @SuppressWarnings("unchecked")
    public <T extends Dao<?>> T createDao(final DaoKeys daoKeys) throws TransactionException {

        try {
            return switch (daoKeys) {
                case ACCOUNT_DAO -> (T) new AccountDaoImpl(connection);
                case BILL_DAO -> (T)new BillDaoImpl(connection);
                case DISCOUNT_DAO -> (T) new DiscountDaoImpl(connection);
                case PROMOTION_DAO -> (T) new PromotionDaoImpl(connection);
                case SUBSCRIPTION_DAO -> (T) new SubscriptionDaoImpl(connection);
                case TARIFF_DAO -> (T) new TariffDaoImpl(connection);
                default -> (T) new TrafficDaoImpl(connection);
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
