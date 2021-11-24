package by.hrachyshkin.provider.controller.action.impl.subscription;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BillsForSubscriptionAction extends BaseAction {

    public static final String BILLS_FOR_SUBSCRIPTION = "/cabinet/subscriptions/bills";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {

        try {
            checkHttpMethod(request);
            setTariffIdAttributeToSession(request, request.getParameter("tariffId"));

        } catch (NumberFormatException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/cabinet/subscriptions/bills-for-subscription";
    }

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException, TransactionException {
        response.sendRedirect(request.getContextPath() + path);
    }
}