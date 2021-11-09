package by.hrachyshkin.servlet;

import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.account_dao.AccountDao;
import by.hrachyshkin.dao.transaction.TransactionFactory;

public class Runner {

    public static void main(String[] args) throws Exception {


        SubscriptionServiceImpl subscriptionService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION_SERVICE);

       subscriptionService.add(new Subscription(7,3));



    }
}