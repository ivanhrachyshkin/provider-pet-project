package by.hrachyshkin.provider.controller.action.action_impl.promotion_impl;

import by.hrachyshkin.provider.controller.action.action_impl.BaseAction;
import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowDiscountsForPromotionAction extends BaseAction{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {

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
            return "/discounts-for-tariff-admin.jsp";
        } else {
            return "/discounts-for-tariff-user.jsp";
        }
    }
}
