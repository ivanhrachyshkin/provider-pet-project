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

public class CreateAccountAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final String email = request.getParameter("email");
            final String password = request.getParameter("password");
            final Account.Role role = Account.Role.valueOf(request.getParameter("role"));
            final String name = request.getParameter("name");
            final String phone = request.getParameter("phone");
            final String address = request.getParameter("address");
            final Float balance = 0.0f;

            accountService.add(new Account(email, password, role, name, phone, address, balance));

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
        }
        return "/cabinet/accounts";
    }
}
