package by.hrachyshkin.provider.controller.action.impl.discount;

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
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            checkGetHTTPMethod(request);

            final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);

            final String name = request.getParameter("name");
            final Discount.Type type = Discount.Type.valueOf(request.getParameter("type").toUpperCase());
            final Integer value = Integer.valueOf(request.getParameter("value"));
            final LocalDate dateFrom = LocalDate.parse(request.getParameter("dateFrom"));
            final LocalDate dateTo = LocalDate.parse(request.getParameter("dateTo"));

            discountService.add(new Discount(name, type, value, dateFrom, dateTo));

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/discounts";
    }

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
