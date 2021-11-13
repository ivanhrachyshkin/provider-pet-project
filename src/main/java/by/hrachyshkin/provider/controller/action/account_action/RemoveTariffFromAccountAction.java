package by.hrachyshkin.provider.controller.action.account_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.SubscriptionService;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/cabinet/myTariffs/remove")
public class RemoveTariffFromAccountAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            final SubscriptionService subscriptionService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION_SERVICE);
            final HttpSession session = request.getSession(false);
            final Integer accountId = (Integer) session.getAttribute("accountId");
            final Integer tariffId = Integer.valueOf(request.getParameter("tariffId"));
            subscriptionService.deleteByAccountAndTariffId(accountId, tariffId);
        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/cabinet/myTariffs").forward(request, response);
    }
}
