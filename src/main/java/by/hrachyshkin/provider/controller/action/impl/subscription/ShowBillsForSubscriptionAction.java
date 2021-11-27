package by.hrachyshkin.provider.controller.action.impl.subscription;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Bill;
import by.hrachyshkin.provider.model.Subscription;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowBillsForSubscriptionAction extends BaseAction {

    public static final String SHOW_BILLS_FOR_SUBSCRIPTION = "/cabinet/subscriptions/bills-for-subscription";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException {

        try {
            final BillService billService = ServiceFactory.getINSTANCE().getService(ServiceKeys.BILL_SERVICE);
            final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);
            final SubscriptionService subscriptionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.SUBSCRIPTION_SERVICE);

            final int offset = getOffset(request);

            final Integer tariffId = Integer.valueOf(getTariffIdAttributeSession(request));
            final Tariff tariff = tariffService.findOneById(tariffId);

            final Integer accountId = getAccountId(request);
            final Account account = accountService.findOneById(accountId);

            final Subscription subscription = subscriptionService.findOneByAccountIdAndTariffId(accountId, tariffId);
            final List<Bill> subscriptionBills = billService.findAndFilterAndSortOffset(subscription.getId(), offset);

            setPage(request);
            setTotalPagesAttribute(request, billService.findAndFilterBySubscriptionId(subscription.getId()));
            request.setAttribute("tariff", tariff);
            request.setAttribute("account", account);
            request.setAttribute("subscriptionBills", subscriptionBills);

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/bills-for-subscription.jsp";
    }
}