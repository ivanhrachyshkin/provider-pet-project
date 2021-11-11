package by.hrachyshkin.provider.controller.action.discount_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.service.*;
import by.hrachyshkin.provider.service.impl.DiscountServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@WebServlet("/discounts/create")
public class CreateDiscountAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {

        final DiscountService discountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);

        try {
            final String name = request.getParameter("name");
            final Discount.Type type = Discount.Type.valueOf(request.getParameter("type").toUpperCase());
            final Integer value = Integer.valueOf(request.getParameter("value"));
            final LocalDate dateFrom = LocalDate.parse(request.getParameter("dateFrom"));
            final LocalDate dateTo = LocalDate.parse(request.getParameter("dateTo"));
            discountService.add(new Discount(name, type, value, dateFrom, dateTo));
        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/discounts").forward(request, response);
    }
}
