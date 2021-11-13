package by.hrachyshkin.provider.controller.action.tariff_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.service.PromotionService;
import by.hrachyshkin.provider.service.impl.PromotionServiceImpl;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tariffs/discounts/remove")
public class RemoveDiscountFromTariffAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            final PromotionService promotionService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
            final String tariffId = request.getParameter("tariffId");
            final String discountId = request.getParameter("discountId");
            promotionService.deleteByTariffAndDiscount(Integer.valueOf(tariffId), Integer.valueOf(discountId));
        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/tariffs/discounts").forward(request, response);
    }
}
