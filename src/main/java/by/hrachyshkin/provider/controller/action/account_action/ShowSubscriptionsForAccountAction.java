package by.hrachyshkin.provider.controller.action.account_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.*;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/cabinet/subscriptions")
public class ShowSubscriptionsForAccountAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

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

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/subscriptions-for-account.jsp").forward(request, response);
    }
}
