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

    protected Integer getAccountId(HttpServletRequest request) {

        final HttpSession session = request.getSession(false);
        return (Integer) session.getAttribute("accountId");
    }

    protected Account.Role getRole(HttpServletRequest request) {

        final HttpSession session = request.getSession(false);
        return (Account.Role) session.getAttribute("accountRole");
    }

    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException, TransactionException {

        request.getRequestDispatcher(path).forward(request, response);
    }

    protected int getOffset(HttpServletRequest request) {

        int offset = 0;
        if (request.getParameter("page") != null) {
            offset = (Integer.parseInt(request.getParameter("page")) - 1) * 5;
        }
        return offset;
    }

    protected void setTotalPagesAttribute(HttpServletRequest request, final List<? extends Model> list) {

        request.setAttribute("totalPages", list.size() / 5 + 1);
    }
}
