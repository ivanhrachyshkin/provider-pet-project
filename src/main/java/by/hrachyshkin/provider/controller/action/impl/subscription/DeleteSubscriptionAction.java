package by.hrachyshkin.provider.controller.action.impl.subscription;

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

public class DeleteSubscriptionAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {

        try {
            final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION_SERVICE);

            final Integer accountId = getAccountId(request);
            final Integer tariffId = Integer.valueOf(request.getParameter("tariffId"));

            subscriptionService.deleteByAccountAndTariffId(accountId, tariffId);

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        return "/cabinet/subscriptions";
    }

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException, TransactionException {

        response.sendRedirect(request.getContextPath() + path);
    }
}
