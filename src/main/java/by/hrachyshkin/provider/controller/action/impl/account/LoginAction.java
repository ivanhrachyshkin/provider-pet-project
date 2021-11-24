package by.hrachyshkin.provider.controller.action.impl.account;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginAction extends BaseAction {

    public static final String LOGIN = "/login";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String path;

        try {
            checkHttpMethod(request);
            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final String email = request.getParameter("email");
            final String password = request.getParameter("password");

            if (accountService.isExistByEmailAndPassword(email, password)) {
                final Account account = accountService.findOneByEmail(email);

                final HttpSession session = request.getSession(false);
                session.setAttribute("accountId", account.getId());
                session.setAttribute("accountRole", account.getRole());

                path = "/cabinet";

            } else {
                setErrorAttributeToSession(request, "Check Email and Password");
                path = "/";
            }

        } catch (ServiceException | NumberFormatException | TransactionException | ServletException e) {
            setErrorAttributeToSession(request, e.getMessage());
            path = "/";
        }
        return path;
    }

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException, TransactionException {
        response.sendRedirect(request.getContextPath() + path);
    }
}



