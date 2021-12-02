package by.hrachyshkin.provider.controller.action.impl.subscription;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.model.Traffic;
import by.hrachyshkin.provider.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowTrafficsForSubscriptionAction extends BaseAction {

    public static final String SHOW_TRAFFIC_FOR_SUBSCRIPTION =
            "/cabinet/subscriptions/traffics-for-subscription";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException, ServiceException {

        try {
            final TrafficService trafficService = ServiceFactory
                    .getINSTANCE().getService(ServiceKeys.TRAFFIC);
            final TariffService tariffService = ServiceFactory
                    .getINSTANCE().getService(ServiceKeys.TARIFF);
            final AccountService accountService = ServiceFactory
                    .getINSTANCE().getService(ServiceKeys.ACCOUNT);
            final SubscriptionService subscriptionService = ServiceFactory
                    .getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);

            final int offset = getOffset(request);

            final Integer tariffId =
                    Integer.valueOf(getTariffIdAttributeSession(request));
            final Tariff tariff =
                    tariffService.findOneById(tariffId);

            final Integer accountId = getAccountId(request);
            final Account account = accountService.findOneById(accountId);

            final Subscription subscription = subscriptionService
                    .findOneByAccountIdAndTariffId(accountId, tariffId);
            final List<Traffic> subscriptionTraffics = trafficService
                    .findAndFilterAndSortOffset(subscription.getId(), offset);

            setPageNumber(request);
            setTotalPagesAttribute(request, trafficService
                    .findAndFilterBySubscriptionId(subscription.getId()));
            request.setAttribute("account", account);
            request.setAttribute("tariff", tariff);
            request.setAttribute("subscriptionTraffics",
                    subscriptionTraffics);

        } catch (ServiceException | NumberFormatException
                | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/traffics-for-subscription.jsp";
    }
}
