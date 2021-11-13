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

@WebServlet("/cabinet/subscriptions/remove")
public class RemoveSubscriptionAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            final SubscriptionService subscriptionService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION_SERVICE);

            final Integer accountId = getAccountId(request);
            final Integer tariffId = Integer.valueOf(request.getParameter("tariffId"));

            subscriptionService.deleteByAccountAndTariffId(accountId, tariffId);

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/cabinet/subscriptions").forward(request, response);
    }
}
