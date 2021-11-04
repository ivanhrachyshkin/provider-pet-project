package by.hrachyshkin.provider.controller.action.impl.account;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CabinetAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String page = null;

        try {
            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final Account account = accountService.findOneById(getAccountId(request));

            if (getRole(request).equals(Account.Role.BLOCKED)) {
                request.setAttribute("error", "Account is blocked");
                page ="/";
            }

            if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
                request.setAttribute("account", account);
                page ="/cabinet-admin.jsp";
            }

            if (getRole(request).equals(Account.Role.USER)) {
                request.setAttribute("account", account);
                page = "/cabinet-user.jsp";
            }

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
             page = "/";
        }
        return page;
    }
}
