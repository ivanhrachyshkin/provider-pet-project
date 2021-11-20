package by.hrachyshkin.provider.controller.action.impl;

import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.dao.TransactionException;
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


    public void postExecute(final HttpServletRequest request, final HttpServletResponse response, final String path) throws ServletException, IOException, ServiceException, TransactionException {

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
        if (request.getParameter("page") != null) {
            offset = (Integer.parseInt(request.getParameter("page")) - 1) * 5;
        }
        return offset;
    }

    protected void setTotalPagesAttribute(final HttpServletRequest request, final List<? extends Model> list) {

        request.setAttribute("totalPages", list.size() / 5 + 1);
    }

    protected void pagination(final HttpServletRequest request) {

        if (request.getParameter("page") == null) {
            request.setAttribute("page", request.getParameter("page"));
        } else {
            request.setAttribute("page", Integer.valueOf(request.getParameter("page")));
        }
    }

    protected void setErrorAttributeToSession(final HttpServletRequest request, final String value) {

        final HttpSession session = request.getSession(false);
        session.setAttribute("error", value);
    }
}
