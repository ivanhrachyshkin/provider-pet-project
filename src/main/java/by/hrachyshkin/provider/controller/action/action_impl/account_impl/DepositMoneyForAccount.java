package by.hrachyshkin.provider.controller.action.action_impl.account_impl;

import by.hrachyshkin.provider.controller.action.action_impl.BaseAction;
import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public class DepositMoneyForAccount extends BaseAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            final AccountService accountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final Integer accountId = getAccountId(request);
            final String card = request.getParameter("card");
            final Float deposit = Float.valueOf(request.getParameter("sum"));
            final LocalDate validity = LocalDate.parse(request.getParameter("validity"));

            accountService.deposit(accountId, card, deposit, validity);

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
        }
        return "/cabinet";
    }
}
