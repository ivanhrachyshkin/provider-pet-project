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

public class UpdateAccountAction extends BaseAction {

    public static final String UPDATE_ACCOUNT = "/cabinet/accounts/update";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            checkHttpMethod(request);

            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final Integer id = Integer.valueOf(request.getParameter("accountId"));
            final String email = request.getParameter("email");
            final String password = request.getParameter("password");
            final Account.Role role = Account.Role.valueOf(request.getParameter("role"));
            final String name = request.getParameter("name");
            final String phone = request.getParameter("phone");
            final String address = request.getParameter("address");
            final Float balance = Float.valueOf(request.getParameter("balance"));

            accountService.update(new Account(id, email, password, role, name, phone, address, balance));

        } catch (ServiceException | NumberFormatException | TransactionException | ServletException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }

        return "/cabinet";
    }

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException, TransactionException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
