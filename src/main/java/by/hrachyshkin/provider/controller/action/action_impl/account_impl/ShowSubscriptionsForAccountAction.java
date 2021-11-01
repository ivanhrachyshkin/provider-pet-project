package by.hrachyshkin.provider.controller.action.action_impl.account_impl;

import by.hrachyshkin.provider.controller.action.action_impl.BaseAction;
import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowSubscriptionsForAccountAction extends BaseAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            final TariffService tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
            final AccountService accountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final Integer accountId = getAccountId(request);
            final Account account = accountService.findOneById(accountId);

            final List<Tariff> accountTariffs = tariffService.findTariffsForSubscription(accountId);
            final List<Tariff> tariffs = tariffService.find();

            request.setAttribute("account", account);
            request.setAttribute("accountTariffs", accountTariffs);
            request.setAttribute("tariffs", tariffs);

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
        }
        return "/subscriptions-for-account.jsp";
    }
}
