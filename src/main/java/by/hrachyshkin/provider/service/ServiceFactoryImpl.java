package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.transaction.Transaction;
import by.hrachyshkin.provider.dao.transaction.TransactionException;
import by.hrachyshkin.provider.dao.transaction.TransactionFactory;
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
            final Transaction transactionImpl = factory.createTransactionImpl();

            return switch (serviceKeys) {
                case ACCOUNT_SERVICE -> (T) new AccountServiceImpl(transactionImpl);
                case BILL_SERVICE -> (T) new BillServiceImpl(transactionImpl);
                case DISCOUNT_SERVICE -> (T) new DiscountServiceImpl(transactionImpl);
                case PROMOTION_SERVICE -> (T) new PromotionServiceImpl(transactionImpl);
                case SUBSCRIPTION_SERVICE -> (T) new SubscriptionServiceImpl(transactionImpl);
                case TARIFF_SERVICE -> (T) new TariffServiceImpl(transactionImpl);
                default -> (T) new TrafficServiceImpl(transactionImpl);
            };

        } catch (Exception e) {
            throw new TransactionException(e.getMessage(), e);
        }
    }
}
