package by.hrachyshkin.provider.controller.action.impl.account;

import by.hrachyshkin.provider.controller.action.impl.ActionException;
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

    public static final String UPDATE_ACCOUNT_CABINET =
            "/cabinet/accounts/update-cabinet";
    public static final String UPDATE_ACCOUNT_LIST =
            "/cabinet/accounts/update-list";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException {

        try {
            checkGetHTTPMethod(request);
            final AccountService accountService = ServiceFactory.getINSTANCE()
                    .getService(ServiceKeys.ACCOUNT);

            final Integer accountId =
                    getIntParameter(request, "accountId");
            final String email = getStringParameter(request, "email");
            final Account.Role role = Account.Role.valueOf(
                    getStringParameter(request, "role"));
            final String name = getStringParameter(request, "name");
            final String phone = getStringParameter(request, "phone");
            final String address = getStringParameter(request, "address");
            final Float balance = getFloatParameter(request, "balance");

            accountService.update(new Account(accountId, email, role,
                    name, phone, address, balance));

            setPageNumberAttributeToSession(request);

        } catch (ServiceException | TransactionException | ActionException e) {
            setErrorAttributeToSession(request, e.getMessage());
            setPageNumberAttributeToSession(request);
        }

        if (request.getServletPath().equals(UPDATE_ACCOUNT_CABINET)) {
            return CabinetAction.CABINET;
        } else {
            return ShowAccountsAction.ACCOUNTS;
        }
    }

    @Override
    public void postExecute(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final String path)
            throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
