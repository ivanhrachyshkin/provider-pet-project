package by.hrachyshkin.service;

import by.hrachyshkin.dao.transaction.Transaction;
import by.hrachyshkin.dao.transaction.TransactionException;
import by.hrachyshkin.dao.transaction.TransactionFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServiceFactoryImpl {

    private final TransactionFactory factory = TransactionFactory.getINSTANCE();

    @SuppressWarnings("unchecked")
    public <T> Service<T> getService(final ServiceKeys serviceKeys) throws TransactionException {

        try {
            final Transaction transaction = factory.createTransaction();

            return switch (serviceKeys) {
                case ACCOUNT_SERVICE -> (Service<T>) new AccountServiceImpl(transaction);
                case BILL_SERVICE -> (Service<T>) new BillServiceImpl(transaction);
                case DISCOUNT_SERVICE -> (Service<T>) new DiscountServiceImpl(transaction);
                case PROMOTION_SERVICE -> (Service<T>) new PromotionServiceImpl(transaction);
                case SUBSCRIPTION_SERVICE -> (Service<T>) new SubscriptionServiceImpl(transaction);
                case TARIFF_SERVICE -> (Service<T>) new TariffServiceImpl(transaction);
                default -> (Service<T>) new TrafficServiceImpl(transaction);
            };

        } catch (Exception e) {
            throw new TransactionException(e.getMessage(), e);
        }
    }
}
