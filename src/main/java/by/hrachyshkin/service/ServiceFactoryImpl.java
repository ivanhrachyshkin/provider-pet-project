package by.hrachyshkin.service;

import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.dao.transaction.TransactionFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServiceFactoryImpl {

    @Getter
    private static final ServiceFactoryImpl INSTANCE = new ServiceFactoryImpl();

    private final TransactionFactory factory = TransactionFactory.getINSTANCE();

    @SuppressWarnings("unchecked")
    public <T extends Service> T getService(final ServiceKeys serviceKeys) throws TransactionException {

        try {
            final Transaction transaction = factory.createTransaction();

            return switch (serviceKeys) {
                case ACCOUNT_SERVICE -> (T) new AccountServiceImpl(transaction);
               // case BILL_SERVICE -> (Service<T>) new BillServiceImpl(transaction);
                case DISCOUNT_SERVICE -> (T) new DiscountServiceImpl(transaction);
               // case PROMOTION_SERVICE -> (Service<T>) new PromotionServiceImpl(transaction);
               // case SUBSCRIPTION_SERVICE -> (Service<T>) new SubscriptionServiceImpl(transaction);
                case TARIFF_SERVICE -> (T) new TariffServiceImpl(transaction);
                default -> (T) new TrafficServiceImpl(transaction);
            };

        } catch (Exception e) {
            throw new TransactionException(e.getMessage(), e);
        }
    }
}
