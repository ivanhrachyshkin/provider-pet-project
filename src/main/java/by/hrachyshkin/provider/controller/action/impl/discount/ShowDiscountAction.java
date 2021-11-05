package by.hrachyshkin.provider.controller.action.impl.discount;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowDiscountAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String path = null;
        try {
            final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);

            final int offset = getOffset(request);
            final String rawType = request.getParameter("filter");

            final List<Discount> discounts;
            if (rawType == null || rawType.equals("all")) {
                discounts = discountService.findAndSortByValue(offset);
            } else {
                final Discount.Type type = Discount.Type.valueOf(rawType.toUpperCase());
                discounts = discountService.findAndFilterByType(type);
            }

            pagination(request);
            setTotalPagesAttribute(request, discountService.find());
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
