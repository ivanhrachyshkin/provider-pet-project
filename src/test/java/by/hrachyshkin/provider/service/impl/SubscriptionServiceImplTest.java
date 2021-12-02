package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.service.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionServiceImplTest {

    @BeforeClass
    public void beforeClass() {
        DataBaseConnection.getINSTANCE().contextInitialized();
    }

    private final Subscription subscription1 = new Subscription(1, 1,1);
    private final Subscription subscription2 = new Subscription(2, 1,2);
    private final Subscription subscription3 = new Subscription(3, 1,3);
    private final Subscription subscription4 = new Subscription(4, 2,1);
    private final Subscription subscription5 = new Subscription(5, 2,2);
    private final Subscription subscription6 = new Subscription(6, 2,3);
    private final Subscription subscription7 = new Subscription(7, 3,1);
    private final Subscription subscription8 = new Subscription(8, 3,2);
    private final Subscription subscription9 = new Subscription(9, 3,3);


    @Test
    public void ShouldReturnSubscriptions_On_Find_Pos() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        final List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription1);
        subscriptions.add(subscription2);
        subscriptions.add(subscription3);
        subscriptions.add(subscription4);
        subscriptions.add(subscription5);
        subscriptions.add(subscription6);
        subscriptions.add(subscription7);
        subscriptions.add(subscription8);
        subscriptions.add(subscription9);

        Assert.assertEquals(subscriptionService.find(), subscriptions);
    }

    @Test
    public void ShouldReturnSubscriptions_On_FindAndFilterByAccountId_Pos() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        final List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription1);
        subscriptions.add(subscription2);
        subscriptions.add(subscription3);

        Assert.assertEquals(subscriptionService.findAndFilterByAccountId(1), subscriptions);
    }

    @Test
    public void ShouldReturnSubscription_On_FindOneById_Pos() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        Assert.assertEquals(subscriptionService.findOneById(1), subscription1);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_FindOneById_Exceptions() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        subscriptionService.findOneById(-1);
    }

    @Test
    public void ShouldReturnSubscription_On_FindOneByAccountIdAndTariffId_Pos() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        Assert.assertEquals(subscriptionService.findOneByAccountIdAndTariffId(1,1), subscription1);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_FindOneByAccountIdAndTariffId_Exceptions() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        subscriptionService.findOneByAccountIdAndTariffId(-1,-1);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldThrowException_On_Update_Exception() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        subscriptionService.update(new Subscription(1,1));
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldThrowException_On_Delete_Exception() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        subscriptionService.delete(1);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void ShouldThrowException_On_DeleteSubscription_Exception() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        subscriptionService.delete(-1);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_AccountExist_Exception() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        subscriptionService.add(new Subscription(-1,1));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_TariffExist_Exception() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        subscriptionService.add(new Subscription(1,-1));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_Add_Exist_Exception() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        subscriptionService.add(new Subscription(1,1));
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_PayBill_AccountExist_Exception() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
       subscriptionService.payBillForSubscription(-1, 1,10.0f, LocalDate.now());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void ShouldThrowException_On_PayBill_BillExist_Exception() throws ServiceException, TransactionException {

        final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);
        subscriptionService.payBillForSubscription(3, -1,10.0f, LocalDate.now());
    }
}