package by.hrachyshkin.servlet;

import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.entity_dao.account_dao.AccountDao;
import by.hrachyshkin.dao.transaction.TransactionFactory;

public class Runner {

    public static void main(String[] args) throws Exception {


        DiscountServiceImpl discountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);


      discountService.add(new Discount("asd", Discount.Type.COEFFICIENT, 12, LocalDate.of(2020,10,10), LocalDate.of(2020,10,10)));
    }
}
