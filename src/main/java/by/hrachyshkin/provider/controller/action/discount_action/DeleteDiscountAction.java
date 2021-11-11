package by.hrachyshkin.provider.controller.action.discount_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Discount;
import by.hrachyshkin.provider.service.*;
import by.hrachyshkin.provider.service.impl.DiscountServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/discounts/delete")
public class DeleteDiscountAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final DiscountService discountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);

        try {
            final Integer id = Integer.valueOf(request.getParameter("id"));
            discountService.delete(id);
        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/discounts").forward(request, response);
    }
}
