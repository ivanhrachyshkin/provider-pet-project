package by.hrachyshkin.provider.controller.action.subscription_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.SubscriptionService;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@WebServlet("/cabinet/subscriptions/bills/pay")
public class PayBillForSubscriptionAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            final SubscriptionService subscriptionService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION_SERVICE);

            final Integer accountId = getAccountId(request);

            final Integer subscriptionId = Integer.valueOf(request.getParameter("subscriptionId"));
            final Float value = Float.valueOf(request.getParameter("value"));
            final LocalDate date = LocalDate.parse(request.getParameter("date"));

            subscriptionService.payBill(accountId, subscriptionId, value, date);

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.setAttribute("tariffId", request.getParameter("tariffId"));
        request.getRequestDispatcher("/cabinet/subscriptions/bills").forward(request, response);
    }
}
