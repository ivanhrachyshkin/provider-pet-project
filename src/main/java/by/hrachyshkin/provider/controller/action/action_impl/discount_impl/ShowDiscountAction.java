package by.hrachyshkin.provider.controller.action.action_impl.discount_impl;

import by.hrachyshkin.provider.controller.action.action_impl.BaseAction;
import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowDiscountAction extends BaseAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String path = null;
        try {
            final DiscountService discountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);

            final String rawType = request.getParameter("filter");
            final List<Discount> discounts;

            if (rawType == null || rawType.equals("all")) {
                discounts = discountService.find();
            } else {
                final Discount.Type type = Discount.Type.valueOf(rawType.toUpperCase());
                discounts = discountService.findAndFilterByType(type);
            }

            request.setAttribute("discounts", discounts);

            if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
                path = "all-discounts-for-admin.jsp";
            } else {
                path = "all-discounts-for-user.jsp";
            }

        } catch (TransactionException | ServiceException e) {
            request.setAttribute("error", e.getMessage());
        }
        return path;
    }
}
