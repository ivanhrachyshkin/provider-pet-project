package by.hrachyshkin.provider.controller.action.action_impl.subscription_impl;

import by.hrachyshkin.provider.controller.action.action_impl.BaseAction;
import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.model.Traffic;
import by.hrachyshkin.provider.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowTrafficsForSubscriptionAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {

        try {
            final TrafficService trafficService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TRAFFIC_SERVICE);
            final TariffService tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
            final AccountService accountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final Integer tariffId = Integer.valueOf(request.getParameter("tariffId"));
            final Tariff tariff = tariffService.findOneById(tariffId);
            final Integer accountId = getAccountId(request);
            final Account account = accountService.findOneById(accountId);

            final List<Traffic> subscriptionTraffics = trafficService.findTrafficForSubscription(accountId, tariffId);

            request.setAttribute("account", account);
            request.setAttribute("tariff", tariff);
            request.setAttribute("subscriptionTraffics", subscriptionTraffics);

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        return "/traffics-for-subscription.jsp";
    }
}
