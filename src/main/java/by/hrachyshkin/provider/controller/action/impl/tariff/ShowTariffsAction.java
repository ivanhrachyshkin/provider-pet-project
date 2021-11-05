package by.hrachyshkin.provider.controller.action.impl.tariff;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.TariffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowTariffsAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {

        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);

        final int offset = getOffset(request);
        setTotalPagesAttribute(request, tariffService.find());
        final String rawType = request.getParameter("filter");

        List<Tariff> tariffs;
        if (rawType == null || rawType.equals("all")) {
            tariffs = tariffService.findAndSortBySpeedAndPrice(offset);
        } else {
            final Tariff.Type type = Tariff.Type.valueOf(rawType.toUpperCase());
            tariffs = tariffService.findAndFilterByType(type);
        }


        request.setAttribute("tariffs", tariffs);

        if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
            return "/all-tariffs-for-admin.jsp";
        } else {
            final List<Tariff> accountTariffs = tariffService.findTariffsForSubscription(getAccountId(request));
            request.setAttribute("accountTariffs", accountTariffs);
            return "/all-tariffs-for-user.jsp";
        }
    }
}
