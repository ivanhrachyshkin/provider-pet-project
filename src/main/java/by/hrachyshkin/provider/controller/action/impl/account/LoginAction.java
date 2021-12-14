package by.hrachyshkin.provider.controller.action.impl.account;

import by.hrachyshkin.provider.controller.action.impl.ActionException;
import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.controller.action.impl.WelcomeAction;
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
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException {

        String path;

        try {
            checkGetHTTPMethod(request);
            final AccountService accountService = ServiceFactory.getINSTANCE()
                    .getService(ServiceKeys.ACCOUNT);

            final String email = getStringParameter(request, "email");
            final String password = getStringParameter(request, "password");

            if (accountService.isExistByEmailAndPassword(email, password)) {
                final Account account = accountService.findOneByEmail(email);

                final HttpSession session = request.getSession(false);
                session.setAttribute("accountId", account.getId());
                session.setAttribute("accountRole", account.getRole());

                path = CabinetAction.CABINET;

            } else {
                setErrorAttributeToSession(request,
                        "Check Email and Password");
                path = WelcomeAction.WELCOME;
            }

        } catch (ServiceException | TransactionException | ActionException e) {
            setErrorAttributeToSession(request, e.getMessage());
            path = WelcomeAction.WELCOME;
        }
        return path;
    }

    @Override
    public void postExecute(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final String path)
            throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}



