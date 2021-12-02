package by.hrachyshkin.provider.controller.action.impl;

import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Model;
import by.hrachyshkin.provider.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public abstract class BaseAction implements Action {


    public void postExecute(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final String path)
            throws ServletException, IOException, ServiceException {

        request.getRequestDispatcher(path).forward(request, response);
    }

    protected Integer getAccountId(final HttpServletRequest request) {

        final HttpSession session = request.getSession(false);
        return (Integer) session.getAttribute("accountId");
    }

    protected Account.Role getRole(final HttpServletRequest request) {

        final HttpSession session = request.getSession(false);
        return (Account.Role) session.getAttribute("accountRole");
    }

    protected int getOffset(final HttpServletRequest request) {

        int offset = 0;

        final HttpSession session = request.getSession();

        if (session.getAttribute("page") != null) {
            String value = (String) session.getAttribute("page");
            offset = (Integer.parseInt(value) - 1) * 5;
        }

        if (request.getParameter("page") != null) {
            offset = (Integer.parseInt(request.getParameter("page")) - 1) * 5;
        }

        return offset;
    }

    protected void setTotalPagesAttribute(final HttpServletRequest request,
                                          final List<? extends Model> list) {

        final int totalPages =
                list.size() % 5 == 0 ? list.size() / 5 : list.size() / 5 + 1;
        request.setAttribute("totalPages", totalPages);
    }

    protected void setPageNumber(final HttpServletRequest request) {

        final HttpSession session = request.getSession();

        String pageNumber;

        if (session.getAttribute("page") != null) {
            pageNumber = (String) session.getAttribute("page");
        } else {
            final String page = request.getParameter("page");
            pageNumber =
                    String.valueOf(page == null ? 1 : Integer.parseInt(page));
        }
        request.setAttribute("page", pageNumber);
    }

    protected void setPageNumberAttributeToSession(
            final HttpServletRequest request) {

        final HttpSession session = request.getSession();
        session.setAttribute("page", request.getParameter("page"));
    }

    protected void setErrorAttributeToSession(final HttpServletRequest request,
                                              final String value) {

        final HttpSession session = request.getSession(false);
        session.setAttribute("error", value);
    }

    protected void setTariffIdAttributeToSession(
            final HttpServletRequest request,
            final String value) {

        final HttpSession session = request.getSession(false);
        session.setAttribute("tariffId", value);
    }

    protected String getTariffIdAttributeSession(
            final HttpServletRequest request) {

        final HttpSession session = request.getSession(false);
        return (String) session.getAttribute("tariffId");
    }

    protected void checkGetHTTPMethod(final HttpServletRequest request)
            throws ServletException {

        if (request.getMethod().equals("GET")) {
            throw new ServletException("Unsupported get operation");
        }
    }

    protected void removeAttribute(final HttpServletRequest request,
                                   final String value) {

        final HttpSession session = request.getSession();
        session.removeAttribute(value);
    }
}
