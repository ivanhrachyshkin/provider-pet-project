package by.hrachyshkin.servlet;

import by.hrachyshkin.dao.entity_dao.DaoKeys;
import by.hrachyshkin.dao.transaction.TransactionFactory;

import java.util.List;

public class Runner {

    public static void main(String[] args) throws Exception {

       List<Object> list = TransactionFactory.getINSTANCE().createTransaction().createDao(DaoKeys.ACCOUNT_DAO).find();
        System.out.print(list.toString());
    }
}
