package by.hrachyshkin.provider.action.tariff_action;

import by.hrachyshkin.provider.entity.Promotion;
import by.hrachyshkin.provider.service.PromotionServiceImpl;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tariffs/discounts/remove")
public class RemoveDiscountToTariffAction extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final PromotionServiceImpl promotionService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.PROMOTION_SERVICE);
        final String tariffId = request.getParameter("tariffId");
        final String discountId = request.getParameter("discountId");

        promotionService.deleteByTariffAndDiscount(Integer.valueOf(tariffId), Integer.valueOf(discountId));
        response.sendRedirect("/training-java-project-provider/tariffs");
    }
}
