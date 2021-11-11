package by.hrachyshkin.provider.controller.action.discount_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.Service;
import by.hrachyshkin.provider.service.impl.DiscountServiceImpl;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/discounts")
public class ShowDiscountAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final DiscountService discountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);

        final List<Discount> discounts;
        final String rawType = request.getParameter("filter");

        if (rawType != null) {
            final Discount.Type type = Discount.Type.valueOf(rawType.toUpperCase());
            discounts = discountService.findAndFilterByType(type);
        } else discounts = discountService.find();

        request.setAttribute("discounts", discounts);
        request.getRequestDispatcher("discounts.jsp").forward(request, response);
    }
}
