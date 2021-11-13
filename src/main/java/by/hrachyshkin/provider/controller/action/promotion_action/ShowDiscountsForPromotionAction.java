package by.hrachyshkin.provider.controller.action.promotion_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.TariffService;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tariffs/discounts")
public class ShowDiscountsForPromotionAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final DiscountService discountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);
        final TariffService tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);

        final Integer tariffId = Integer.valueOf(request.getParameter("tariffId"));
        final Tariff tariff = tariffService.findOneById(tariffId);

        final List<Discount> tariffDiscounts = discountService.findDiscountsForPromotion(tariffId);
        final List<Discount> discounts = discountService.find();

        request.setAttribute("tariff", tariff);
        request.setAttribute("tariffDiscounts", tariffDiscounts);
        request.setAttribute("discounts", discounts);

        if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
            request.getRequestDispatcher("/discounts-for-tariff-admin.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/discounts-for-tariff-user.jsp").forward(request, response);
        }
    }
}
