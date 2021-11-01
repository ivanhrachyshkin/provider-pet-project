package by.hrachyshkin.provider.controller.action.action_impl.account_impl;

import by.hrachyshkin.provider.controller.action.action_impl.BaseAction;
import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginAction extends BaseAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){

        String path;

        try {
            final AccountService accountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final String email = request.getParameter("email");
            final String password = request.getParameter("password");

            if (accountService.isExistByEmailAndPassword(email, password)) {
                final Account account = accountService.findOneByEmail(email);

                final HttpSession session = request.getSession(false);
                session.setAttribute("accountId", account.getId());
                session.setAttribute("accountRole", account.getRole());

                path = "/cabinet";

            } else {
                request.setAttribute("error", "Check Email and Password");
                path = "/";
            }

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
            path = "/";
        }
        return path;
    }
}



