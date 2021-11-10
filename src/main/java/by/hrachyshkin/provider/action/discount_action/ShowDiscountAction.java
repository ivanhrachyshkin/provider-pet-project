package by.hrachyshkin.provider.action.discount_action;

import by.hrachyshkin.provider.entity.Discount;
import by.hrachyshkin.provider.entity.Tariff;
import by.hrachyshkin.provider.service.DiscountServiceImpl;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.TariffServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/discounts")
public class ShowDiscountAction extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final DiscountServiceImpl discountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);

        final List<Discount> discounts;
        final String rawType = request.getParameter("filter");
        final String tariffId = request.getParameter("id");

        if (rawType != null) {
            final Discount.Type type = Discount.Type.valueOf(rawType.toUpperCase());
            discounts = discountService.findAndFilterByType(type);
        } else if (tariffId != null) {
            discounts = discountService.findDiscountsForTariff(Integer.valueOf(tariffId));
        } else discounts = discountService.find();

        request.setAttribute("discounts", discounts);
        request.getRequestDispatcher("discounts.jsp").forward(request, response);
    }
}
