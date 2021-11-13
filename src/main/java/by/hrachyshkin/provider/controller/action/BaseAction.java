package by.hrachyshkin.provider.controller.action;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Account;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class BaseAction extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (TransactionException e) {
            throw new ServletException("Transaction exception");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (TransactionException e) {
            throw new ServletException("Transaction exception");
        }
    }

    protected abstract void processRequest(HttpServletRequest request,
                                           HttpServletResponse response)
            throws ServletException, IOException, TransactionException;

    protected Integer getAccountId(HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        return (Integer) session.getAttribute("accountId");
    }

    protected Account.Role getRole(HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        return (Account.Role) session.getAttribute("accountRole");
    }
}
