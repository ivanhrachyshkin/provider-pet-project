package by.hrachyshkin.provider.controller.action;

import by.hrachyshkin.provider.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseAction extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected abstract void processRequest(HttpServletRequest request,
                                           HttpServletResponse response)
            throws ServletException, IOException;

    public void redirect(HttpServletRequest request,
                         HttpServletResponse response,
                         String resource) throws IOException {

        response.sendRedirect(request.getContextPath() + "/" + resource);

    }
}
