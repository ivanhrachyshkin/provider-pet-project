package by.hrachyshkin.provider.controller.action.impl.subscription;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.SubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class PayBillForSubscriptionAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {

        try {
            final SubscriptionService subscriptionService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION_SERVICE);

            final Integer accountId = getAccountId(request);

            final Integer subscriptionId = Integer.valueOf(request.getParameter("subscriptionId"));
            final Float value = Float.valueOf(request.getParameter("value"));
            final LocalDate date = LocalDate.parse(request.getParameter("date"));

            subscriptionService.payBill(accountId, subscriptionId, value, date);

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.setAttribute("tariffId", request.getParameter("tariffId"));
        return "/cabinet/subscriptions/bills";
    }
}
