package by.hrachyshkin.provider.controller.action.impl.subscription;

import by.hrachyshkin.provider.controller.action.impl.ActionException;
import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.SubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class PayBillForSubscriptionAction extends BaseAction {

    public static final String PAY_BILL_FOR_SUBSCRIPTION =
            "/cabinet/subscriptions/bills/pay";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException, ServiceException {

        try {
            checkGetHTTPMethod(request);
            final SubscriptionService subscriptionService = ServiceFactory
                    .getINSTANCE().getService(ServiceKeys.SUBSCRIPTION);

            final Integer accountId = getAccountId(request);
            final Integer subscriptionId =
                    getIntParameter(request, "subscriptionId");
            final Float value = getFloatParameter(request, "value");
            final LocalDate date = getDateParameter(request, "date");

            subscriptionService.payBillForSubscription(
                    accountId, subscriptionId, value, date);

            setPageNumberAttributeToSession(request);

        } catch (ServiceException | TransactionException | ActionException e) {
            setErrorAttributeToSession(request, e.getMessage());
            setPageNumberAttributeToSession(request);
        }
        request.setAttribute("tariffId", request.getParameter("tariffId"));

        return ShowBillsForSubscriptionAction.SHOW_BILLS_FOR_SUBSCRIPTION;
    }

    @Override
    public void postExecute(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final String path)
            throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
