package by.hrachyshkin.provider.controller.action.account_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.service.AccountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cabinet/update")
public class UpdateAccountAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try {
            final AccountService accountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final Integer id = Integer.valueOf(request.getParameter("accountId"));
            final String email = request.getParameter("email");
            final String password = request.getParameter("password");
            final Account.Role role = Account.Role.valueOf(request.getParameter("role"));
            final String name = request.getParameter("name");
            final String phone = request.getParameter("phone");
            final String address = request.getParameter("address");
            final Float balance = Float.valueOf(request.getParameter("balance"));

            accountService.update(new Account(id, email, password, role, name, phone, address, balance));

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/cabinet").forward(request, response);
    }
}
