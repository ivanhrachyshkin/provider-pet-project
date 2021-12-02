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

    public static final String DEPOSIT = "/cabinet/deposit";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException {

        try {
            checkGetHTTPMethod(request);
            final AccountService accountService = ServiceFactory.getINSTANCE()
                    .getService(ServiceKeys.ACCOUNT);

            final Integer accountId = getAccountId(request);
            final String card = request.getParameter("card");
            final Float deposit =
                    Float.valueOf(request.getParameter("sum"));
            final LocalDate validity =
                    LocalDate.parse(request.getParameter("validity"));

            accountService.deposit(accountId, card, deposit, validity);

        } catch (ServiceException | NumberFormatException
                | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return CabinetAction.CABINET;
    }

    @Override
    public void postExecute(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final String path)
            throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
