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
import java.util.List;

public class ShowAccountsAction extends BaseAction {

    public static final String ACCOUNTS = "/cabinet/accounts";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response) {

        try {
            final AccountService accountService = ServiceFactory.getINSTANCE()
                    .getService(ServiceKeys.ACCOUNT);

            final int offset = getOffset(request);
            final List<Account> accounts = accountService
                    .findAndSortByName(offset);

            setPageNumber(request);
            setTotalPagesAttribute(request, accountService.find());
            request.setAttribute("accounts", accounts);

            removeAttribute(request, "page");

        } catch (ServiceException | NumberFormatException
                | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/accounts.jsp";
    }
}
