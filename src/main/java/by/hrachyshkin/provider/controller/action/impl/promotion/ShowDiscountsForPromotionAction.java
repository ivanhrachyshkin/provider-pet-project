package by.hrachyshkin.provider.controller.action.impl.promotion;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShowDiscountsForPromotionAction extends BaseAction {

    public static final String DISCOUNTS_FOR_PROMOTION = "/tariffs/discounts";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {

        final String tariffId;

        if (request.getMethod().equals("GET")) {
            HttpSession session = request.getSession();
            tariffId = (String) session.getAttribute("tariffId");
        } else {
            tariffId = request.getParameter("tariffId");
        }

        final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);
        final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);


        final Tariff tariff = tariffService.findOneById(Integer.valueOf(tariffId));

        final List<Discount> tariffDiscounts = discountService.findDiscountsForPromotion(Integer.valueOf(tariffId));
        final List<Discount> discounts = discountService.find();

        request.setAttribute("tariff", tariff);
        request.setAttribute("tariffDiscounts", tariffDiscounts);
        request.setAttribute("discounts", discounts);

        if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
            return "/discounts-for-tariff-admin.jsp";
        } else {
            return "/discounts-for-tariff-user.jsp";
        }
    }
}
