package by.hrachyshkin.provider.controller.action.action_factory;

import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.controller.action.action_impl.MainPageAction;
import by.hrachyshkin.provider.controller.action.action_impl.IndexAction;
import by.hrachyshkin.provider.controller.action.action_impl.account_impl.*;
import by.hrachyshkin.provider.controller.action.action_impl.discount_impl.CreateDiscountAction;
import by.hrachyshkin.provider.controller.action.action_impl.discount_impl.DeleteDiscountAction;
import by.hrachyshkin.provider.controller.action.action_impl.discount_impl.ShowDiscountAction;
import by.hrachyshkin.provider.controller.action.action_impl.discount_impl.UpdateDiscountAction;
import by.hrachyshkin.provider.controller.action.action_impl.promotion_impl.CreatePromotionAction;
import by.hrachyshkin.provider.controller.action.action_impl.promotion_impl.DeletePromotionAction;
import by.hrachyshkin.provider.controller.action.action_impl.promotion_impl.ShowDiscountsForPromotionAction;
import by.hrachyshkin.provider.controller.action.action_impl.subscription_impl.*;
import by.hrachyshkin.provider.controller.action.action_impl.tariff_impl.CreateTariffAction;
import by.hrachyshkin.provider.controller.action.action_impl.tariff_impl.DeleteTariffAction;
import by.hrachyshkin.provider.controller.action.action_impl.tariff_impl.ShowTariffsAction;
import by.hrachyshkin.provider.controller.action.action_impl.tariff_impl.UpdateTariffAction;
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
