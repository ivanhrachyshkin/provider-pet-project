package by.hrachyshkin.provider.controller.action.subscription_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.model.Traffic;
import by.hrachyshkin.provider.service.*;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/cabinet/subscriptions/traffics")
public class ShowTrafficsForSubscriptionAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            final TrafficService trafficService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TRAFFIC_SERVICE);
            final TariffService tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
            final AccountService accountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final Integer tariffId = Integer.valueOf(request.getParameter("tariffId"));
            final Tariff tariff = tariffService.findOneById(tariffId);
            final Integer accountId = getAccountId(request);
            final Account account = accountService.findOneById(accountId);

            final List<Traffic> subscriptionTraffics = trafficService.findTrafficForTariffPerAccount(accountId, tariffId);

            request.setAttribute("account", account);
            request.setAttribute("tariff", tariff);
            request.setAttribute("subscriptionTraffics", subscriptionTraffics);

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/traffics-for-subscription.jsp").forward(request, response);
    }
}
