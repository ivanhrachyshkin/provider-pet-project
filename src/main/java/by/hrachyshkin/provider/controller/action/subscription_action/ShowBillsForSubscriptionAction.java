package by.hrachyshkin.provider.controller.action.subscription_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Bill;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.*;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/cabinet/subscriptions/bills")
public class ShowBillsForSubscriptionAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            final BillService billService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.BILL_SERVICE);
            final TariffService tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
            final AccountService accountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final Integer tariffId = Integer.valueOf(request.getParameter("tariffId"));
            final Integer accountId = getAccountId(request);
            final Tariff tariff = tariffService.findOneById(tariffId);
            final Account account = accountService.findOneById(accountId);

            request.setAttribute("tariff", tariff);
            request.setAttribute("account", account);

            final List<Bill> subscriptionBills = billService.findBillsForSubscription(accountId, tariffId);

            request.setAttribute("subscriptionBills", subscriptionBills);

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/bills-for-subscription.jsp").forward(request, response);
    }
}
