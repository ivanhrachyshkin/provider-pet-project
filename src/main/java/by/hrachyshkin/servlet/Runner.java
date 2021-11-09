package by.hrachyshkin.servlet;

import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.account_dao.AccountDao;
import by.hrachyshkin.dao.transaction.TransactionFactory;

public class Runner {

    public static void main(String[] args) throws Exception {


        BillServiceImpl billService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.BILL_SERVICE);
        billService.findBillsForTariffPerAccount(4, 3).stream().forEach(System.out::println);

    }
}