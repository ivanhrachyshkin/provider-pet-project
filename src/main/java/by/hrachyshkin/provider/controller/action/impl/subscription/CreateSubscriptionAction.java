package by.hrachyshkin.provider.controller.action.impl.subscription;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.SubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateSubscriptionAction extends BaseAction {

    public static final String CREATE_SUBSCRIPTION =
            "/cabinet/subscriptions/create";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException, ServiceException {

        try {
            checkGetHTTPMethod(request);
            final SubscriptionService subscriptionService = ServiceFactory
                    .getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);

            final Integer accountId = getAccountId(request);
            final Integer tariffId =
                    Integer.valueOf(request.getParameter("tariffId"));

            subscriptionService.add(new Subscription(accountId, tariffId));

        } catch (ServiceException | NumberFormatException
                | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return ShowSubscriptionsForAccountAction.SUBSCRIPTIONS;
    }

    @Override
    public void postExecute(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final String path)
            throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
