package by.hrachyshkin.provider.controller.action.impl.promotion;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.service.PromotionService;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletePromotionAction extends BaseAction {

    public static final String DELETE_PROMOTION = "/tariffs/discounts/delete";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {

        try {
            checkGetHTTPMethod(request);
            final PromotionService promotionService = ServiceFactory
                    .getINSTANCE().getService(ServiceKeys.PROMOTION);

            final String tariffId = request.getParameter("tariffId");
            final Integer discountId =
                    Integer.valueOf(request.getParameter("discountId"));

            promotionService.deleteByTariffAndDiscount(
                    Integer.valueOf(tariffId), discountId);

            setTariffIdAttributeToSession(request, tariffId);

        } catch (ServiceException | NumberFormatException
                | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return ShowDiscountsForPromotionAction.SHOW_DISCOUNTS_FOR_PROMOTION;
    }

    @Override
    public void postExecute(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final String path)
            throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
