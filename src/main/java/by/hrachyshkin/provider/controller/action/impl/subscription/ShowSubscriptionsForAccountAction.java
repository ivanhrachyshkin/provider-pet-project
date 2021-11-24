package by.hrachyshkin.provider.controller.action.impl.subscription;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowSubscriptionsForAccountAction extends BaseAction {

    public static final String SUBSCRIPTIONS = "/cabinet/subscriptions";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final Integer accountId = getAccountId(request);
            final Account account = accountService.findOneById(accountId);

            final List<Tariff> accountTariffs = tariffService.findTariffsForSubscription(accountId);
            final List<Tariff> tariffs = tariffService.find();

            request.setAttribute("account", account);
            request.setAttribute("accountTariffs", accountTariffs);
            request.setAttribute("tariffs", tariffs);

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/subscriptions-for-account.jsp";
    }
}
