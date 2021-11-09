package by.hrachyshkin.provider.controller.action.action_impl.discount_impl;

import by.hrachyshkin.provider.controller.action.action_impl.BaseAction;
import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class UpdateDiscountAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            final DiscountService discountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);

            final Integer id = Integer.valueOf(request.getParameter("id"));
            final String name = request.getParameter("name");
            final Discount.Type type = Discount.Type.valueOf(request.getParameter("type").toUpperCase());
            final Integer value = Integer.valueOf(request.getParameter("value"));
            final LocalDate dateFrom = LocalDate.parse(request.getParameter("dateFrom"));
            final LocalDate dateTo = LocalDate.parse(request.getParameter("dateTo"));

            discountService.update(new Discount(id, name, type, value, dateFrom, dateTo));

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
        }
        return "/discounts";
    }
}
