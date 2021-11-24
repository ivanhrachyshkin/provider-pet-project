package by.hrachyshkin.provider.controller.action.impl.subscription;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.model.Traffic;
import by.hrachyshkin.provider.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowTrafficsForSubscriptionAction extends BaseAction {

    public static final String SHOW_TRAFFICS_FOR_SUBSCRIPTION = "/cabinet/subscriptions/traffics-for-subscription";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {

        try {
            final TrafficService trafficService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TRAFFIC_SERVICE);
            final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final int offset = getOffset(request);
            final Integer tariffId = Integer.valueOf(getTariffIdAttributeSession(request));
            final Tariff tariff = tariffService.findOneById(tariffId);
            final Integer accountId = getAccountId(request);
            final Account account = accountService.findOneById(accountId);
            final List<Traffic> subscriptionTraffics = trafficService.findTrafficForSubscription(accountId, tariffId, offset);

            setPage(request);
            setTotalPagesAttribute(request, subscriptionTraffics);
            request.setAttribute("account", account);
            request.setAttribute("tariff", tariff);
            request.setAttribute("subscriptionTraffics", subscriptionTraffics);

            System.out.println(request.getMethod() + "2");

        } catch (ServiceException | NumberFormatException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/traffics-for-subscription.jsp";
    }
}