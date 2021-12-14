package by.hrachyshkin.provider.controller.action.impl.discount;

import by.hrachyshkin.provider.controller.action.impl.ActionException;
import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class CreateDiscountAction extends BaseAction {

    public static final String CREATE_DISCOUNT = "/discounts/create";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException {

        try {
            checkGetHTTPMethod(request);
            final DiscountService discountService = ServiceFactory.getINSTANCE()
                    .getService(ServiceKeys.DISCOUNT);

            final String name = getStringParameter(request, "name");
            final Discount.Type type = Discount.Type.valueOf(
                    getStringParameter(request, "type").toUpperCase());
            final Integer value = getIntParameter(request, "value");
            final LocalDate dateFrom =
                    getDateParameter(request, "dateFrom");
            final LocalDate dateTo = getDateParameter(request, "dateTo");

            discountService.add(new Discount(name, type, value, dateFrom,
                    dateTo));

            setPageNumberAttributeToSession(request);

        } catch (ServiceException | TransactionException | ActionException e) {
            setErrorAttributeToSession(request, e.getMessage());
            setPageNumberAttributeToSession(request);
        }
        return ShowDiscountAction.DISCOUNTS;
    }

    @Override
    public void postExecute(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final String path)
            throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
