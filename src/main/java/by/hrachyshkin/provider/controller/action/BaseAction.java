package by.hrachyshkin.provider.controller.action;

import by.hrachyshkin.provider.dao.TransactionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
