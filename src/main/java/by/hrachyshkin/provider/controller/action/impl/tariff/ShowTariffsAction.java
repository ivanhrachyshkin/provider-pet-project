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

    public static final String TARIFFS = "/tariffs";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException, ServiceException {

        try {
            final TariffService tariffService = ServiceFactory.getINSTANCE()
                    .getService(ServiceKeys.TARIFF);

            final int offset = getOffset(request);
            final String rawType = request.getParameter("filter");

            List<Tariff> tariffs;
            if (rawType == null || rawType.isEmpty() || rawType.equals("all")) {
                tariffs = tariffService.findAndSortBySpeedAndPrice(offset);

                setTotalPagesAttribute(request, tariffService.find());

            } else {
                final Tariff.Type type =
                        Tariff.Type.valueOf(rawType.toUpperCase());
                tariffs = tariffService.findAndFilterByTypeOffset(type, offset);

                setTotalPagesAttribute(request,
                        tariffService.findAndFilterAndSort(type));
            }

            setPageNumber(request);
            request.setAttribute("tariffs", tariffs);
            request.setAttribute("filter", rawType);

            removeAttribute(request, "page");

        } catch (TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }

        if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
            return "/jsp/all-tariffs-for-admin.jsp";
        } else {
            return "/jsp/all-tariffs-for-user.jsp";
        }
    }
}
