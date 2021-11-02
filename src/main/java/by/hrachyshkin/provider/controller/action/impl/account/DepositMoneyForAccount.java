package by.hrachyshkin.provider.controller.action.impl.account;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class DepositMoneyForAccount extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

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

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException, TransactionException {

        response.sendRedirect(request.getContextPath() + path);
    }
}
