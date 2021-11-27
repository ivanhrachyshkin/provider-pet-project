package by.hrachyshkin.provider.controller.action.impl.promotion;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.service.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DiscountsForPromotionAction extends BaseAction {

    public static final String DISCOUNTS_FOR_PROMOTION = "/tariffs/discounts";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException {

        checkGetHTTPMethod(request);
        setTariffIdAttributeToSession(request, request.getParameter("tariffId"));

        return "/tariffs/discounts-for-promotion";
    }

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
