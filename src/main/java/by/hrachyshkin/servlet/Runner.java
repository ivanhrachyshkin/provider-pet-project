package by.hrachyshkin.servlet;

import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.account_dao.AccountDao;
import by.hrachyshkin.dao.transaction.TransactionFactory;

public class Runner {

    public static void main(String[] args) throws Exception {


TariffServiceImpl tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);

List<Tariff> tariffs = tariffService.findTariffsForAccountId(4);
        System.out.println(tariffs);
    }
}
