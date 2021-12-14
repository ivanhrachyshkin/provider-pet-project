package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.ResourceBundleFactory;
import by.hrachyshkin.provider.dao.Transaction;
import by.hrachyshkin.provider.dao.TransactionFactory;
import by.hrachyshkin.provider.service.impl.*;
import lombok.Getter;

import java.util.ResourceBundle;

public class ServiceFactory {

    @Getter
    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final TransactionFactory factory = TransactionFactory.getINSTANCE();
    private final ResourceBundle rb = ResourceBundleFactory.getINSTANCE().getRb();

    @SuppressWarnings("unchecked")
    public <T extends Service> T getService(final ServiceKeys serviceKeys)
            throws ServiceException {

        try {
            final Transaction transactionImpl =
                    factory.createTransactionImpl(rb);

            return switch (serviceKeys) {
                case ACCOUNT -> (T)
                        new AccountServiceImpl(transactionImpl, rb);
                case BILL -> (T)
                        new BillServiceImpl(transactionImpl, rb);
                case DISCOUNT -> (T)
                        new DiscountServiceImpl(transactionImpl, rb);
                case PROMOTION -> (T)
                        new PromotionServiceImpl(transactionImpl, rb);
                case SUBSCRIPTION -> (T)
                        new SubscriptionServiceImpl(transactionImpl, rb);
                case TARIFF -> (T)
                        new TariffServiceImpl(transactionImpl, rb);
                default -> (T)
                        new TrafficServiceImpl(transactionImpl, rb);
            };

        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
