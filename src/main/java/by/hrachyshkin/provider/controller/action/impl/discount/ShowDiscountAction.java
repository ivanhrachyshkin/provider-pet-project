package by.hrachyshkin.provider.controller.action.impl.discount;

import by.hrachyshkin.provider.controller.action.impl.ActionException;
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

    public static final String DISCOUNTS = "/discounts";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response) {

        String path = null;
        try {
            final DiscountService discountService = ServiceFactory.getINSTANCE()
                    .getService(ServiceKeys.DISCOUNT);

            final int offset = getOffset(request);
            final String rawType = request.getParameter("filter");

            final List<Discount> discounts;
            if (rawType == null || rawType.isEmpty() || rawType.equals("all")) {
                discounts = discountService.findAndSortByValue(offset);
                setTotalPagesAttribute(request, discountService.find());
            } else {
                final Discount.Type type = Discount.Type.valueOf(rawType
                        .toUpperCase());
                discounts = discountService
                        .findAndFilterByTypeOffset(type, offset);

                setTotalPagesAttribute(request, discountService
                        .findAndFilterByTypeAndSortByValue(type));
            }

            setPageNumber(request);
            request.setAttribute("discounts", discounts);

            removeAttribute(request, "page");

            if (getRole(request).equals(Account.Role.ADMINISTRATOR)) {
                path = "/jsp/all-discounts-for-admin.jsp";
            } else {
                path = "/jsp/all-discounts-for-user.jsp";
            }

        } catch (ServiceException | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return path;
    }
}
