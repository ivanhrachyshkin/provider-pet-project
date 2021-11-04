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
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            checkHttpMethod(request);

            final PromotionService promotionService = ServiceFactory.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);

            final Integer tariffId = Integer.valueOf(request.getParameter("tariffId"));
            final Integer discountId = Integer.valueOf(request.getParameter("discountId"));

            promotionService.deleteByTariffAndDiscount(tariffId, discountId);

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            request.setAttribute("error", e.getMessage());
        }
        return "/tariffs/discounts";
    }
}
