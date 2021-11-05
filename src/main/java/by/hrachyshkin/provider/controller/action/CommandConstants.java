package by.hrachyshkin.provider.controller.action;

public interface CommandConstants {

    // Account constants
    String WELCOME = "/";
    String CABINET = "/cabinet";
    String CREATE_ACCOUNT = "/cabinet/accounts/create";
    String DEPOSIT = "/cabinet/deposit";
    String LOGIN = "/login";
    String LOGOUT = "/logout";
    String ACCOUNTS = "/cabinet/accounts";
    String SUBSCRIPTIONS = "/cabinet/subscriptions";
    String UPDATE_ACCOUNT = "/cabinet/update";
    String MAIN = "/main";

    // Discount constants
    String CREATE_DISCOUNT = "/discounts/create";
    String DELETE_DISCOUNT = "/discounts/delete";
    String DISCOUNTS = "/discounts";
    String UPDATE_DISCOUNTS = "/discounts/update";

    // Promotion constants
    String CREATE_PROMOTION = "/tariffs/discounts/create";
    String DELETE_PROMOTION = "/tariffs/discounts/delete";
    String DISCOUNTS_FOR_PROMOTION = "/tariffs/discounts";

    // Subscription constants
    String CREATE_SUBSCRIPTION = "/cabinet/subscriptions/create";
    String PAY_BILL_FOR_PROMOTION = "/cabinet/subscriptions/bills/pay";
    String DELETE_SUBSCRIPTION = "/cabinet/subscriptions/delete";
    String SHOW_BILLS_FOR_SUBSCRIPTION = "/cabinet/subscriptions/bills";
    String SHOW_TRAFFICS_FOR_SUBSCRIPTION = "/cabinet/subscriptions/traffics";

    // Tariff constants
    String CREATE_TARIFF = "/tariffs/create";
    String DELETE_TARIFF = "/tariffs/delete";
    String TARIFFS = "/tariffs";
    String UPDATE_TARIFF = "/tariffs/update";

}


