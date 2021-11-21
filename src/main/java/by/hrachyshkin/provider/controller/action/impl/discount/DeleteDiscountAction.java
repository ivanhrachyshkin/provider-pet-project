package by.hrachyshkin.provider.controller.action.impl.discount;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.service.DiscountService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteDiscountAction extends BaseAction {

    public static final String DELETE_DISCOUNT = "/discounts/delete";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        try {
            final DiscountService discountService = ServiceFactory.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);

            final Integer id = Integer.valueOf(request.getParameter("discountId"));
            discountService.delete(id);

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/discounts";
    }

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException, TransactionException {

        response.sendRedirect(request.getContextPath() + path);
    }
}
