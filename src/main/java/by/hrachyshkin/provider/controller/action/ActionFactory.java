package by.hrachyshkin.provider.controller.action;

import by.hrachyshkin.provider.controller.action.impl.IndexAction;
import by.hrachyshkin.provider.controller.action.impl.MainPageAction;
import by.hrachyshkin.provider.controller.action.impl.account.*;
import by.hrachyshkin.provider.controller.action.impl.discount.CreateDiscountAction;
import by.hrachyshkin.provider.controller.action.impl.discount.DeleteDiscountAction;
import by.hrachyshkin.provider.controller.action.impl.discount.ShowDiscountAction;
import by.hrachyshkin.provider.controller.action.impl.discount.UpdateDiscountAction;
import by.hrachyshkin.provider.controller.action.impl.promotion.CreatePromotionAction;
import by.hrachyshkin.provider.controller.action.impl.promotion.DeletePromotionAction;
import by.hrachyshkin.provider.controller.action.impl.promotion.ShowDiscountsForPromotionAction;
import by.hrachyshkin.provider.controller.action.impl.subscription.*;
import by.hrachyshkin.provider.controller.action.impl.tariff.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    final Map<String, Action> commands = new HashMap<>() {

        {
            put(CommandConstants.INDEX, new IndexAction());
            put(CommandConstants.CABINET, new CabinetAction());
            put(CommandConstants.CREATE_ACCOUNT, new CreateAccountAction());
            put(CommandConstants.DEPOSIT, new DepositMoneyForAccount());
            put(CommandConstants.LOGIN, new LoginAction());
            put(CommandConstants.LOGOUT, new LogoutAction());
            put(CommandConstants.ACCOUNTS, new ShowAccountsAction());
            put(CommandConstants.SUBSCRIPTIONS, new ShowSubscriptionsForAccountAction());
            put(CommandConstants.UPDATE_ACCOUNT, new UpdateAccountAction());
            put(CommandConstants.MAIN, new MainPageAction());
            put(CommandConstants.DISCOUNTS, new ShowDiscountAction());

            put(CommandConstants.CREATE_DISCOUNT, new CreateDiscountAction());
            put(CommandConstants.DELETE_DISCOUNT, new DeleteDiscountAction());
            put(CommandConstants.UPDATE_DISCOUNTS, new UpdateDiscountAction());

            put(CommandConstants.CREATE_PROMOTION, new CreatePromotionAction());
            put(CommandConstants.DELETE_PROMOTION, new DeletePromotionAction());
            put(CommandConstants.DISCOUNTS_FOR_PROMOTION, new ShowDiscountsForPromotionAction());

            put(CommandConstants.CREATE_SUBSCRIPTION, new CreateSubscriptionAction());
            put(CommandConstants.PAY_BILL_FOR_PROMOTION, new PayBillForSubscriptionAction());
            put(CommandConstants.DELETE_SUBSCRIPTION, new DeleteSubscriptionAction());
            put(CommandConstants.SHOW_BILLS_FOR_SUBSCRIPTION, new ShowBillsForSubscriptionAction());
            put(CommandConstants.SHOW_TRAFFICS_FOR_SUBSCRIPTION, new ShowTrafficsForSubscriptionAction());

            put(CommandConstants.CREATE_TARIFF, new CreateTariffAction());
            put(CommandConstants.DELETE_TARIFF, new DeleteTariffAction());
            put(CommandConstants.TARIFFS, new ShowTariffsAction());
            put(CommandConstants.UPDATE_TARIFF, new UpdateTariffAction());
        }
    };


    @Getter
    private static final ActionFactory INSTANCE = new ActionFactory();

    public Action getCommand(final String commandName) {
        return commands.get(commandName);
    }
}
