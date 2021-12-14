package by.hrachyshkin.provider.controller.action.impl.promotion;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DiscountsForPromotionAction extends BaseAction {

    public static final String DISCOUNTS_FOR_PROMOTION = "/tariffs/discounts";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException, ServiceException {

        checkGetHTTPMethod(request);
        setTariffIdAttributeToSession(request,
                request.getParameter("tariffId"));

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
