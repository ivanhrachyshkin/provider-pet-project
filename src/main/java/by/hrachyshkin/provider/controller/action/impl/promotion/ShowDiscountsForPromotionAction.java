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
import java.io.IOException;
import java.util.List;

public class ShowDiscountsForPromotionAction extends BaseAction {

    public static final String SHOW_DISCOUNTS_FOR_PROMOTION =
            "/tariffs/discounts-for-promotion";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException, ServiceException {

        try {
            final String tariffId = getTariffIdAttributeSession(request);

            final DiscountService discountService = ServiceFactory.getINSTANCE()
                    .getService(ServiceKeys.DISCOUNT);
            final TariffService tariffService = ServiceFactory.getINSTANCE()
                    .getService(ServiceKeys.TARIFF);

            final Tariff tariff;
            tariff = tariffService.findOneById(Integer.valueOf(tariffId));

            final List<Discount> tariffDiscounts = discountService
                    .findDiscountsForPromotion(Integer.valueOf(tariffId));
            final List<Discount> discounts = discountService.find();

            request.setAttribute("tariff", tariff);
            request.setAttribute("tariffDiscounts", tariffDiscounts);
            request.setAttribute("discounts", discounts);

        } catch (TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }

        if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
            return "/disounts-for-promotion-admin.jsp";
        } else {
            return "/discounts-for-promotion-user.jsp";
        }
    }
}
