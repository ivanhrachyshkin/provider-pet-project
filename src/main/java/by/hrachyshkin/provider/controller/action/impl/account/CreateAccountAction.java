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
import java.io.IOException;

public class CreateAccountAction extends BaseAction {

    public static final String CREATE_ACCOUNT = "/cabinet/accounts/create";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            checkGetHTTPMethod(request);

            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final String email = request.getParameter("email");
            final String password = request.getParameter("password");
            final Account.Role role = Account.Role.valueOf(request.getParameter("role"));
            final String name = request.getParameter("name");
            final String phone = request.getParameter("phone");
            final String address = request.getParameter("address");
            final Float balance = 0.0f;

            accountService.add(new Account(email, password, role, name, phone, address, balance));
        } catch (ServiceException | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/cabinet/accounts";
    }

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
