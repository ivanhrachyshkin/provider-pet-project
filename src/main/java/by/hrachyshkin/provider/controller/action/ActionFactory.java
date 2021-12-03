package by.hrachyshkin.provider.controller.action;

import by.hrachyshkin.provider.controller.action.impl.LocalizationAction;
import by.hrachyshkin.provider.controller.action.impl.WelcomeAction;
import by.hrachyshkin.provider.controller.action.impl.MainPageAction;
import by.hrachyshkin.provider.controller.action.impl.account.*;
import by.hrachyshkin.provider.controller.action.impl.discount.CreateDiscountAction;
import by.hrachyshkin.provider.controller.action.impl.discount.DeleteDiscountAction;
import by.hrachyshkin.provider.controller.action.impl.discount.ShowDiscountAction;
import by.hrachyshkin.provider.controller.action.impl.discount.UpdateDiscountAction;
import by.hrachyshkin.provider.controller.action.impl.promotion.CreatePromotionAction;
import by.hrachyshkin.provider.controller.action.impl.promotion.DeletePromotionAction;
import by.hrachyshkin.provider.controller.action.impl.promotion.DiscountsForPromotionAction;
import by.hrachyshkin.provider.controller.action.impl.promotion.ShowDiscountsForPromotionAction;
import by.hrachyshkin.provider.controller.action.impl.subscription.*;
import by.hrachyshkin.provider.controller.action.impl.tariff.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private final Map<String, Action> commands = new HashMap<>() {

        {
            put(WelcomeAction.WELCOME, new WelcomeAction());
            put(CabinetAction.CABINET, new CabinetAction());
            put(CreateAccountAction.CREATE_ACCOUNT, new CreateAccountAction());
            put(DepositMoneyForAccount.DEPOSIT, new DepositMoneyForAccount());
            put(LoginAction.LOGIN, new LoginAction());
            put(LogoutAction.LOGOUT, new LogoutAction());
            put(ShowAccountsAction.ACCOUNTS, new ShowAccountsAction());
            put(ShowSubscriptionsForAccountAction.SUBSCRIPTIONS,
                    new ShowSubscriptionsForAccountAction());
            put(UpdateAccountAction.UPDATE_ACCOUNT_LIST,
                    new UpdateAccountAction());
            put(UpdateAccountAction.UPDATE_ACCOUNT_CABINET,
                    new UpdateAccountAction());
            put(MainPageAction.MAIN, new MainPageAction());

            put(ShowDiscountAction.DISCOUNTS, new ShowDiscountAction());
            put(CreateDiscountAction.CREATE_DISCOUNT,
                    new CreateDiscountAction());
            put(DeleteDiscountAction.DELETE_DISCOUNT,
                    new DeleteDiscountAction());
            put(UpdateDiscountAction.UPDATE_DISCOUNTS,
                    new UpdateDiscountAction());

            put(CreatePromotionAction.CREATE_PROMOTION,
                    new CreatePromotionAction());
            put(DeletePromotionAction.DELETE_PROMOTION,
                    new DeletePromotionAction());
            put(ShowDiscountsForPromotionAction.SHOW_DISCOUNTS_FOR_PROMOTION,
                    new ShowDiscountsForPromotionAction());
            put(DiscountsForPromotionAction.DISCOUNTS_FOR_PROMOTION,
                    new DiscountsForPromotionAction());

            put(CreateSubscriptionAction.CREATE_SUBSCRIPTION,
                    new CreateSubscriptionAction());
            put(PayBillForSubscriptionAction.PAY_BILL_FOR_SUBSCRIPTION,
                    new PayBillForSubscriptionAction());
            put(DeleteSubscriptionAction.DELETE_SUBSCRIPTION,
                    new DeleteSubscriptionAction());
            put(TrafficsForSubscriptionAction.TRAFFICS_FOR_SUBSCRIPTION,
                    new TrafficsForSubscriptionAction());
            put(ShowTrafficsForSubscriptionAction.SHOW_TRAFFIC_FOR_SUBSCRIPTION,
                    new ShowTrafficsForSubscriptionAction());
            put(BillsForSubscriptionAction.BILLS_FOR_SUBSCRIPTION,
                    new BillsForSubscriptionAction());
            put(ShowBillsForSubscriptionAction.SHOW_BILLS_FOR_SUBSCRIPTION,
                    new ShowBillsForSubscriptionAction());

            put(CreateTariffAction.CREATE_TARIFF, new CreateTariffAction());
            put(DeleteTariffAction.DELETE_TARIFF, new DeleteTariffAction());
            put(ShowTariffsAction.TARIFFS, new ShowTariffsAction());
            put(UpdateTariffAction.UPDATE_TARIFF, new UpdateTariffAction());
            put(UpdateTariffAction.UPDATE_TARIFF, new UpdateTariffAction());

            put(LocalizationAction.LOCALIZATION_ACTION, new LocalizationAction());
        }
    };

    @Getter
    private static final ActionFactory INSTANCE = new ActionFactory();

    public Action getCommand(final String commandName) {
        return commands.get(commandName);
    }
}
