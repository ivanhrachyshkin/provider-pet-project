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

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            final AccountService accountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE);

            final int offset = getOffset(request);
            final List<Account> accounts = accountService.findAndSortByName(offset);

            pagination(request);
            setTotalPagesAttribute(request, accountService.find());
            request.setAttribute("accounts", accounts);

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
        }
       return "/accounts.jsp";
    }
}
