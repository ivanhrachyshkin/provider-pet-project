package by.hrachyshkin.provider.controller.action.account_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cabinet")
public class CabinetAction extends BaseAction {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            final AccountService accountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final Account account = accountService.findOneById(getAccountId(request));

            if (getRole(request).equals(Account.Role.BLOCKED)) {
                request.setAttribute("error", "Account is blocked");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

            if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
                request.setAttribute("account", account);
                request.getRequestDispatcher("/cabinet-admin.jsp").forward(request, response);
            }

            if (getRole(request).equals(Account.Role.USER)) {
                request.setAttribute("account", account);
                request.getRequestDispatcher("/cabinet-user.jsp").forward(request, response);
            }

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
